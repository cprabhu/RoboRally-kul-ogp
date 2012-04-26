package roborally.model;

import java.util.*;

import roborally.model.Energy.unitOfPower;

public class Node implements Comparable<Node> {

    public Node(Node parent, Orientation orientation, double cost,
            Position position, Position targetPosition) {
        this.POSITION = position;
        this.TARGET_POSITION = targetPosition;
        this.orientation = orientation;
        this.parent = parent;
        this.cost = cost;
    }

    public Node shortestPath() {
        if (POSITION == null)
            return null;
        double energyAmount = getOriginRobot().getAmountOfEnergy();
        Queue<Node> open = new PriorityQueue<Node>();
        Set<Node> closed = new HashSet<Node>();
        Node current = this;
        // boolean sufficientEnergy = energyAmount > getOriginRobot()
        // .getEnergyToMove();
        double criterion = getTargetRobot() != null ? getOriginRobot()
                .getEnergyToMove() : 0;

        open.add(this);
        while (open.size() != 0 && current.heuristic() != criterion) {
            current = open.poll();
            System.out.println(current.getPosition() + " -- " + current.cost
                    + " -- " + open.size());
            // sufficientEnergy = current.cost < energyAmount;
            // if (!sufficientEnergy)
            // continue;
            closed.add(current);

            Set<Node> neighbours = current.getNeighbours();
            Set<Node> neighbourSet = new HashSet<Node>();
            for (Node neighbour : neighbours) {
                neighbourSet.clear();
                neighbourSet.addAll(open);
                for (Node openNeighbour : neighbourSet)
                    if (neighbour.equals(openNeighbour)
                            && neighbour.cost < openNeighbour.cost) {
                        open.remove(openNeighbour);
                    }
                neighbourSet.clear();
                neighbourSet.addAll(closed);
                for (Node closedNeighbour : neighbourSet)
                    if (neighbour.equals(closedNeighbour)
                            && neighbour.cost < closedNeighbour.cost)
                        closed.remove(closedNeighbour);
                if (!open.contains(neighbour) && !closed.contains(neighbour)
                        && neighbour.cost <= energyAmount)
                    open.add(neighbour);
            }
        }

        return current;
    }

    // TODO: test of allshortestpaths de beginnode geeft als te weinig energy.
    public Set<Node> allShortestPaths() {
        if (POSITION == null)
            return null;
        double energyAmount = getOriginRobot().getAmountOfEnergy();
        double minimumPathEnergy = Double.POSITIVE_INFINITY;
        Queue<Node> open = new PriorityQueue<Node>();
        List<Node> closed = new ArrayList<Node>();
        Set<Node> shortestPaths = new HashSet<Node>();
        Node targetNode = new Node(null, null, 0, TARGET_POSITION, null);
        Node current = this;
        boolean sufficientEnergy = energyAmount > getOriginRobot()
                .getEnergyToMove();
        double criterion = getTargetRobot() != null ? getOriginRobot()
                .getEnergyToMove() : 0;

        open.add(this);
        while (open.size() != 0 && current.heuristic() != criterion) {
            current = open.poll();
            if (targetNode.equals(open.peek()))
                minimumPathEnergy = Math.min(minimumPathEnergy, current.cost);
            sufficientEnergy = current.cost < energyAmount;
            if (!sufficientEnergy)
                continue;
            if (current.cost > minimumPathEnergy)
                continue;
            closed.add(current);

            Set<Node> neighbours = getNeighbours();
            for (Node neighbour : neighbours) {
                for (Node openNeighbour : open)
                    if (neighbour.equals(openNeighbour)
                            && neighbour.compareTo(openNeighbour) <= 0) {
                        open.remove(openNeighbour);
                    }
                for (Node closedNeighbour : closed)
                    if (neighbour.compareTo(closedNeighbour) <= 0) {
                        closed.remove(closedNeighbour);
                    }
                if (!open.contains(neighbour) && !closed.contains(neighbour))
                    open.add(neighbour);
            }
        }

        if (minimumPathEnergy < Double.POSITIVE_INFINITY) {
            List<Node> it = new ArrayList<Node>();
            it.addAll(closed);

            for (Node node : it)
                if (!targetNode.getNeighbours().contains(node))
                    closed.remove(node);
        }

        double energyToReachLowerBound = POSITION
                .manhattanDistance(TARGET_POSITION)
                * Math.min(getOriginRobot().getEnergyToMove(), getTargetRobot()
                        .getEnergyToMove());
        energyAmount = getOriginRobot().getAmountOfEnergy()
                + getTargetRobot().getAmountOfEnergy();
        if (energyToReachLowerBound > energyAmount) {
            List<Node> it = new ArrayList<Node>();
            it.addAll(closed);
            for (Node node : it)
                if (closed.containsAll(node.getNeighbours()))
                    closed.remove(node);
            shortestPaths.addAll(closed);
        } else
            shortestPaths.addAll(closed);

        return shortestPaths;
    }

