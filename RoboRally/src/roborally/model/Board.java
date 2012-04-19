package roborally.model;

import be.kuleuven.cs.som.annotate.*;
import java.util.*;

public class Board {

    public Board(long width, long height) {
        this.isTerminated = false;
        this.WIDTH = width;
        this.HEIGHT = height;
    }

    public void merge(Board board2) {
        Set<Position> occupiedPositionsBoard2 = new HashSet<Position>();
        occupiedPositionsBoard2.addAll(board2.occupiedPositions);
        for (Position occupiedPosition2 : occupiedPositionsBoard2) {
            if (isValidPosition(occupiedPosition2)) {
                Position position = new Position(occupiedPosition2.X,
                        occupiedPosition2.Y, this);
                for (Element elem : occupiedPosition2.getElements()) {
                    putElement(position, elem);
                }
            }
        }
        board2.terminate();
    }

    public void putElement(Position position, Element element) {
        if (isValidPosition(position))
            if (occupiedPositions.contains(position)) {
                for (Position occupiedPosition : occupiedPositions)
                    if (position.equals(occupiedPosition))
                        position = occupiedPosition;
                position.addElement(element);
            } else {
                addOccupiedPosition(position);
                putElement(position, element);
            }
    }

    public void removeElement(Element element) {
        element.getPosition().removeElement(element);
    }

    public Set<Element> getElementsAt(Position position) {
        for (Position occupiedPosition : occupiedPositions)
            if (position.equals(occupiedPosition))
                return occupiedPosition.getElements();
        return new HashSet<Element>();
    }

    // TODO: Testen getelementsof
    public Set<Element> getElementsOf(Class<?> type) {
        Set<Element> elements = new HashSet<Element>();
        for (Position occupiedPosition : occupiedPositions) {
            for (Element element : occupiedPosition.getElements()) {
                if (type.isAssignableFrom(element.getClass()))
                    elements.add(element);
            }
        }
        return elements;
    }

    public void addOccupiedPosition(Position position) {
        if (isValidPosition(position))
            occupiedPositions.add(position);
    }

    public int getNumberOfOccupiedPositions() {
        return occupiedPositions.size();
    }

    public Set<Position> getOccupiedPositions() {
        return occupiedPositions;
    }

    public boolean isValidPosition(Position position) {
        if (position != null)
            return isValidPosition(position.X, position.Y);
        return false;
    }

    public boolean isValidPosition(long x, long y) {
        return x >= 0 && x <= WIDTH && y >= 0 && y <= HEIGHT;
    }

    public boolean isOccupiedPosition(Position position) {
        return occupiedPositions.contains(position);
    }

    public void removePosition(Position position) {
        occupiedPositions.remove(position);
    }

    public void terminate() {
        Set<Position> occupiedPositionIterator = new HashSet<Position>();
        occupiedPositionIterator.addAll(occupiedPositions);
        for (Position pos : occupiedPositionIterator)
            pos.terminate();
        this.isTerminated = true;
    }

    @Raw
    @Basic
    public boolean isTerminated() {
        return isTerminated;
    }

    public final long WIDTH;
    public final long HEIGHT;

    private boolean isTerminated;
    private final Set<Position> occupiedPositions = new HashSet<Position>();
}