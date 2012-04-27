package roborally.test;

import java.util.HashSet;
import java.util.Set;

import roborally.model.*;
import roborally.model.Energy.unitOfPower;
import roborally.model.Weight.unitOfMass;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Ben Adriaenssens <ben.adriaenssens@student.kuleuven.be>, Toon Nolten <toon.nolten@student.kuleuven.be>
 */
public class RobotTest {

    @Before
    public void setUp() throws Exception {
        energy = new Energy(3000, unitOfPower.Ws);
        robot = new Robot(
                new Energy(energy.getAmountOfEnergy(), unitOfPower.Ws),
                Orientation.UP);
    }

    @Test
    public void testGetPosition() {
        Position position = Position.newPosition(10, 5, board);

        robot.setPosition(position);

        assertTrue(position.equals(robot.getPosition()));
    }

    @Test
    public void testRobot() {
        assertNotNull(robot);
        assertEquals(3000, robot.getAmountOfEnergy(), epsilon);
        assertEquals(Orientation.UP, robot.getOrientation());
    }

    @Test
    public void testRecharge() {
        Energy chargeEnergy = new Energy(5000, unitOfPower.Ws);
        Energy chargeEnergy2 = new Energy(15000, unitOfPower.Ws);

        robot.recharge(chargeEnergy);

        assertEquals(8000, robot.getAmountOfEnergy(), epsilon);

        robot.recharge(chargeEnergy2);

        assertEquals(maxEnergy.getAmountOfEnergy(), robot.getAmountOfEnergy(),
                epsilon);
        assertEquals(3000, chargeEnergy2.getAmountOfEnergy(), epsilon);
    }

    @Test
    public void testGetEnergyRequiredToReach() {
        Position reachablePosition1 = Position.newPosition(23, 17, board);
        Position reachablePosition2 = Position.newPosition(6, 42, board);
        board.putElement(Position.newPosition(5, 40, board), new Wall());
        board.putElement(Position.newPosition(6, 41, board), new Wall());
        board.putElement(Position.newPosition(7, 42, board), new Wall());
        board.putElement(Position.newPosition(8, 43, board), new Wall());
        Position robotPosition = Position.newPosition(14, 36, board);
        Position insufficientEnergyPosition = Position.newPosition(117, 256,
                board);
        Position surroundedByWallPosition = Position.newPosition(18, 29, board);
        for (Position neighbour : surroundedByWallPosition.getNeighbours())
            board.putElement(neighbour, new Wall());

        assertNull(robot.getEnergyRequiredToReach(reachablePosition1));

        robot.recharge(new Energy(15000, unitOfPower.Ws));
        robot.setPosition(robotPosition);

        assertEquals(14100, robot.getEnergyRequiredToReach(reachablePosition1)
                .getAmountOfEnergy(), epsilon);

        assertEquals(9300, robot.getEnergyRequiredToReach(reachablePosition2)
                .getAmountOfEnergy(), epsilon);

        assertNull(robot.getEnergyRequiredToReach(insufficientEnergyPosition));

        assertNull(robot.getEnergyRequiredToReach(surroundedByWallPosition));
    }

    @Test
    public void testGetEnergyRequiredToReachWs() {
        Position reachablePosition1 = Position.newPosition(23, 17, board);
        Position reachablePosition2 = Position.newPosition(6, 42, board);
        board.putElement(Position.newPosition(5, 40, board), new Wall());
        board.putElement(Position.newPosition(6, 41, board), new Wall());
        board.putElement(Position.newPosition(7, 42, board), new Wall());
        board.putElement(Position.newPosition(8, 43, board), new Wall());
        Position robotPosition = Position.newPosition(14, 36, board);
        // Position insufficientEnergyPosition = Position.newPosition(117, 256,
        // board);
        Position surroundedByWallPosition = Position.newPosition(18, 29, board);
        for (Position neighbour : surroundedByWallPosition.getNeighbours())
            board.putElement(neighbour, new Wall());

        assertEquals(-1, robot.getEnergyRequiredToReachWs(reachablePosition1),
                epsilon);

        robot.recharge(new Energy(15000, unitOfPower.Ws));
        robot.setPosition(robotPosition);

        assertEquals(14100,
                robot.getEnergyRequiredToReachWs(reachablePosition1), epsilon);

        assertEquals(9300,
                robot.getEnergyRequiredToReachWs(reachablePosition2), epsilon);

        // TODO: Werkt enkel als we floodfill implementeren.
        // assertEquals(-2,
        // robot.getEnergyRequiredToReachWs(insufficientEnergyPosition),
        // epsilon);

        assertEquals(-1,
                robot.getEnergyRequiredToReachWs(surroundedByWallPosition),
                epsilon);
    }

