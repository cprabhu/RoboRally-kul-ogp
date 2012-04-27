package roborally.model;

import roborally.model.Energy.unitOfPower;
import roborally.model.Weight.unitOfMass;
import java.util.*;

// TODO: energy nominally
// TODO: orientation totally: CHECK
// TODO: position defensively

/**
 * @author Ben Adriaenssens <ben.adriaenssens@student.kuleuven.be>, Toon Nolten <toon.nolten@student.kuleuven.be>
 */
public class Robot extends Element {

    public Robot(Energy initialEnergy, Orientation initialOrienation) {
        maxEnergy = new Energy(20000, unitOfPower.Ws);
        assert (initialEnergy != null);
        assert (initialEnergy.isValidEnergy(maxEnergy));
        energy = initialEnergy;
        orientation = initialOrienation;
        position = null;
        carryWeight = null;
    }

    public void recharge(Energy chargeEnergy) {
        assert (chargeEnergy != null);
        assert (chargeEnergy.isValidEnergy(chargeEnergy));
        energy.recharge(chargeEnergy, maxEnergy);
    }

    public Energy getEnergyRequiredToReach(Position destination) {
        if (!destination.canContainElement(this))
            return null;
        Node node = new Node(null, getOrientation(), 0, getPosition(),
                destination);
        Node shortestPath = node.shortestPath();
        if (shortestPath == null)
            return null; // Robot staat niet op een position dus niet op een
                         // board.
        Energy energyRequiredToReach = shortestPath.getEnergy();

        if (shortestPath.getPosition().equals(destination))
            return energyRequiredToReach;
        return null;
    }

    // TODO: floodfill implementeren voor -2
    public double getEnergyRequiredToReachWs(Position destination) {
        if (!destination.canContainElement(this))
            return -1;
        Node node = new Node(null, getOrientation(), 0, getPosition(),
                destination);
        Node shortestPath = node.shortestPath();
        if (shortestPath == null)
            return -1; // Robot staat niet op een position dus niet op een
                       // board.
        Energy energyRequiredToReach = shortestPath.getEnergy();

        if (shortestPath.getPosition().equals(destination))
            return energyRequiredToReach.getAmountOfEnergy();
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

    public double getEnergyToTurn() {
        return energyToTurn.getAmountOfEnergy();
    }

    public void turnClockwise90() {
        if (energyToTurn.compareTo(energy) <= 0) {
            orientation = orientation.turnClockwise90();
            energy.removeEnergy(energyToTurn);
        }
    }

    public void turnCounterClockwise90() {
        if (energyToTurn.compareTo(energy) <= 0) {
            orientation = orientation.turnCounterClockwise90();
            energy.removeEnergy(energyToTurn);
        }
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public int getOrientationInt() {
        return orientation.ordinal();
    }

    public void move() throws IllegalArgumentException {
        assert (getEnergyToMove() < energy.getAmountOfEnergy());
        Position nextPosition = orientation.nextPosition(position);
        setPosition(nextPosition);
        energy.removeEnergy(energyToMove);

    }

    public void moveTo(Position position) {
        Energy energyRequiredToReach = getEnergyRequiredToReach(position);
        assert (energyRequiredToReach != null);
        assert (energyRequiredToReach.isValidEnergy(maxEnergy));
        energy.removeEnergy(energyRequiredToReach);
        position.BOARD.putElement(position, this);
    }

    public void moveNextTo(Robot robot2) {
        Node robot1Node = new Node(null, getOrientation(), 0, getPosition(),
                robot2.getPosition());
        Node robot2Node = new Node(null, robot2.getOrientation(), 0,
                robot2.getPosition(), getPosition());

        Set<Node> robot1AllShortestPaths = new HashSet<Node>();
        Set<Node> robot2AllShortestPaths = new HashSet<Node>();

        try {
            robot1AllShortestPaths = robot1Node.allShortestPaths();
            robot2AllShortestPaths = robot2Node.allShortestPaths();

            List<Node[]> bestNodePairs = Node.bestNodePairs(
                    robot1AllShortestPaths, robot2AllShortestPaths);

            moveTo(bestNodePairs.get(0)[0].getPosition());
            robot2.moveTo(bestNodePairs.get(0)[1].getPosition());
        } catch (IllegalArgumentException e) {
            System.err
                    .println("Een van de twee robots heeft een position null.");
        } catch (AssertionError ae) {
            System.err
                    .println("moveNextTo: This assertionerror is to be expected.");
        }
    }

    public void shoot() {
        assert (energy.compareTo(new Energy(1000, unitOfPower.Ws)) >= 0);
        energy.removeEnergy(new Energy(1000, unitOfPower.Ws));
        Position bulletPosition = orientation.nextPosition(position);
        while (position.BOARD.isValidPosition(bulletPosition)
                && !position.BOARD.isOccupiedPosition(bulletPosition)) {
            bulletPosition = orientation.nextPosition(bulletPosition);
        }

        int item = new Random().nextInt(bulletPosition.getElements().size());

        Iterator<Element> elementsAtBulletPositionIt = bulletPosition
                .getElements().iterator();
        for (int i = 0; i < item; i++) {
            elementsAtBulletPositionIt.next();
        }
        elementsAtBulletPositionIt.next().terminate();
    }

    public void pickup(Battery battery) throws IllegalStateException {
        if (battery == null
                || (carryWeight != null && carryWeight.compareTo(battery
                        .getWeight()) < 0))
            throw new IllegalStateException();
        if (position.equals(battery.getPosition())) {
            if (batteries.size() != 0)
                for (Battery compBattery : batteries) {
                    if (battery.getWeight().compareTo(compBattery.getWeight()) >= 0) {
                        batteries.add(batteries.indexOf(compBattery), battery);
                        break;
                    }
                }
            if (!batteries.contains(battery))
                batteries.add(battery);
            battery.removePosition();
            if (carryWeight != null)
                carryWeight.removeWeight(battery.getWeight());
        }
    }

    public void use(Battery battery) throws IllegalStateException {
        if (battery == null)
            throw new IllegalStateException();

        if (battery.isTerminated())
            batteries.remove(battery);
        if (batteries.contains(battery))
            battery.charge(energy, maxEnergy);

    }

    public void drop(Battery battery) throws IllegalStateException {
        if (battery == null)
            throw new IllegalStateException();
        batteries.remove(battery);
        battery.setPosition(position);
        if (carryWeight != null)
            carryWeight.addWeight(battery.getWeight());

    }

    public Battery ithHeaviestElement(int ordinal)
            throws IndexOutOfBoundsException {
        if (ordinal > batteries.size())
            throw new IndexOutOfBoundsException();
        return batteries.get(ordinal - 1);
    }

    public Set<Battery> getPossesions() {
        Set<Battery> possesions = new HashSet<Battery>();
        possesions.addAll(batteries);
        return possesions;
    }

    public void terminate() {
        if (position != null)
            position.removeElement(this);
        position = null;
        for (Battery battery : getPossesions())
            battery.terminate();
        this.isTerminated = true;
    }

    private int getCarriedWeight(unitOfMass unit) {
        int carriedWeight = 0;
        for (Battery battery : batteries)
            carriedWeight += battery.getWeight().getMassIn(unit);
        return carriedWeight;
    }

    private final Energy energyToMove = new Energy(500, unitOfPower.Ws);
    private static final Energy energyToTurn = new Energy(100, unitOfPower.Ws);
    private final Energy energy;
    private final Energy maxEnergy;
    private final Weight carryWeight;
    private final List<Battery> batteries = new ArrayList<Battery>();

    private Orientation orientation;

}
