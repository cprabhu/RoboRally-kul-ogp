package roborally.test;

import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;

import roborally.IRobot;
import roborally.model.Robot;

public class RobotTest {
    IRobot robot;
    Double epsilon = 0.1;
    
    @Before
    public void setUp() {
        robot = new Robot(7, 70, Robot.LEFT, -1300);
    }
    
    @Test
    public void testRobotNoProblem() {
        assertNotNull(robot);
        assertEquals(7, robot.getX());
        assertEquals(70, robot.getY());
        assertEquals(Robot.LEFT, robot.getOrientation());
        assertEquals(robot.getMaxEnergy(), robot.getEnergy(), epsilon);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testRobotIllegalPosition(){
        robot = new Robot(-7, 70, Robot.LEFT, 666);
        assertNull(robot);
    }
    
    @Test
    public void testRobotIllegalOrientation(){
        robot = new Robot(7, 70, 40, 666);
        assertNotNull(robot);
        assertEquals(Robot.UP, robot.getOrientation());
    }

    @Test
    public void testMove() {
        robot.move();
        assertEquals(6, robot.getX());
        
    }

    @Test
    public void testTurnClockwise() {
        robot.turnClockwise();
        assertEquals(0, robot.getOrientation());
    }

    @Test
    public void testRecharge() {
        robot = new Robot(7, 70, 40, 666);
        robot.recharge(615);
        assertEquals(1281, robot.getEnergy(), epsilon);
    }

    @Test
    public void testMoveToLegalPosition() {
        robot.moveTo(8, 38);
        assertEquals(8, robot.getX());
        assertEquals(38, robot.getY());
    }
    
    @Test (expected = IllegalStateException.class)
    public void testMoveToIllegalPosition() {
        robot.moveTo(1, 1);
        assertEquals(1, robot.getX());
        assertEquals(1, robot.getY());
    }

    @Test
    public void testMoveNextTo() {
        IRobot robot2 = new Robot(20, 117, Robot.LEFT, -1300);
        robot.moveNextTo(robot2);
        long manhattanDistance = Math.abs(robot.getX() - robot2.getX())
                            + Math.abs(robot.getY() - robot2.getY());
        assertEquals(1, manhattanDistance);
    }
    
    @Test
    public void testMoveNextToBestEffort() {
        IRobot robot2 = new Robot(7, 150, Robot.UP, -1300);
        robot.moveNextTo(robot2);
        long manhattanDistance = Math.abs(robot.getX() - robot2.getX())
                            + Math.abs(robot.getY() - robot2.getY());
        assertEquals(2, manhattanDistance);
    }
    
    @Test
    public void testMoveNextToOnTop() {
        IRobot robot2 = new Robot(7, 70, Robot.LEFT, -1300);
        robot.moveNextTo(robot2);
        long manhattanDistance = Math.abs(robot.getX() - robot2.getX())
                            + Math.abs(robot.getY() - robot2.getY());
        assertEquals(1, manhattanDistance);

    }
    
    @Test
    public void testMoveNextToOnTopInsufficientEnergy() {
        IRobot robot2 = new Robot(7, 70, Robot.UP, 0);
        robot.setEnergy(0);
        robot.moveNextTo(robot2);
        long manhattanDistance = Math.abs(robot.getX() - robot2.getX())
                            + Math.abs(robot.getY() - robot2.getY());
        assertEquals(0, manhattanDistance);
    }

    @Test
    public void testGetEnergyRequiredToReach() {
        double energy = robot.getEnergyRequiredToReach(8, 38);
        assertEquals(16700, energy, epsilon);
    }

    @Test
    public void testGetReachableInQuadrant() {
        List<Long[]> reachable = robot.getReachableInQuadrant(8, 71);
        Long[] coord1 = {(long) 7, (long) 70};
        Long[] coord2 = {(long) 7, (long) 71};
        Long[] coord3 = {(long) 8, (long) 70};
        Long[] coord4 = {(long) 8, (long) 71};
        
        assertArrayEquals(coord1, reachable.get(0));
        assertArrayEquals(coord2, reachable.get(1));
        assertArrayEquals(coord3, reachable.get(2));
        assertArrayEquals(coord4, reachable.get(3));
    }
    
    @Test
    public void testGetReachableOnTop() {
        List<Long[]> reachable = robot.getReachableInQuadrant(7, 70);
        Long[] coord1 = {(long) 8, (long) 70};
        Long[] coord2 = {(long) 6, (long) 70};
        Long[] coord3 = {(long) 7, (long) 71};
        Long[] coord4 = {(long) 7, (long) 69};
        Long[] coord5 = {(long) 7, (long) 70};
        
        assertArrayEquals(coord1, reachable.get(0));
        assertArrayEquals(coord2, reachable.get(1));
        assertArrayEquals(coord3, reachable.get(2));
        assertArrayEquals(coord4, reachable.get(3));
        assertArrayEquals(coord5, reachable.get(4));
    }

    @Test
    public void testSetX() {
        robot.setX(12);
        assertEquals(12, robot.getX());
    }

    @Test
    public void testGetX() {
        assertEquals(7, robot.getX());
    }

    @Test
    public void testSetY() {
        robot.setY(50);
        assertEquals(50, robot.getY());
    }

    @Test
    public void testGetY() {
        assertEquals(70, robot.getY());
    }

    @Test
    public void testSetOrientation() {
        robot.setOrientation(Robot.DOWN);
        assertEquals(Robot.DOWN, robot.getOrientation());
    }

    @Test
    public void testGetOrientation() {
        assertEquals(Robot.LEFT, robot.getOrientation());
    }

    @Test
    public void testSetEnergy() {
        robot.setEnergy(666);
        assertEquals(666, robot.getEnergy(), epsilon);
    }

    @Test
    public void testGetEnergy() {
        assertEquals(robot.getMaxEnergy(), robot.getEnergy(), epsilon);
    }

    @Test
    public void testGetEnergyRatio() {
        assertEquals(1, robot.getEnergyRatio(), epsilon);
    }
}
