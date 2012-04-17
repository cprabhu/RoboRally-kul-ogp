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

    }

    public void putElement(Position position, Element element) {

    }

    public void removeElement(Element element) {

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
    
    public boolean isOccupiedPosition(Position position){
        return occupiedPositions.contains(position);
    }
    
    public void removePosition(Position position){
        occupiedPositions.remove(position);
    }
    
    public void terminate() {
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