package roborally.model;

import roborally.model.auxiliary.Energy;
import roborally.model.auxiliary.EnergyElement;
import roborally.model.auxiliary.Node;
import roborally.model.auxiliary.Weight;
import roborally.model.auxiliary.Energy.unitOfPower;
import roborally.model.auxiliary.Weight.unitOfMass;
import roborally.program.Program;

import java.util.*;

// TODO: NOTE Klassediagram voor verdediging

// TODO: NOTE energy nominally
// TODO: NOTE orientation totally
// TODO: NOTE position defensively

/**
 * @author Ben Adriaenssens <ben.adriaenssens@student.kuleuven.be>, Toon Nolten <toon.nolten@student.kuleuven.be>
 */
public class Robot extends Element implements EnergyElement {

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
        assert (chargeEnergy.isValidEnergy(chargeEnergy));/*
                                                           * chargeEnergy als
                                                           * argument omdat de
                                                           * energie zo groot
                                                           * mag zijn als ze
                                                           * wil.
                                                           */
        energy.recharge(chargeEnergy, maxEnergy);
    }

    public void increaseMaxEnergy(Energy energy) {
        assert (energy != null);
        assert (energy.compareTo(new Energy(0, unitOfPower.Ws)) >= 0);
        maxEnergy.recharge(energy, energyLimit);
    }

    public Energy getEnergyRequiredToReach(Position destination) {
        if (!destination.canContainElement(this))
            return null;
        Node node = new Node(null, getOrientation(), 0, getPosition(),
                destination);
        Node shortestPath = node.shortestPath();
        if (shortestPath == null /*
                                  * Robot staat niet op een position dus niet
                                  * op een board.
                                  */
                || !(shortestPath.getPosition().equals(destination)))
            return null;
        Energy energyRequiredToReach = shortestPath.getEnergy();

        return energyRequiredToReach;
    }

    // TODO: NOTE Opgave 3, Position(insuf energy: -1, obstacle: -1,
    // notonboard: -1)
    public double getEnergyRequiredToReachWs(Position destination) {
        if (!destination.canContainElement(this))
            return -1;
        Node node = new Node(null, getOrientation(), 0, getPosition(),
                destination);
        Node shortestPath = node.shortestPath();
        if (shortestPath == null /*
                                  * Robot staat niet op een position dus niet
                                  * op een board.
                                  */
                || !(shortestPath.getPosition().equals(destination)))
            return -1;
        Energy energyRequiredToReach = shortestPath.getEnergy();

        return energyRequiredToReach.getAmountOfEnergy();
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

    public void move() throws IllegalStateException {
        assert (getEnergyToMove() <= energy.getAmountOfEnergy());
        Position nextPosition = orientation.nextPosition(position);
        if (nextPosition.equals(getPosition()))
            throw new IllegalStateException();
        setPosition(nextPosition);
        energy.removeEnergy(energyToMove);

    }

    public void moveTo(Node node) {
        Energy energyRequiredToReach = getEnergyRequiredToReach(node
                .getPosition());
        assert (energyRequiredToReach != null);
        assert (energyRequiredToReach.isValidEnergy(maxEnergy));
        energy.removeEnergy(energyRequiredToReach);
        node.getPosition().BOARD.putElement(node.getPosition(), this);
        orientation = node.getOrientation();
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
            Node robot1Destination = bestNodePairs.get(0)[0];
            Node robot2Destination = bestNodePairs.get(0)[1];

            Energy energyRequiredToReachRobot1Destination = getEnergyRequiredToReach(robot1Destination
                    .getPosition());
            Energy energyRequiredToReachRobot2Destination = robot2
                    .getEnergyRequiredToReach(robot2Destination.getPosition());
            if (energyRequiredToReachRobot1Destination != null
                    && energyRequiredToReachRobot1Destination
                            .isValidEnergy(maxEnergy))
                moveTo(robot1Destination);

            if (energyRequiredToReachRobot2Destination != null
                    && energyRequiredToReachRobot2Destination
                            .isValidEnergy(maxEnergy))
                robot2.moveTo(robot2Destination);
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

        if (bulletPosition != null) {
            int item = new Random()
                    .nextInt(bulletPosition.getElements().size());

            Iterator<Element> elementsAtBulletPositionIt = bulletPosition
                    .getElements().iterator();
            for (int i = 0; i < item; i++) {
                elementsAtBulletPositionIt.next();
            }
            elementsAtBulletPositionIt.next().terminate();
        }
    }

    public void hit() {
        maxEnergy.removeEnergy(new Energy(4000, unitOfPower.Ws));
        if (energy.compareTo(maxEnergy) > 0)
            energy.removeEnergy(new Energy(energy.getAmountOfEnergy()
                    - maxEnergy.getAmountOfEnergy(), unitOfPower.Ws));
        if (!maxEnergy.isValidEnergy(energyLimit)
                || maxEnergy.compareTo(new Energy(0, unitOfPower.Ws)) == 0)
            terminate();
    }

    public void addItems(Set<Item> itemsToAdd) {
        if (!isTerminated())
            for (Item itemToAdd : itemsToAdd) {
                if (itemToAdd != null)
                    if (items.isEmpty()
                            || itemToAdd.getWeight().compareTo(
                                    items.get(items.size() - 1).getWeight()) < 0)
                        items.add(itemToAdd);
                    else {
                        List<Item> compItems = new ArrayList<Item>(items);
                        for (Item compItem : compItems)
                            if (itemToAdd.getWeight().compareTo(
                                    compItem.getWeight()) >= 0)
                                items.add(items.indexOf(compItem), itemToAdd);
                    }
            }
    }

    public void pickup(Item item) throws IllegalStateException {
        if (item == null
                || (carryWeight != null && carryWeight.compareTo(item
                        .getWeight()) < 0))
            throw new IllegalStateException();
        if (position.equals(item.getPosition())) {
            if (items.size() != 0) {
                Set<Item> itemsToAdd = new HashSet<Item>();
                itemsToAdd.add(item);
                addItems(itemsToAdd);
            }
            if (!items.contains(item))
                items.add(item);
            item.removePosition();
            if (carryWeight != null)
                carryWeight.removeWeight(item.getWeight());
        }
    }

    public void use(Item item) throws IllegalArgumentException {
        if (item == null)
            throw new IllegalArgumentException();

        if (item.isTerminated())
            items.remove(item);
        if (items.contains(item))
            item.use(this);
    }

    public void drop(Item item) throws IllegalArgumentException {
        if (item == null)
            throw new IllegalArgumentException();
        items.remove(item);
        item.setPosition(position);
        if (carryWeight != null)
            carryWeight.addWeight(item.getWeight());
    }

    public void transferItems(Robot robot2) {
        if (!(isTerminated() || robot2 == null || robot2.isTerminated()))
            if (position != null)
                for (Position neighbour : position.getNeighbours())
                    if (robot2.getPosition().equals(neighbour)) {
                        robot2.addItems(getPossesions());
                        items.clear();
                    }
    }

    public Item ithHeaviestElement(int ordinal)
            throws IndexOutOfBoundsException {
        if (ordinal > items.size())
            throw new IndexOutOfBoundsException();
        return items.get(ordinal - 1);
    }

    public Set<Item> getPossesions() {
        Set<Item> possessions = new HashSet<Item>();
        possessions.addAll(items);
        return possessions;
    }

    public void stepn(int n) {
        if (program != null)
            for (int i = 0; i < n; i++) {
                program.step();
            }
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Program getProgram() {
        return program;
    }

    public void terminate() {
        if (position != null)
            position.removeElement(this);
        position = null;
        for (Item item : getPossesions())
            item.terminate();
        program = null;
        this.isTerminated = true;
    }

    private int getCarriedWeight(unitOfMass unit) {
        int carriedWeight = 0;
        for (Item item : items)
            carriedWeight += item.getWeight().getMassIn(unit);
        return carriedWeight;
    }

    private final Energy energyToMove = new Energy(500, unitOfPower.Ws);
    private static final Energy energyToTurn = new Energy(100, unitOfPower.Ws);
    private final Energy energy;
    private final Energy maxEnergy;
    private static final Energy energyLimit = new Energy(20000, unitOfPower.Ws);
    private final Weight carryWeight;
    private final List<Item> items = new ArrayList<Item>();

    private Program program;
    private Orientation orientation;
}
