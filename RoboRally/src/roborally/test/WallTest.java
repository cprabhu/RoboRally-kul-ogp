package roborally.test;

import roborally.model.*;
import static org.junit.Assert.*;

import org.junit.*;
import org.junit.Test;

import roborally.model.Element;
import roborally.model.Position;

public class WallTest {

    @Before
    public void setUp() {
        wall = new Wall();
        wall2 = new Wall(POSITION);
    }

    @Test
    public void testEqualsObject() {
        Element wall3 = new Wall();
        Element wall4 = new Wall(new Position(4, 1, BOARD));

        assertTrue(wall.equals(wall2));
        assertTrue(wall.equals(wall3));
        assertTrue(wall.equals(wall4));
        assertTrue(wall2.equals(wall3));
        assertTrue(wall2.equals(wall4));
    }

    @Test
    public void testHashcode() {
        assertEquals(0, wall.hashCode());
        assertEquals(0, wall2.hashCode());
    }

    @Test
    public void testWall() {
        assertNotNull(wall);
        assertNotNull(wall2);

        testEqualsObject();
    }

    @Test
    public void testSetPosition() {
        Position position = new Position(3, 2, BOARD);
        
        wall.setPosition(position);

        assertEquals(position, wall.getPosition());
        assertTrue(position.containsElement(wall));
    }

    @Test
    public void testGetPosition() {
        assertFalse(wall.getPosition() == POSITION);
        assertEquals(POSITION, wall2.getPosition());
    }

    @Test
    public void testTerminate() {
        assertFalse(wall.isTerminated());
        assertFalse(wall2.isTerminated());

        wall.terminate();
        wall2.terminate();

        assertTrue(wall.isTerminated());
        assertTrue(wall2.isTerminated());
        assertFalse(POSITION.containsElement(wall2));
        assertFalse(BOARD.isOccupiedPosition(POSITION)
                && POSITION.getElements().isEmpty());
    }

    @Test
    public void testIsTerminated() {
        wall.terminate();

        assertTrue(wall.isTerminated());
        assertFalse(wall2.isTerminated());
    }

    private Wall wall;
    private Wall wall2;
    private final Board BOARD = new Board(4, 5);
    private final Position POSITION = new Position(2, 3, BOARD);
}
