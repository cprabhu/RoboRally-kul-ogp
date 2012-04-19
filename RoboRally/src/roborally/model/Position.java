package roborally.model;

import java.util.*;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

// TODO: Maak van Position een "Value klasse"
public class Position {

    public Position(long x, long y, Board board)
            throws IllegalArgumentException {
        this.BOARD = board;
        if (!board.isValidPosition(x, y))
            throw new IllegalArgumentException();
        this.X = x;
        this.Y = y;
        elements = new HashSet<Element>();
        this.isTerminated = false;
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

        if (elements.contains(new Wall())) {
            boolean elementsContainsOtherWall = true;
            for (Element elem : elements)
                if (element == elem)
                    elementsContainsOtherWall = false;
            if (elementsContainsOtherWall)
                return false;
        }

        if (element instanceof Wall && elements.size() != 0)
            return false;

        boolean elementsContainsOtherRobot = false;
        for (Element elem : elements)
            if (elem instanceof Robot && !element.equals(elem))
                elementsContainsOtherRobot = true;
        if (element instanceof Robot && elementsContainsOtherRobot)
            return false;

        return true;
    }

    public boolean hasSameCoordinates(Position position) {
        return (X == position.X && Y == position.Y);
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
        this.isTerminated = true;
    }

    @Raw
    @Basic
    public boolean isTerminated() {
        return isTerminated;
    }

    public final long X;
    public final long Y;
    public final Board BOARD;

    private Set<Element> elements;
    private boolean isTerminated;
}
