package roborally.model;

import roborally.model.Energy.unitOfPower;
import roborally.model.Weight.unitOfMass;
import java.util.*;

// TODO: energy nominally
// TODO: orientation totally
// TODO: position defensively

public class Robot extends Element {

    public Robot(Energy initialEnergy, Orientation initialOrienation) {
        energy = initialEnergy;
        maxEnergy = new Energy(20000, unitOfPower.Ws);
        orientation = initialOrienation;
        position = null;
        carryWeight = null;
    }

    public void recharge(Energy chargeEnergy) {
        energy.recharge(chargeEnergy, maxEnergy);
    }

    public Energy getEnergyRequiredToReach(Position destination) {
        Node node = new Node(null, getOrientation(), 0, getPosition(),
                destination);
        Node shortestPath = node.shortestPath();
        Energy energyRequiredToReach = shortestPath.getEnergy();

        if (shortestPath.getPosition().getNeighbours().contains(destination))
            return energyRequiredToReach;
        return null;
    }

    public double getEnergyRequiredToReachWs(Position destination) {
        Node node = new Node(null, getOrientation(), 0, getPosition(),
                destination);
        Node shortestPath = node.shortestPath();
        Energy energyRequiredToReach = shortestPath.getEnergy();

        if (shortestPath.getPosition().getNeighbours().contains(destination))
            return energyRequiredToReach.getAmountOfEnergy();
        if (getAmountOfEnergy() - energyRequiredToReach.getAmountOfEnergy() < getEnergyToMove())
            return -2;
        return -1;
    }

    public double getAmountOfEnergy() {
        return energy.getAmountOfEnergy();
    }

    public double getFractionOfEnergy() {
        return energy.getAmountOfEnergy() / maxEnergy.getAmountOfEnergy();
    }

    public double getEnergyToMove() {
        Energy weightEnergy = new Energy(50 * getCarriedWeight(unitOfMass.kg),
                unitOfPower.Ws);
        Energy moveEnergy = new Energy(0, unitOfPower.Ws);
        moveEnergy.addEnergy(weightEnergy);
        moveEnergy.addEnergy(energyToMove);
        return moveEnergy.getAmountOfEnergy();
    }

    public void turnClockwise90() {
        if (energyToTurn.compareTo(energy) < 1) {
            orientation.turnClockwise90();
            energy.removeEnergy(energyToTurn);
        }
    }

