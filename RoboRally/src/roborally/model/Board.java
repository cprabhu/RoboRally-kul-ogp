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

    public void putElement(long[] position, Element element) {

    }

    public void removeElement(Element element) {

    }

    public Set<Element> getElements(Class<Element> type) {
        Set<Element> elements = new HashSet<Element>();
        for (Set<Element> elementSet : occupiedPositions.values()) {
            for (Element element : elementSet) {
                if (type.isAssignableFrom(element.getClass()))
                    elements.add(element);
            }

        }
        return elements;
    }

    public boolean isValidPosition(long[] position) {
        return position[0] >= 0 && position[0] <= WIDTH && position[1] >= 0
                && position[1] <= HEIGHT;
    }

    public void terminate() {
        this.isTerminated = true;
    }

    @Raw
    @Basic
    public boolean isTerminated() {
        return isTerminated;
    }

    private boolean isTerminated;
    private final long WIDTH;
    private final long HEIGHT;
    private Map<long[], Set<Element>> occupiedPositions = new HashMap<long[], Set<Element>>();
}