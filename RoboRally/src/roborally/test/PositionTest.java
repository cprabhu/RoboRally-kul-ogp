package roborally.test;

import roborally.model.*;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PositionTest {

    @Before
    public void setUp() {
        board1 = new Board(7, 4);
        board2 = new Board(15, 6);
        position1Board1 = new Position(2, 4, board1);
        position2Board1 = new Position(3, 4, board1);
        position3Board1 = new Position(3, 3, board1);
    }

    @Test
    public void testHashCode() {
        Position position = new Position(4, 2, board1);
        assertEquals(board1.hashCode() + 11, position.hashCode());
    }

    @Test
    public void testPosition() {
        Position position = new Position(3, 2, board1);

        assertNotNull(position);
        assertEquals(3, position.X);
        assertEquals(2, position.Y);

        assertEquals(0, position.getElements().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalPosition() {
        Position illegalPosition = new Position(687, 16654, board1);
        assertNull(illegalPosition);
    }

    @Test
    public void testHasSameCoordinates() {
        Position position4Board1 = new Position(2, 1, board1);
        Position position5Board1 = new Position(2, 4, board1);

        assertFalse(position1Board1.hasSameCoordinates(position2Board1));
        assertFalse(position1Board1.hasSameCoordinates(position3Board1));
        assertFalse(position1Board1.hasSameCoordinates(position4Board1));
        assertTrue(position1Board1.hasSameCoordinates(position5Board1));
    }

    @Test
    public void testGetElements() {
        Wall element1 = new Wall(position1Board1);
        Wall element2 = new Wall(position2Board1);
        Wall element3 = new Wall(position3Board1);
        position1Board1.addElement(element1);
        position2Board1.addElement(element2);
        position3Board1.addElement(element3);

        assertTrue(position1Board1.getElements().contains(element1));
        assertTrue(position2Board1.getElements().contains(element2));
        assertTrue(position3Board1.getElements().contains(element3));
    }

    @Test
    public void testAddElement() {
        assertEquals(0, position1Board1.getElements().size());
        assertEquals(0, position2Board1.getElements().size());
        assertEquals(0, position3Board1.getElements().size());

        position1Board1.addElement(new Wall(position1Board1));
        position2Board1.addElement(new Wall(position2Board1));
        position3Board1.addElement(new Wall(position3Board1));

        assertEquals(1, position1Board1.getElements().size());
        assertEquals(1, position2Board1.getElements().size());
        assertEquals(1, position3Board1.getElements().size());

        position1Board1.terminate();
        position1Board1.addElement(new Wall(position1Board1));

        assertNull(position1Board1.getElements());
    }

    @Test
    public void testRemoveElement() {
        position1Board1.addElement(new Wall(position1Board1));
        position2Board1.addElement(new Wall(position2Board1));
        position3Board1.addElement(new Wall(position3Board1));

        assertEquals(1, position1Board1.getElements().size());
        assertEquals(1, position2Board1.getElements().size());
        assertEquals(1, position3Board1.getElements().size());

        position1Board1.removeElement(new Wall(position1Board1));
        position2Board1.removeElement(new Wall(position2Board1));
        position3Board1.removeElement(new Wall(position3Board1));

        assertNull(position1Board1.getElements());
        assertNull(position2Board1.getElements());
        assertNull(position3Board1.getElements());
    }

    @Test
    public void testCanContainElement() {
        Wall element1 = new Wall();

        assertTrue(position1Board1.canContainElement(element1));
        assertTrue(position2Board1.canContainElement(element1));

        element1.setPosition(position1Board1);

        assertFalse(position1Board1.canContainElement(element1));

        position2Board1.terminate();

        assertFalse(position1Board1.canContainElement(element1));
    }

    @Test
    public void testEqualsObject() {
        Position position1Board1 = new Position(2, 4, board1);
        Position position2Board1 = new Position(2, 4, board1);
        Position position3Board1 = new Position(3, 3, board1);
        Position position1Board2 = new Position(2, 4, board2);
        Position position2Board2 = new Position(7, 6, board2);

        assertTrue(position1Board1.equals(position1Board1));
        assertTrue(position1Board1.equals(position2Board1));

        assertFalse(position1Board1.equals(position3Board1));
        assertFalse(position1Board1.equals(position1Board2));
        assertFalse(position1Board1.equals(position2Board2));
    }

    @Test
    public void testIsEmpty() {
        assertTrue(position1Board1.isEmpty());
    }

    @Test
    public void testTerminate() {
        position1Board1.terminate();

        assertTrue(position1Board1.isTerminated());
    }

    @Test
    public void testIsTerminated() {
        assertFalse(position1Board1.isTerminated());

        position1Board1.terminate();

        assertTrue(position1Board1.isTerminated());
    }

    Board board1;
    Board board2;
    Position position1Board1;
    Position position2Board1;
    Position position3Board1;
}