    public void turnCounterClockwise90() {
        if (energyToTurn.compareTo(energy) < 1) {
            orientation.turnCounterClockwise90();
            energy.removeEnergy(energyToTurn);
        }
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public int getOrientationInt() {
        return orientation.ordinal();
    }

    public void move() {
        if (getEnergyToMove() < energy.getAmountOfEnergy()) {
            Position nextPosition = orientation.nextPosition(position);
            setPosition(nextPosition);
            energy.removeEnergy(energyToMove);
        }
    }

    public void moveTo(Position position) {
        position.BOARD.putElement(position, this);
        energy.removeEnergy(getEnergyRequiredToReach(position));
    }

    public void moveNextTo(Robot robot2) {
        Node robot1Node = new Node(null, getOrientation(), 0, getPosition(),
                robot2.getPosition());
        Node robot2Node = new Node(null, getOrientation(), 0,
                robot2.getPosition(), getPosition());
        Node robot1BestNode;
        Node robot2BestNode;
        Position robot1BestPosition = getPosition();
        Position robot2BestPosition = robot2.getPosition();
        double minimumEnergy = Double.POSITIVE_INFINITY;

        Node robot1ShortestPath = robot1Node.shortestPath();
        Node robot2ShortestPath = robot2Node.shortestPath();

        Set<Node> robot1AllShortestPaths = new HashSet<Node>();
        Set<Node> robot2AllShortestPaths = new HashSet<Node>();

        if (robot1ShortestPath.getPosition().getNeighbours()
                .contains(robot2.getPosition()))
            robot1AllShortestPaths = robot1Node.allShortestPaths();
        if (robot2ShortestPath.getPosition().getNeighbours()
                .contains(getPosition()))
            robot2AllShortestPaths = robot2Node.allShortestPaths();
        if (robot1AllShortestPaths.size() != 0
                || robot2AllShortestPaths.size() != 0) {
            robot1BestNode = robot1Node
                    .bestNodeAlongPaths(robot1AllShortestPaths);
            robot2BestNode = robot2Node
                    .bestNodeAlongPaths(robot2AllShortestPaths);

            robot1BestPosition = robot1BestNode.getPosition();
            for (Position neighbour : robot1BestPosition.getNeighbours())
                if (getEnergyRequiredToReachWs(robot1BestPosition)
                        + robot2.getEnergyRequiredToReachWs(neighbour) < minimumEnergy)
                    robot2BestPosition = neighbour;
            for (Position neighbour : robot2BestNode.getPosition()
                    .getNeighbours()) {
                if (robot2.getEnergyRequiredToReachWs(robot2BestNode
                        .getPosition()) + getEnergyRequiredToReachWs(neighbour) < minimumEnergy) {
                    robot1BestPosition = neighbour;
                    robot2BestPosition = robot2BestNode.getPosition();
                }
            }
        } else {
            robot1AllShortestPaths = robot1Node.allShortestPaths();
            robot2AllShortestPaths = robot2Node.allShortestPaths();
            List<Node> robot1BestNodes = new ArrayList<Node>();
            List<Node> robot2BestNodes = new ArrayList<Node>();
            double minManhattanDistance = getPosition().manhattanDistance(
                    robot2.getPosition());

            for (Node node1 : robot1AllShortestPaths) {
                for (Node node2 : robot2AllShortestPaths) {
                    if (node1.getPosition().manhattanDistance(
                            node2.getPosition()) == minManhattanDistance) {
                        robot1BestNodes.add(node1);
                        robot2BestNodes.add(node2);
                    } else if (node1.getPosition().manhattanDistance(
                            node2.getPosition()) < minManhattanDistance) {
                        robot1BestNodes.clear();
                        robot2BestNodes.clear();
                        robot1BestNodes.add(node1);
                        robot2BestNodes.add(node2);
                    }
                }
            }

            Iterator<Node> robot2BestNodeIt = robot2BestNodes.iterator();
            for (Node node1 : robot1BestNodes) {
                Node node2 = robot2BestNodeIt.next();
                if (getEnergyRequiredToReachWs(node1.getPosition())
                        + robot2.getEnergyRequiredToReachWs(node2.getPosition()) < minimumEnergy) {
                    robot1BestPosition = node1.getPosition();
                    robot2BestPosition = node2.getPosition();
                }
            }
        }
        moveTo(robot1BestPosition);
        robot2.moveTo(robot2BestPosition);
    }

    public Position getPosition() {
        return position;
    }

    public void shoot() {
        energy.removeEnergy(new Energy(1000, unitOfPower.Ws));
        Position bulletPosition = position;
        while (position.BOARD.isValidPosition(bulletPosition)
                && !position.BOARD.isOccupiedPosition(bulletPosition)) {
            bulletPosition = orientation.nextPosition(bulletPosition);
        }

        int item = new Random().nextInt(bulletPosition.getElements().size());
        int i = 0;
        for (Element elem : bulletPosition.getElements()) {
            if (i == item)
                elem.terminate();
            i = i + 1;
        }
    }

    public void pickup(Battery battery) {
        if (battery != null && carryWeight.compareTo(battery.getWeight()) > -1)
            if (position.equals(battery.getPosition())) {
                for (int i = 0; i < batteries.size(); i++) {
                    if (battery.getWeight().compareTo(
                            batteries.get(i).getWeight()) > -1) {
                        batteries.add(i, battery);
                        break;
                    }
                }
                battery.removePosition();
                carryWeight.removeWeight(battery.getWeight());
            }
    }

    public void use(Battery battery) {
        if (battery != null) {
            if (battery.isTerminated())
                batteries.remove(battery);
            if (batteries.contains(battery))
                battery.charge(energy, maxEnergy);
        }
    }

    public void drop(Battery battery) {
        if (battery != null) {
            batteries.remove(battery);
            battery.setPosition(position);
            carryWeight.addWeight(battery.getWeight());
        }
    }

    public Battery ithHeaviestElement(int ordinal) {
        return batteries.get(ordinal - 1);
    }

    public Set<Battery> getPossesions() {
        Set<Battery> possesions = new HashSet<Battery>();
        possesions.addAll(batteries);
        return possesions;
    }

    public int getCarriedWeight(unitOfMass unit) {
        int carriedWeight = 0;
        for (Battery battery : batteries)
            carriedWeight += battery.getWeight().getMassIn(unit);
        return carriedWeight;
    }

    public final Energy energyToMove = new Energy(500, unitOfPower.Ws);

    public static final Energy energyToTurn = new Energy(100, unitOfPower.Ws);

    private final Energy energy;
    private final Energy maxEnergy;
    private final Orientation orientation;
    private Position position;
    private final Weight carryWeight;
    private final List<Battery> batteries = new ArrayList<Battery>();

}
