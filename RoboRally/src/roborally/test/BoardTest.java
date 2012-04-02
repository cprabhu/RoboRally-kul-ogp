package roborally.test;

import static org.junit.Assert.*;

import org.junit.*;

import roborally.model.*;

public class BoardTest {

    @Before
    public void setUp() throws Exception {
        board = new Board(535, 364);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBoard() {
        assertNotNull(board);

        Position legalPosition = new Position(346, 210, board);
        assertTrue(board.isValidPosition(legalPosition));

        Position illegalPositionUp = new Position(236, -17, board);
        assertFalse(board.isValidPosition(illegalPositionUp));
        Position illegalPositionRight = new Position(714, 156, board);
        assertFalse(board.isValidPosition(illegalPositionRight));
        Position illegalPositionDown = new Position(524, 425, board);
        assertFalse(board.isValidPosition(illegalPositionDown));
        Position illegalPositionLeft = new Position(-42, 357, board);
        assertFalse(board.isValidPosition(illegalPositionLeft));
    }

    @Test
    public void testMerge() {
        fail("Not yet implemented");
    }

    @Test
    public void testPutElement() {
        fail("Not yet implemented");
    }

    @Test
    public void testRemoveElement() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetElementsAt() {
        fail("Not yet implemented");
    }

    @Test
    public void testIsValidPosition() {
        assertTrue(board.isValidPosition(315, 243));

        assertFalse(board.isValidPosition(236, -17));
        assertFalse(board.isValidPosition(714, 156));
        assertFalse(board.isValidPosition(524, 425));
        assertFalse(board.isValidPosition(-42, 357));
    }

    @Test
    // TODO: Test na het toevoegen van elementen.
    public void testTerminate() {
        assertFalse(board.isTerminated());
        board.terminate();
        assertEquals(0, board.getNumberOfOccupiedPositions());
        assertTrue(board.isTerminated());
    }

    @Test
    public void testIsTerminated() {
        assertFalse(board.isTerminated());
        board.terminate();
        assertTrue(board.isTerminated());
    }

    private Board board;
}