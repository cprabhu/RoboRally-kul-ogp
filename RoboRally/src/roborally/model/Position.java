package roborally.model;

import java.util.*;

public class Position {

    public Position(long x, long y, Board board)
            throws IllegalArgumentException {
        this.board = board;
        if (!board.isValidPosition(x, y))
            throw new IllegalArgumentException();
        this.X = x;
        this.Y = y;
    }

    public void addElement(Element element) {
        elements.add(element);
    }

    public void removeElement(Element element) {
        elements.remove(element);
    }

    public Set<Element> getElements() {
        return elements;
    }

    public boolean equals(Object o) {
        return hashCode() == o.hashCode();
    }

    public int hashCode() {
        long positionNumber = X + board.WIDTH * (Y - 1);
        return board.hashCode() + (int) positionNumber;
    }

    public final long X;
    public final long Y;

    private Set<Element> elements = new HashSet<Element>();
    private final Board board;

}
