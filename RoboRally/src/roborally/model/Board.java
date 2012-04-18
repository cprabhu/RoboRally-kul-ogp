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
        position.addElement(element);
        if (position.getElements().contains(element)) {
            occupiedPositions.add(position);
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

    public int getNumberOfOccupiedPositions() {
        return occupiedPositions.size();
    }

    public boolean isValidPosition(Position position) {
        return isValidPosition(position.X, position.Y);
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
        for (Position pos : occupiedPositions)
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
    private Set<Position> occupiedPositions = new HashSet<Position>();
}