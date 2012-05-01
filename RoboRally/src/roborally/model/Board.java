package roborally.model;

import be.kuleuven.cs.som.annotate.*;
import java.util.*;

// TODO: all aspects related to placement of walls, robots, batteries on
// boards: defensively

/**
 * @author Ben Adriaenssens <ben.adriaenssens@student.kuleuven.be>, Toon Nolten <toon.nolten@student.kuleuven.be>
 */
public class Board {

    public Board(long width, long height) throws IllegalArgumentException {
        this.isTerminated = false;
        if (width < 0 || height < 0)
            throw new IllegalArgumentException();
        this.WIDTH = width;
        this.HEIGHT = height;
    }

    public void merge(Board board2) {
        if (board2 != null) {
            Set<Position> occupiedPositionsBoard2 = new HashSet<Position>();
            occupiedPositionsBoard2.addAll(board2.occupiedPositions);
            for (Position occupiedPosition2 : occupiedPositionsBoard2) {
                if (isValidPosition(occupiedPosition2)) {
                    Position position = Position.newPosition(
                            occupiedPosition2.X, occupiedPosition2.Y, this);
                    for (Element elem : occupiedPosition2.getElements()) {
                        if (!position.canContainElement(elem)) {
                            // NOTE: while(it.hasNext()) komt niet aan het
                            // einde.
                            for (Position neighbour : occupiedPosition2
                                    .getNeighbours()) {
                                putElement(neighbour, elem);
                                break;
                            }

                        } else
                            putElement(position, elem);
                    }
                }
            }
            board2.terminate();
        }
    }

    public void putElement(Position position, Element element) {
        if (isValidPosition(position))
            if (occupiedPositions.contains(position)) {
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
        if (isOccupiedPosition(position))
            return position.getElements();
        return new HashSet<Element>();
    }

    public Set<Element> getElementsOf(Class<?> type) {
        Set<Element> elements = new HashSet<Element>();
        for (Position occupiedPosition : occupiedPositions) {
            elements.addAll(occupiedPosition.getElementsOf(type));
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