package roborally.test;

import static org.junit.Assert.*;

import org.junit.*;

import roborally.model.*;

public class BoardTest {
    
    @Before
    public void setUp() throws Exception {
        board = new Board(535, 364);
    }

    @Test
    public void testBoard() {
        assertNotNull(board);
        long[] position = {535, 364};
        assertTrue(board.isValidPosition(position));
        long[] illegalPositionUp = {298, -53};
        assertFalse(board.isValidPosition(illegalPositionUp));
        long[] illegalPositionRight = {647, 53};
        assertFalse(board.isValidPosition(illegalPositionRight));
        long[] illegalPositionDown = {46, 687};
        assertFalse(board.isValidPosition(illegalPositionDown));
        long[] illegalPositionLeft = {-64, 354};
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
    public void testGetElements() {
        fail("Not yet implemented");
    }

    @Test
    public void testIsValidPosition() {
        long[] positionTrue = {354, 251};
        assertTrue(board.isValidPosition(positionTrue));
        long[] positionFalse = {146876847, -56684834};
        assertFalse(board.isValidPosition(positionFalse));
    }

    @Test
    public void testTerminate() {
        assertFalse(board.isTerminated());
        board.terminate();
        assertEquals(0, board.getElements(Element.class).size());
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