    @Test
    public void testGetAmountOfEnergy() {
        assertEquals(energy.getAmountOfEnergy(), robot.getAmountOfEnergy(),
                epsilon);
    }

    @Test
    public void testGetFractionOfEnergy() {
        assertEquals(
                energy.getAmountOfEnergy() / maxEnergy.getAmountOfEnergy(),
                robot.getFractionOfEnergy(), epsilon);
    }

    @Test
    public void testGetEnergyToMove() {
        Position position = Position.newPosition(10, 10, board);
        Weight weight = new Weight(2, unitOfMass.kg);
        Battery battery = new Battery(position, energy, weight);

        assertEquals(500, robot.getEnergyToMove(), epsilon);

        robot.setPosition(position);
        robot.pickup(battery);

        assertEquals(600, robot.getEnergyToMove(), epsilon);
    }

    @Test
    public void testTurnClockwise90() {
        Energy notEnoughEnergy = new Energy(50, unitOfPower.Ws);
        Robot robotNotEnoughEnergy = new Robot(notEnoughEnergy, Orientation.UP);

        robot.turnClockwise90();

        assertEquals(Orientation.RIGHT, robot.getOrientation());
        assertEquals(energy.getAmountOfEnergy() - robot.getEnergyToTurn(),
                robot.getAmountOfEnergy(), epsilon);

        robotNotEnoughEnergy.turnClockwise90();

        assertEquals(Orientation.UP, robotNotEnoughEnergy.getOrientation());
        assertEquals(notEnoughEnergy.getAmountOfEnergy(),
                robotNotEnoughEnergy.getAmountOfEnergy(), epsilon);
    }

    @Test
    public void testTurnCounterClockwise90() {
        Energy notEnoughEnergy = new Energy(50, unitOfPower.Ws);
        Robot robotNotEnoughEnergy = new Robot(notEnoughEnergy, Orientation.UP);

        robot.turnCounterClockwise90();

        assertEquals(Orientation.LEFT, robot.getOrientation());
        assertEquals(energy.getAmountOfEnergy() - robot.getEnergyToTurn(),
                robot.getAmountOfEnergy(), epsilon);

        robotNotEnoughEnergy.turnCounterClockwise90();

        assertEquals(Orientation.UP, robotNotEnoughEnergy.getOrientation());
        assertEquals(notEnoughEnergy.getAmountOfEnergy(),
                robotNotEnoughEnergy.getAmountOfEnergy(), epsilon);
    }

    @Test
    public void testGetOrientation() {
        assertEquals(Orientation.UP, robot.getOrientation());

        robot.turnClockwise90();

        assertEquals(Orientation.RIGHT, robot.getOrientation());
    }

    @Test
    public void testGetOrientationInt() {
        assertEquals(0, robot.getOrientationInt());

        robot.turnClockwise90();

        assertEquals(1, robot.getOrientationInt());
    }

    @Test
    public void testMove() {
        Position position = Position.newPosition(50, 50, board);
        Position moveToPosition = Position.newPosition(50, 49, board);

        robot.setPosition(position);

        assertTrue(position.equals(robot.getPosition()));
        assertEquals(energy.getAmountOfEnergy(), robot.getAmountOfEnergy(),
                epsilon);

        robot.move();

        assertTrue(moveToPosition.equals(robot.getPosition()));
        assertEquals(energy.getAmountOfEnergy() - robot.getEnergyToMove(),
                robot.getAmountOfEnergy(), epsilon);
    }

