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
    }
    
    @Test
    public void testHashCode() {
        Position position = new Position(4,2, board1);
        assertEquals(board1.hashCode() + 11, position.hashCode());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testPosition() {
        Position position = new Position(3, 2, board1);
        
        assertNotNull(position);
        assertEquals(3, position.X);
        assertEquals(2, position.Y);
        
        assertEquals(0, position.getElements().size());
        
        Position illegalPosition = new Position(687, 16654, board1);
        assertNull(illegalPosition);
    }

    @Test
    // TODO: Test uitbreiden met toegevoegde Elements.
    public void testGetElements() {
        Position position = new Position(4, 2, board1);
        
        assertEquals(0, position.getElements().size());
    }
    
    @Test
    // TODO: Test schrijven.
    public void testAddElement() {
        fail("Not yet implemented!");
    }
    
    @Test
    // TODO: Test schrijven.
    public void testRemoveElement() {
        fail("Not yet implemented!");
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

    Board board1;
    Board board2;
}