    public Node bestNodeAlongPaths(Set<Node> paths) {
        Node bestNode = null;
        double bestNodeCost = Double.POSITIVE_INFINITY;
        Node bestAlongPath;
        double currentCost;

        for (Node path : paths) {
            bestAlongPath = path.bestNodeAlongPath();
            for (Node neighbour : bestAlongPath.getNeighbours()) {
                double minimumEnergyRequiredToReachNeighbour = getTargetRobot()
                        .getEnergyRequiredToReachWs(neighbour.getPosition());
                if (minimumEnergyRequiredToReachNeighbour < 0)
                    break;
                currentCost = path.cost + minimumEnergyRequiredToReachNeighbour;
                if (currentCost < bestNodeCost) {
                    bestNodeCost = currentCost;
                    bestNode = neighbour;
                }
            }
        }

        return bestNode;
    }

    public int compareTo(Node node) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;

        if (getEstimatedCost() == node.getEstimatedCost())
            return EQUAL;
        else if (getEstimatedCost() < node.getEstimatedCost())
            return BEFORE;
        else
            return AFTER;
    }

    public boolean equals(Object o) {
        Position otherPosition = ((Node) o).POSITION;
        return POSITION.equals(otherPosition);
    }

    public double getEstimatedCost() {
        return cost + heuristic();
    }

    public Energy getEnergy() {
        return new Energy(cost, unitOfPower.Ws);
    }

    public Node getParent() {
        return parent;
    }

    public Position getPosition() {
        return POSITION;
    }

    private Robot getOriginRobot() {
        if (POSITION == null)
            return null;
        if (parent != null)
            return parent.getOriginRobot();
        for (Element elem : POSITION.getElements())
            if (elem instanceof Robot)
                return (Robot) elem;
        return null;
    }

    private Robot getTargetRobot() {
        for (Element elem : TARGET_POSITION.getElements())
            if (elem instanceof Robot)
                return (Robot) elem;
        return null;
    }

    private Set<Node> getNeighbours() {
        Set<Node> neighbours = new HashSet<Node>();

        for (int i = 0; i < 4; i++) {

            if (!orientation.nextPosition(POSITION).canContainElement(
                    getOriginRobot()))
                continue;
            double stepCost = 100 * i;
            if (stepCost == 300)
                stepCost = 100;
            stepCost += 500;
            double costToReach = cost + stepCost;
            neighbours.add(new Node(this, orientation, costToReach, orientation
                    .nextPosition(POSITION), TARGET_POSITION));
            orientation = orientation.turnClockwise90();
        }

        return neighbours;
    }

    private double heuristic() {
        return POSITION.manhattanDistance(TARGET_POSITION)
                * getOriginRobot().getEnergyToMove();
    }

    private Node bestNodeAlongPath() {
        Node bestNode = null;
        double bestNodeCost = Double.POSITIVE_INFINITY;
        Node bestParentNode;
        Set<Node> candidates = new HashSet<Node>();

        if (getParent() != null) {
            bestParentNode = getParent().bestNodeAlongPath();
            candidates.add(bestParentNode);
        }

        candidates.addAll(getNeighbours());
        double currentCost;
        for (Node candidate : candidates) {
            double minimumEnergyRequiredToReachNeighbour = getTargetRobot()
                    .getEnergyRequiredToReachWs(candidate.getPosition());
            if (minimumEnergyRequiredToReachNeighbour < 0)
                break;
            currentCost = cost + minimumEnergyRequiredToReachNeighbour;
            if (currentCost < bestNodeCost) {
                bestNodeCost = currentCost;
                bestNode = this;
            }
        }

        return bestNode;
    }

    private Node parent;
    private Orientation orientation;
    private double cost;

    private final Position POSITION;
    private final Position TARGET_POSITION;
}