    @Test
    public void testMoveTo() {
        Position originalPosition = Position.newPosition(50, 50, board);
        Position moveablePosition = Position.newPosition(60, 55, board);
        Position unReachablePosition = Position.newPosition(200, 300, board);
        Position occupiedPosition = Position.newPosition(61, 56, board);
        board.putElement(occupiedPosition, new Wall());

        robot.recharge(new Energy(15000, unitOfPower.Ws));
        robot.setPosition(originalPosition);
        robot.moveTo(moveablePosition);

        assertTrue(moveablePosition.equals(robot.getPosition()));
        assertEquals(
                18000 - (2 * robot.getEnergyToTurn() + 15 * robot
                        .getEnergyToMove()),
                robot.getAmountOfEnergy(), epsilon);

        // TODO: Exception als moveTo er niet geraakt?
        try {
            robot.moveTo(unReachablePosition);
        } catch (AssertionError ae) {
            System.err
                    .println("testMoveTo: This assertionerror is to be expected.");
        } /*
           * ik denk dat hij het in dit geval gewoon niet doet. Maar zou hij
           * hier een error moeten geven van "je raakt er niet"
           */

        assertTrue(moveablePosition.equals(robot.getPosition()));

        try {
            robot.moveTo(occupiedPosition);
        } catch (AssertionError ae) {
            System.err
                    .println("testMoveTo: This assertionerror is to be expected.");
        }

        assertTrue(moveablePosition.equals(robot.getPosition()));
    }

    @Test
    public void testMoveNextToNoObstacles() {
        Robot robot2 = new Robot(energy, Orientation.RIGHT);

        board.putElement(Position.newPosition(7, 15, board), robot);
        board.putElement(Position.newPosition(9, 12, board), robot2);

        robot.moveNextTo(robot2);

        assertTrue(robot.getPosition().getNeighbours()
                .contains(robot2.getPosition()));
        assertTrue(robot.getPosition().equals(
                Position.newPosition(8, 12, board))
                || robot.getPosition().equals(
                        Position.newPosition(9, 13, board)));
    }

    // Deze test duurt lang. (2 seconden)
    // @Test
    // public void testMoveNextToNoObstaclesLongDistance() {
    // Robot robot2 = new Robot(energy, Orientation.LEFT);
    // robot.recharge(new Energy(17000, unitOfPower.Ws));
    // robot2.recharge(new Energy(17000, unitOfPower.Ws));
    //
    // board.putElement(Position.newPosition(10, 10, board), robot);
    // board.putElement(Position.newPosition(40, 40, board), robot2);
    //
    // robot.moveNextTo(robot2);
    //
    // assertTrue(robot.getPosition().getNeighbours()
    // .contains(robot2.getPosition()));
    //
    // }

    @Test
    public void testMoveNextToBestEffort() {
        Robot robot2 = new Robot(energy, Orientation.RIGHT);

        board.putElement(Position.newPosition(7, 15, board), robot);
        board.putElement(Position.newPosition(12, 23, board), robot2);

        robot.moveNextTo(robot2);

        assertEquals(3,
                robot.getPosition().manhattanDistance(robot2.getPosition()),
                epsilon);
        assertEquals(400, robot.getAmountOfEnergy(), epsilon);
        assertEquals(400, robot2.getAmountOfEnergy(), epsilon);

    }

    @Test
    public void testMoveNextToRobotNotOnBoard() {
        Robot robot2 = new Robot(energy, Orientation.RIGHT);
        Board board2 = new Board(542, 897);

        board.putElement(Position.newPosition(7, 15, board), robot);

        robot.moveNextTo(robot2);

        assertTrue(robot.getPosition().equals(
                Position.newPosition(7, 15, board)));
        assertEquals(3000, robot.getAmountOfEnergy(), epsilon);

        robot2.moveNextTo(robot);

        assertNull(robot2.getPosition());
        assertEquals(3000, robot2.getAmountOfEnergy(), epsilon);

        board2.putElement(Position.newPosition(12, 23, board2), robot2);

        robot.moveNextTo(robot2);

        assertTrue(robot.getPosition().equals(
                Position.newPosition(7, 15, board)));
        assertEquals(3000, robot.getAmountOfEnergy(), epsilon);
    }

