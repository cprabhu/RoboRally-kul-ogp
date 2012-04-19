package roborally.test;

import static org.junit.Assert.*;

import org.junit.*;

import roborally.model.*;
import roborally.model.Energy.unitOfPower;

public class BoardTest {

    @Before
    public void setUp() throws Exception {
        board = new Board(535, 364);
    }

    @Test
    public void testBoard() {
        assertNotNull(board);

        Position legalPosition = new Position(346, 210, board);
        assertTrue(board.isValidPosition(legalPosition));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBoardFail() {
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
        Energy energy = new Energy(100, unitOfPower.Ws);
        Position emptyBoard = new Position(2, 4, board);
        Position occupiedBoard = new Position(3, 6, board);
        board.putElement(occupiedBoard, new Battery(occupiedBoard, energy));
        Position occupied2Board = new Position(4, 14, board);
        board.putElement(occupied2Board, new Battery(occupied2Board, energy));
        Position obstacleBoard = new Position(5, 25, board);
        board.putElement(obstacleBoard, new Wall(obstacleBoard));

        Board board2 = new Board(600, 234);
        Position overlapEmptyBoard2 = new Position(2, 4, board2);
        board2.putElement(overlapEmptyBoard2, new Battery(overlapEmptyBoard2,
                energy));
        Position overlapOccupiedBoard2 = new Position(3, 6, board2);
        board2.putElement(overlapOccupiedBoard2,
                new Wall(overlapOccupiedBoard2));
        Position overlapOccupied2Board2 = new Position(4, 14, board2);
        board2.putElement(overlapOccupied2Board2, new Battery(
                overlapOccupied2Board2, energy));
        Position overlapObstacleBoard2 = new Position(5, 25, board2);
        board2.putElement(overlapObstacleBoard2, new Battery(
                overlapObstacleBoard2, energy));
        Position noOverlapBoard2 = new Position(586, 215, board2);
        board2.putElement(noOverlapBoard2, new Battery(noOverlapBoard2, energy));

        board.merge(board2);

        assertEquals(1, board.getElementsAt(emptyBoard).size());
        assertEquals(1, board.getElementsAt(occupiedBoard).size());
        assertEquals(2, board.getElementsAt(occupied2Board).size());
        assertEquals(1, board.getElementsAt(obstacleBoard).size());
        assertFalse(board.isOccupiedPosition(noOverlapBoard2));
        assertTrue(board2.isTerminated());
        assertTrue(overlapEmptyBoard2.isTerminated());
        assertTrue(overlapOccupiedBoard2.isTerminated());
        assertTrue(overlapOccupied2Board2.isTerminated());
        assertTrue(overlapObstacleBoard2.isTerminated());
        assertTrue(noOverlapBoard2.isTerminated());

    }

    @Test
    public void testPutElement() {
        Position position = new Position(33, 57, board);
        Energy energy = new Energy(100, unitOfPower.Ws);
        Element element1 = new Battery(position, energy);
        Element element2 = new Battery(position, energy);
        Position newPosition = new Position(15, 254, board);

        board.putElement(newPosition, element1);

        assertTrue(newPosition.containsElement(element1));
        assertFalse(position.containsElement(element1));
        assertTrue(element1.getPosition().equals(newPosition));
        assertFalse(position.isTerminated());

        board.putElement(newPosition, element2);

        assertTrue(newPosition.containsElement(element2));
        assertFalse(position.containsElement(element2));
        assertTrue(element2.getPosition().equals(newPosition));
        assertTrue(position.isTerminated());
    }

    @Test
    public void testRemoveElement() {
        Position position = new Position(33, 57, board);
        Element element = new Battery();
        board.putElement(position, element);

        assertEquals(1, board.getNumberOfOccupiedPositions());

        board.removeElement(element);

        assertEquals(0, board.getNumberOfOccupiedPositions());
        assertTrue(position.isTerminated());
        assertFalse(element.isTerminated());
        assertNull(element.getPosition());

    }

    @Test
    public void testGetElementsAt() {
        Position position = new Position(33, 57, board);
        Element element = new Battery();
        board.putElement(position, element);

        assertTrue(board.getElementsAt(position).contains(element));
        assertEquals(1, board.getElementsAt(position).size());

        board.removeElement(element);

        assertFalse(element.equals(board.getElementsAt(position)));
        assertEquals(0, board.getElementsAt(position).size());
    }

    @Test
    public void testGetNumberOfOccupiedPositions() {
        Position occupied1 = new Position(3, 6, board);
        Position occupied2 = new Position(4, 14, board);
        Position occupied3 = new Position(5, 25, board);

        assertEquals(0, board.getNumberOfOccupiedPositions());

        board.putElement(occupied1, new Wall());

        assertEquals(1, board.getNumberOfOccupiedPositions());

        board.putElement(occupied2, new Wall());

        assertEquals(2, board.getNumberOfOccupiedPositions());

        board.putElement(occupied3, new Wall());

        assertEquals(3, board.getNumberOfOccupiedPositions());
    }

    @Test
    public void testGetOccupiedPositions() {
        Position occupied1 = new Position(3, 6, board);
        Position occupied2 = new Position(4, 14, board);
        Position occupied3 = new Position(5, 25, board);

        assertEquals(0, board.getOccupiedPositions().size());

        board.putElement(occupied1, new Wall());

        assertTrue(board.getOccupiedPositions().contains(occupied1));
        assertFalse(board.getOccupiedPositions().contains(occupied2));
        assertFalse(board.getOccupiedPositions().contains(occupied3));

        board.putElement(occupied2, new Wall());

        assertTrue(board.getOccupiedPositions().contains(occupied1));
        assertTrue(board.getOccupiedPositions().contains(occupied2));
        assertFalse(board.getOccupiedPositions().contains(occupied3));

        board.putElement(occupied3, new Wall());

        assertTrue(board.getOccupiedPositions().contains(occupied1));
        assertTrue(board.getOccupiedPositions().contains(occupied2));
        assertTrue(board.getOccupiedPositions().contains(occupied3));
    }

    @Test
    public void testIsValidPosition() {
        Position position = new Position(33, 57, board);

        assertTrue(board.isValidPosition(position));
        assertFalse(board.isValidPosition(null));
    }

    @Test
    public void testIsValidPosition2() {
        assertTrue(board.isValidPosition(315, 243));

        assertFalse(board.isValidPosition(236, -17));
        assertFalse(board.isValidPosition(714, 156));
        assertFalse(board.isValidPosition(524, 425));
        assertFalse(board.isValidPosition(-42, 357));
    }

    @Test
    public void testTerminate() {
        Energy energy = new Energy(100, unitOfPower.Ws);
        Position position = new Position(3, 6, board);
        board.putElement(position, new Battery(position, energy));

        assertFalse(board.isTerminated());
        assertFalse(position.isTerminated());

        board.terminate();

        assertEquals(0, board.getNumberOfOccupiedPositions());
        assertTrue(board.isTerminated());
        assertNull(position.getElements());
        assertTrue(position.isTerminated());
    }

    @Test
    public void testIsTerminated() {
        assertFalse(board.isTerminated());
        board.terminate();
        assertTrue(board.isTerminated());
    }

    private Board board;
}