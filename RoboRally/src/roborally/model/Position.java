package roborally.model;

import java.util.*;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * @author Ben Adriaenssens <ben.adriaenssens@student.kuleuven.be>, Toon Nolten <toon.nolten@student.kuleuven.be>
 */
public class Position {

    private Position(long x, long y, Board board)
            throws IllegalArgumentException {
        if (!board.isValidPosition(x, y))
            throw new IllegalArgumentException();
        this.BOARD = board;
        this.X = x;
        this.Y = y;
        elements = new HashSet<Element>();
        this.isTerminated = false;
    }

    public static Position newPosition(long x, long y, Board board) {
        Position position = new Position(x, y, board);
        if (positionInstances.contains(position))
            for (Position instance : positionInstances)
                if (instance.equals(position)) {
                    position = instance;
                    break;
                }
        if (position != null)
            positionInstances.add(position);
        return position;
    }

    public void addElement(Element element) {
        if (!isTerminated() && canContainElement(element)
                && !elements.contains(element) && element != null) {
            if (!BOARD.getOccupiedPositions().contains(this))
                BOARD.addOccupiedPosition(this);
            elements.add(element);
            element.setPosition(this);

        }
    }

    public void removeElement(Element element) {
        elements.remove(element);
        element.removePosition();

        if (elements.isEmpty()) {
            BOARD.removePosition(this);
            this.terminate();
        }
    }

    public Set<Element> getElements() {
        return elements;
    }

    public Set<Element> getElementsOf(Class<?> type) {
        Set<Element> elementsOfType = new HashSet<Element>();
        for (Element element : getElements()) {
            if (type.isAssignableFrom(element.getClass()))
                elementsOfType.add(element);
        }
        return elementsOfType;
    }

    public boolean containsElement(Element element) {
        if (elements != null)
            return elements.contains(element);
        return false;
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public boolean canContainElement(Element element) {
        if (isTerminated())
            return false;

        if (elements.contains(element))
            return true;

        if (getElementsOf(Wall.class).size() != 0)
            return false;

        if (element instanceof Wall && !elements.isEmpty())
            return false;

        boolean elementsContainsOtherRobot = false;
        for (Element elem : elements)
            if (elem instanceof Robot && !element.equals(elem))
                elementsContainsOtherRobot = true;
        if (element instanceof Robot && elementsContainsOtherRobot)
            return false;

        return true;
    }

    public boolean canContainType(Class<?> type) {
        if (isTerminated())
            return false;

        if (getElementsOf(Wall.class).size() != 0)
            return false;

        if (elements.size() != 0 && Wall.class == type)
            return false;

        boolean elementsContainsOtherRobot = false;
        for (Element elem : elements)
            if (elem instanceof Robot)
                elementsContainsOtherRobot = true;
        if (elementsContainsOtherRobot && Robot.class == type)
            return false;
        return true;
    }

    public boolean hasSameCoordinates(Position position) {
        return (X == position.X && Y == position.Y);
    }

    public double manhattanDistance(Position position)
            throws IllegalArgumentException {
        if (position == null || BOARD != position.BOARD)
            throw new IllegalArgumentException();
        return Math.abs(position.X - X) + Math.abs(position.Y - Y);
    }

    public boolean equals(Object o) {
        Position position = (Position) o;
        return (hasSameCoordinates(position) && BOARD == position.BOARD);
    }

    public int hashCode() {
        long positionNumber = X + BOARD.WIDTH * (Y - 1);
        return BOARD.hashCode() + (int) positionNumber;
    }

    public void terminate() {
        for (Element elem : elements)
            elem.terminate();
        elements = null;
        positionInstances.remove(this);
        this.isTerminated = true;
    }

    @Raw
    @Basic
    public boolean isTerminated() {
        return isTerminated;
    }

    public Set<Position> getNeighbours() {
        Set<Position> neighbours = new HashSet<Position>();

        if (BOARD.isValidPosition(X, Y - 1))
            neighbours.add(Position.newPosition(X, Y - 1, BOARD));
        if (BOARD.isValidPosition(X + 1, Y))
            neighbours.add(Position.newPosition(X + 1, Y, BOARD));
        if (BOARD.isValidPosition(X, Y + 1))
            neighbours.add(Position.newPosition(X, Y + 1, BOARD));
        if (BOARD.isValidPosition(X - 1, Y))
            neighbours.add(Position.newPosition(X - 1, Y, BOARD));

        return neighbours;
    }

    public String toString() {
        return "(" + X + ", " + Y + ")";
    }

    public final long X;
    public final long Y;
    public final Board BOARD;

    private Set<Element> elements;
    private boolean isTerminated;
    private static final Set<Position> positionInstances = new HashSet<Position>();
}