    @Test
    public void testMoveNextToBestEffortOverlap() {
        Robot robot2 = new Robot(energy, Orientation.RIGHT);

        board.putElement(Position.newPosition(7, 15, board), robot);
        board.putElement(Position.newPosition(9, 20, board), robot2);

        robot.moveNextTo(robot2);

        assertTrue(robot.getPosition().getNeighbours()
                .contains(robot2.getPosition()));
    }

    @Test
    public void testMoveNextToDifferentCarriedWeight() {
        Robot robot2 = new Robot(new Energy(1000, unitOfPower.Ws),
                Orientation.DOWN);
        Battery battery = new Battery(Position.newPosition(7, 15, board),
                energy, new Weight(10, unitOfMass.kg));

        robot.recharge(new Energy(3000, unitOfPower.Ws));

        board.putElement(Position.newPosition(7, 15, board), robot);
        robot.pickup(battery);
        board.putElement(Position.newPosition(10, 13, board), robot2);

        robot.moveNextTo(robot2);

        assertTrue(robot.getPosition().equals(
                Position.newPosition(9, 15, board)));
        assertTrue(robot2.getPosition().equals(
                Position.newPosition(10, 15, board)));
        assertEquals(3900, robot.getAmountOfEnergy(), epsilon);
        assertEquals(0, robot2.getAmountOfEnergy(), epsilon);
    }

    @Test
    public void testMoveNextToSurroundedByWalls() {
        Robot robot2 = new Robot(energy, Orientation.UP);

        board.putElement(Position.newPosition(7, 15, board), robot);
        board.putElement(Position.newPosition(12, 23, board), robot2);

        robot.recharge(new Energy(3000, unitOfPower.Ws));

        Set<Position> wallsRoundRobot2Positions = new HashSet<Position>();
        for (long x = 10; x < 15; x++) {
            wallsRoundRobot2Positions.add(Position.newPosition(x, 21, board));
            wallsRoundRobot2Positions.add(Position.newPosition(x, 26, board));
        }
        for (long y = 22; y < 26; y++) {
            wallsRoundRobot2Positions.add(Position.newPosition(10, y, board));
            wallsRoundRobot2Positions.add(Position.newPosition(14, y, board));
        }
        for (Position wallPosition : wallsRoundRobot2Positions)
            board.putElement(wallPosition, new Wall());

        robot.moveNextTo(robot2);

        assertTrue(robot.getPosition().equals(
                Position.newPosition(12, 20, board)));
        assertTrue(robot2.getPosition().equals(
                Position.newPosition(12, 22, board)));
        assertEquals(800, robot.getAmountOfEnergy(), epsilon);
        assertEquals(2500, robot2.getAmountOfEnergy(), epsilon);
    }

    @Test
    public void testMoveNextToSurroundedByRobots() {
        Robot robot2 = new Robot(energy, Orientation.UP);

        board.putElement(Position.newPosition(7, 15, board), robot);
        board.putElement(Position.newPosition(12, 23, board), robot2);

        robot.recharge(new Energy(3000, unitOfPower.Ws));

        Set<Position> robotsRoundRobot2Positions = new HashSet<Position>();
        for (long x = 10; x < 15; x++) {
            robotsRoundRobot2Positions.add(Position.newPosition(x, 21, board));
            robotsRoundRobot2Positions.add(Position.newPosition(x, 26, board));
        }
        for (long y = 22; y < 26; y++) {
            robotsRoundRobot2Positions.add(Position.newPosition(10, y, board));
            robotsRoundRobot2Positions.add(Position.newPosition(14, y, board));
        }
        for (Position robotPosition : robotsRoundRobot2Positions)
            board.putElement(robotPosition, new Robot(energy, Orientation.UP));

        robot.moveNextTo(robot2);

        assertTrue(robot.getPosition().equals(
                Position.newPosition(12, 20, board)));
        assertTrue(robot2.getPosition().equals(
                Position.newPosition(12, 22, board)));
        assertEquals(800, robot.getAmountOfEnergy(), epsilon);
        assertEquals(2500, robot2.getAmountOfEnergy(), epsilon);
    }

