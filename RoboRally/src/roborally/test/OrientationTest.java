package roborally.test;

import roborally.model.*;
import roborally.model.auxiliary.Energy;
import roborally.model.auxiliary.Orientation;
import roborally.model.auxiliary.Position;
import roborally.model.auxiliary.Energy.unitOfPower;

import static org.junit.Assert.*;

import org.junit.*;

/**
 * @author Ben Adriaenssens (ben.adriaenssens@student.kuleuven.be) - WtkCws,
 *         Toon Nolten (toon.nolten@student.kuleuven.be) - CwsElt.
 */
public class OrientationTest {

    @Before
    public void setUp() {
        up = Orientation.UP;
        right = Orientation.RIGHT;
        down = Orientation.DOWN;
        left = Orientation.LEFT;
    }

    @Test
    public void testTurnClockwise90() {
        assertEquals(Orientation.RIGHT, up.turnClockwise90());
        assertEquals(Orientation.DOWN, right.turnClockwise90());
        assertEquals(Orientation.LEFT, down.turnClockwise90());
        assertEquals(Orientation.UP, left.turnClockwise90());
    }

    @Test
    public void testTurnCounterClockwise90() {
        assertEquals(Orientation.LEFT, up.turnCounterClockwise90());
        assertEquals(Orientation.UP, right.turnCounterClockwise90());
        assertEquals(Orientation.RIGHT, down.turnCounterClockwise90());
        assertEquals(Orientation.DOWN, left.turnCounterClockwise90());
    }

    @Test
    public void testTurn180() {
        assertEquals(Orientation.DOWN, up.turn180());
        assertEquals(Orientation.LEFT, right.turn180());
        assertEquals(Orientation.UP, down.turn180());
        assertEquals(Orientation.RIGHT, left.turn180());
    }

    @Test
    public void testGetEnergyRequiredForOrientation() {
        Energy upEnergy = new Energy(0, unitOfPower.Ws);
        Energy quarterTurnEnergy = new Energy(100, unitOfPower.Ws);
        Energy halfTurnEnergy = new Energy(200, unitOfPower.Ws);
        Energy energyToTurn = new Energy(100, unitOfPower.Ws);

        assertEquals(
                0,
                up.getEnergyRequiredForOrientation(Orientation.UP, energyToTurn)
                        .compareTo(upEnergy));
        assertEquals(
                0,
                up.getEnergyRequiredForOrientation(Orientation.RIGHT,
                        energyToTurn).compareTo(quarterTurnEnergy));
        assertEquals(
                0,
                up.getEnergyRequiredForOrientation(Orientation.LEFT,
                        energyToTurn).compareTo(quarterTurnEnergy));
        assertEquals(
                0,
                up.getEnergyRequiredForOrientation(Orientation.DOWN,
                        energyToTurn).compareTo(halfTurnEnergy));

    }

    @Test
    public void testNextPosition() {
        Board board = new Board(5, 7);
        Position currentPosition = Position.newPosition(3, 4, board);
        Position testPosition1 = Position.newPosition(3, 3, board);
        Position testPosition2 = Position.newPosition(4, 4, board);
        Position testPosition3 = Position.newPosition(3, 5, board);
        Position testPosition4 = Position.newPosition(2, 4, board);
        Position testUpEdgePosition = Position.newPosition(3, 0, board);
        Position testRightEdgePosition = Position.newPosition(5, 3, board);
        Position testDownEdgePosition = Position.newPosition(3, 7, board);
        Position testLeftEdgePosition = Position.newPosition(0, 4, board);

        assertTrue(testPosition1.equals(up.nextPosition(currentPosition)));
        assertTrue(testPosition2.equals(right.nextPosition(currentPosition)));
        assertTrue(testPosition3.equals(down.nextPosition(currentPosition)));
        assertTrue(testPosition4.equals(left.nextPosition(currentPosition)));

        try {
            assertNull(up.nextPosition(testUpEdgePosition));
            assertNull(right.nextPosition(testRightEdgePosition));
            assertNull(down.nextPosition(testDownEdgePosition));
            assertNull(left.nextPosition(testLeftEdgePosition));
        } catch (IllegalArgumentException e) {
            System.err.println("testNextPosition: This is to be expected a"
                    + "position not on the board cannot be contstructed."
                    + " No worries.");
        }
    }

    private Orientation up;
    private Orientation right;
    private Orientation down;
    private Orientation left;
}
