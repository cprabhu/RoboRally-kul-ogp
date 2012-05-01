package roborally.test;

import roborally.model.*;
import roborally.model.Energy.unitOfPower;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Ben Adriaenssens <ben.adriaenssens@student.kuleuven.be>, Toon Nolten <toon.nolten@student.kuleuven.be>
 */
public class PositionTest {

    @Before
    public void setUp() {
        board1 = new Board(7, 4);
        board2 = new Board(15, 6);
        position1Board1 = Position.newPosition(2, 4, board1);
        position2Board1 = Position.newPosition(3, 4, board1);
        position3Board1 = Position.newPosition(3, 3, board1);
    }

    @Test
    public void testHashCode() {
        Position position = Position.newPosition(4, 2, board1);
        assertEquals(board1.hashCode() + 11, position.hashCode());
    }

    @Test
    public void testPosition() {
        Position position = Position.newPosition(3, 2, board1);

        assertNotNull(position);
        assertEquals(3, position.X);
        assertEquals(2, position.Y);

        assertEquals(0, position.getElements().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalPosition() {
        Position illegalPosition = Position.newPosition(687, 16654, board1);
        assertNull(illegalPosition);
    }

    @Test
    public void testHasSameCoordinates() {
        Position position4Board1 = Position.newPosition(2, 1, board1);
        Position position5Board1 = Position.newPosition(2, 4, board1);
        Position position1Board2 = Position.newPosition(2, 4, board2);
        Position position2Board2 = Position.newPosition(3, 4, board2);

        assertFalse(position1Board1.hasSameCoordinates(position2Board1));
        assertFalse(position1Board1.hasSameCoordinates(position3Board1));
        assertFalse(position1Board1.hasSameCoordinates(position4Board1));
        assertTrue(position1Board1.hasSameCoordinates(position5Board1));
        assertTrue(position1Board1.hasSameCoordinates(position1Board2));
        assertFalse(position1Board1.hasSameCoordinates(position2Board2));
    }

    @Test
    public void testManhattanDistance() {
        double epsilon = 0.01;
        assertEquals(0, position1Board1.manhattanDistance(position1Board1),
                epsilon);
        assertEquals(1, position1Board1.manhattanDistance(position2Board1),
                epsilon);
        assertEquals(2, position1Board1.manhattanDistance(position3Board1),
                epsilon);

        Position illegalPosition = Position.newPosition(1, 6, board2);
        try {
            position1Board1.manhattanDistance(illegalPosition);
        } catch (IllegalArgumentException exc) {
            System.err
                    .println("testManhattanDistance: This exception is expected");
        }
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
        position3Board1.addElement(null);

        assertEquals(1, position1Board1.getElements().size());
        assertEquals(1, position2Board1.getElements().size());
        assertEquals(0, position3Board1.getElements().size());

        position1Board1.terminate();
        position1Board1.addElement(new Wall(position1Board1));

        assertNull(position1Board1.getElements());
    }

    @Test
    public void testRemoveElement() {
        Wall position1Board1Wall = new Wall(position1Board1);
        Wall position2Board1Wall = new Wall(position2Board1);
        Wall position3Board1Wall = new Wall(position3Board1);
        position1Board1.addElement(position1Board1Wall);
        position2Board1.addElement(position2Board1Wall);
        position3Board1.addElement(position3Board1Wall);

        assertEquals(1, position1Board1.getElements().size());
        assertEquals(1, position2Board1.getElements().size());
        assertEquals(1, position3Board1.getElements().size());

        position1Board1.removeElement(position1Board1Wall);
        position2Board1.removeElement(position2Board1Wall);
        position3Board1.removeElement(position3Board1Wall);

        assertNull(position1Board1.getElements());
        assertNull(position2Board1.getElements());
        assertNull(position3Board1.getElements());
    }

    @Test
    public void testCanContainElement() {
        Element wall = new Wall();
        Element battery1 = new Battery();
        Element battery2 = new Battery();
        Element robot1 = new Robot(new Energy(100, unitOfPower.Ws),
                Orientation.RIGHT);
        Element robot2 = new Robot(new Energy(100, unitOfPower.Ws),
                Orientation.RIGHT);

        assertTrue(position1Board1.canContainElement(wall));
        assertTrue(position1Board1.canContainElement(battery1));
        assertTrue(position1Board1.canContainElement(robot1));

        assertTrue(position2Board1.canContainElement(wall));
        assertTrue(position2Board1.canContainElement(battery1));
        assertTrue(position2Board1.canContainElement(robot1));

        assertTrue(position3Board1.canContainElement(wall));
        assertTrue(position3Board1.canContainElement(battery1));
        assertTrue(position3Board1.canContainElement(robot1));

        wall.setPosition(position1Board1);

        assertTrue(position1Board1.canContainElement(wall));
        assertFalse(position1Board1.canContainElement(battery1));
        assertFalse(position1Board1.canContainElement(robot1));

        robot2.setPosition(position2Board1);

        assertTrue(position2Board1.canContainElement(battery1));
        assertTrue(position2Board1.canContainElement(robot2));

        assertFalse(position2Board1.canContainElement(wall));
        assertFalse(position2Board1.canContainElement(robot1));

        battery2.setPosition(position3Board1);

        assertTrue(position3Board1.canContainElement(battery1));
        assertTrue(position3Board1.canContainElement(battery2));
        assertTrue(position3Board1.canContainElement(robot1));
        assertTrue(position3Board1.canContainElement(robot2));

        assertFalse(position3Board1.canContainElement(wall));

        position3Board1.terminate();

        assertFalse(position3Board1.canContainElement(wall));
        assertFalse(position3Board1.canContainElement(battery1));
        assertFalse(position3Board1.canContainElement(battery2));
        assertFalse(position3Board1.canContainElement(robot1));
        assertFalse(position3Board1.canContainElement(robot2));
    }

    @Test
    public void testCanContainType() {
        Element wall = new Wall();
        Element battery = new Battery();
        Element robot = new Robot(new Energy(100, unitOfPower.Ws),
                Orientation.RIGHT);

        assertTrue(position1Board1.canContainType(Wall.class));
        assertTrue(position1Board1.canContainType(Battery.class));
        assertTrue(position1Board1.canContainType(Robot.class));

        assertTrue(position2Board1.canContainType(Wall.class));
        assertTrue(position2Board1.canContainType(Battery.class));
        assertTrue(position2Board1.canContainType(Robot.class));

        assertTrue(position3Board1.canContainType(Wall.class));
        assertTrue(position3Board1.canContainType(Battery.class));
        assertTrue(position3Board1.canContainType(Robot.class));

        wall.setPosition(position1Board1);
        battery.setPosition(position2Board1);
        robot.setPosition(position3Board1);

        assertFalse(position1Board1.canContainType(Wall.class));
        assertFalse(position1Board1.canContainType(Battery.class));
        assertFalse(position1Board1.canContainType(Robot.class));

        assertFalse(position2Board1.canContainType(Wall.class));
        assertTrue(position2Board1.canContainType(Battery.class));
        assertTrue(position2Board1.canContainType(Robot.class));

        assertFalse(position3Board1.canContainType(Wall.class));
        assertTrue(position3Board1.canContainType(Battery.class));
        assertFalse(position3Board1.canContainType(Robot.class));

        position1Board1.terminate();

        assertFalse(position1Board1.canContainType(Wall.class));
        assertFalse(position1Board1.canContainType(Battery.class));
        assertFalse(position1Board1.canContainType(Robot.class));
    }

    @Test
    public void testEqualsObject() {
        Position position1Board1 = Position.newPosition(2, 4, board1);
        Position position2Board1 = Position.newPosition(2, 4, board1);
        Position position3Board1 = Position.newPosition(3, 3, board1);
        Position position1Board2 = Position.newPosition(2, 4, board2);
        Position position2Board2 = Position.newPosition(7, 6, board2);

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