    @Test
    public void testMoveNextToObstacles() {
        Robot robot2 = new Robot(new Energy(1800, unitOfPower.Ws),
                Orientation.UP);

        board.putElement(Position.newPosition(5, 5, board), robot);
        robot.recharge(new Energy(3600, unitOfPower.Ws));
        board.putElement(Position.newPosition(15, 5, board), robot2);

        Set<Position> maze = new HashSet<Position>();
        for (long x = 3; x < 18; x++) {
            maze.add(Position.newPosition(x, 3, board));
            maze.add(Position.newPosition(x, 7, board));
        }
        for (long y = 4; y < 7; y++) {
            maze.add(Position.newPosition(3, y, board));
            maze.add(Position.newPosition(17, y, board));
        }
        maze.add(Position.newPosition(7, 4, board));
        maze.add(Position.newPosition(7, 5, board));
        maze.add(Position.newPosition(10, 5, board));
        maze.add(Position.newPosition(10, 6, board));
        maze.add(Position.newPosition(13, 4, board));
        maze.add(Position.newPosition(13, 5, board));

        for (Position position : maze) {
            board.putElement(position, new Wall());
        }

        robot.moveNextTo(robot2);

        assertTrue(robot.getPosition().equals(
                Position.newPosition(12, 6, board)));
        assertTrue(robot2.getPosition().equals(
                Position.newPosition(13, 6, board)));
        assertEquals(0, robot.getAmountOfEnergy(), epsilon);
        assertEquals(0, robot2.getAmountOfEnergy(), epsilon);
    }

    @Test
    public void testShoot() {
        Energy enoughEnergy = new Energy(5000, unitOfPower.Ws);
        Energy notEnoughEnergy = new Energy(500, unitOfPower.Ws);
        Robot robotEnoughEnergy = new Robot(enoughEnergy, Orientation.RIGHT);
        Robot robotNotEnoughEnergy = new Robot(notEnoughEnergy,
                Orientation.LEFT);
        Position positionRobotEnoughEnergy = Position.newPosition(0, 0, board);
        Position positionRobotNotEnoughEnergy = Position.newPosition(20, 0,
                board);

        robotEnoughEnergy.setPosition(positionRobotEnoughEnergy);
        robotNotEnoughEnergy.setPosition(positionRobotNotEnoughEnergy);

        try {
            robotNotEnoughEnergy.shoot();
        } catch (AssertionError ae) {
            System.err
                    .println("testShoot: This assertionerror is to be expected.");
        }

        assertTrue(positionRobotEnoughEnergy.equals(robotEnoughEnergy
                .getPosition()));
        assertTrue(positionRobotNotEnoughEnergy.equals(robotNotEnoughEnergy
                .getPosition()));
        assertEquals(notEnoughEnergy.getAmountOfEnergy(),
                robotNotEnoughEnergy.getAmountOfEnergy(), epsilon);
        assertEquals(enoughEnergy.getAmountOfEnergy(),
                robotEnoughEnergy.getAmountOfEnergy(), epsilon);

        robotEnoughEnergy.shoot();

        assertTrue(positionRobotEnoughEnergy.equals(robotEnoughEnergy
                .getPosition()));
        assertNull(robotNotEnoughEnergy.getPosition());
        assertTrue(robotNotEnoughEnergy.isTerminated());
        assertEquals(notEnoughEnergy.getAmountOfEnergy(),
                robotNotEnoughEnergy.getAmountOfEnergy(), epsilon);
        assertEquals(4000, robotEnoughEnergy.getAmountOfEnergy(), epsilon);
    }

    @Test
    public void testPickup() {
        Position position = Position.newPosition(10, 10, board);
        Battery battery = new Battery(position);
        Battery battery2 = new Battery(position);

        assertEquals(0, robot.getPossesions().size());
        assertEquals(2, position.getElements().size());

        robot.setPosition(position);
        robot.pickup(battery);

        assertEquals(1, robot.getPossesions().size());
        assertEquals(2, position.getElements().size());

        robot.pickup(battery2);

        assertEquals(2, robot.getPossesions().size());
        assertEquals(1, position.getElements().size());
    }

    @Test
    public void testUse() {
        Position position = Position.newPosition(10, 10, board);
        Energy chargeEnergy = new Energy(3000, unitOfPower.Ws);
        Battery battery = new Battery(position, chargeEnergy);

        robot.setPosition(position);
        robot.pickup(battery);
        robot.recharge(new Energy(16000, unitOfPower.Ws));

        assertEquals(19000, robot.getAmountOfEnergy(), epsilon);

        robot.use(battery);

        assertEquals(maxEnergy.getAmountOfEnergy(), robot.getAmountOfEnergy(),
                epsilon);
        assertEquals(2000, battery.getEnergy().getAmountOfEnergy(), epsilon);
    }

    @Test
    public void testDrop() {
        Position position = Position.newPosition(10, 10, board);
        Battery battery = new Battery(position);
        Battery battery2 = new Battery(position);

        robot.setPosition(position);
        robot.pickup(battery);
        robot.pickup(battery2);

        assertEquals(2, robot.getPossesions().size());

        robot.drop(battery);

        assertEquals(1, robot.getPossesions().size());

        robot.drop(battery2);

        assertEquals(0, robot.getPossesions().size());
    }

    @Test
    public void testIthHeaviestElement() {
        Position position = Position.newPosition(10, 10, board);
        Weight lightWeight = new Weight(1, unitOfMass.kg);
        Weight heavyWeight = new Weight(100, unitOfMass.kg);

        Battery battery = new Battery(position, energy, lightWeight);
        Battery battery2 = new Battery(position, energy, heavyWeight);

        robot.setPosition(position);
        robot.pickup(battery);
        robot.pickup(battery2);

        assertEquals(0,
                heavyWeight.compareTo(robot.ithHeaviestElement(1).getWeight()));
        assertEquals(0,
                lightWeight.compareTo(robot.ithHeaviestElement(2).getWeight()));
    }

    @Test
    public void testGetPossesions() {
        Position position = Position.newPosition(10, 10, board);
        Battery battery = new Battery(position);
        Battery battery2 = new Battery(position);
        Battery battery3 = new Battery(position);

        robot.setPosition(position);
        robot.pickup(battery);
        robot.pickup(battery2);

        assertEquals(2, robot.getPossesions().size());
        assertTrue(robot.getPossesions().contains(battery));
        assertTrue(robot.getPossesions().contains(battery2));
        assertFalse(robot.getPossesions().contains(battery3));
    }

    @Test
    public void testSetPosition() {
        Position position = Position.newPosition(10, 10, board);

        robot.setPosition(position);

        assertEquals(position, robot.getPosition());
        assertTrue(position.containsElement(robot));
    }

    @Test
    public void testRemovePosition() {
        Position position = Position.newPosition(10, 10, board);
        robot.setPosition(position);

        assertNotNull(robot.getPosition());

        robot.removePosition();

        assertNull(robot.getPosition());
        assertNull(position.getElements());
    }

    @Test
    public void testTerminate() {
        Position position = Position.newPosition(10, 10, board);
        Battery battery = new Battery(position);
        Battery battery2 = new Battery(position);
        robot.setPosition(position);
        robot.pickup(battery);
        robot.pickup(battery2);

        assertFalse(robot.isTerminated());

        robot.terminate();

        assertTrue(robot.isTerminated());
        assertFalse(position.containsElement(robot));
        assertFalse(board.isOccupiedPosition(position));
        assertTrue(battery.isTerminated());
        assertTrue(battery2.isTerminated());
    }

    @Test
    public void testIsTerminated() {
        robot.terminate();

        assertTrue(robot.isTerminated());
    }

    private Robot robot;
    private Energy energy;
    private final Energy maxEnergy = new Energy(20000, unitOfPower.Ws);
    private final Board board = new Board(535, 364);
    private double epsilon = 0.01;
}
