package roborally.test;

import roborally.model.*;
import roborally.model.Energy.unitOfPower;
import roborally.model.Weight.unitOfMass;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

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

        assertNull(robot.getEnergyRequiredToReach(reachablePosition1));

        robot.recharge(new Energy(15000, unitOfPower.Ws));
        robot.setPosition(robotPosition);

        assertEquals(14100, robot.getEnergyRequiredToReach(reachablePosition1)
                .getAmountOfEnergy(), epsilon);

        assertEquals(9300, robot.getEnergyRequiredToReach(reachablePosition2)
                .getAmountOfEnergy(), epsilon);

        assertNull(robot.getEnergyRequiredToReach(insufficientEnergyPosition));
    }

    @Test
    public void testGetEnergyRequiredToReachWs() {
        fail("Not yet implemented");
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

        // robot.moveTo(unReachablePosition); // ik denk dat hij het in dit
        // geval
        // gewoon niet doet. Maar zou hij
        // hier een error moeten geven van
        // "je raakt er niet"

        assertTrue(moveablePosition.equals(robot.getPosition()));

        robot.moveTo(occupiedPosition);

        assertTrue(moveablePosition.equals(robot.getPosition()));
    }

    @Test
    public void testMoveNextTo() {
        fail("Not yet implemented");
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

        robotNotEnoughEnergy.shoot();

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
    public void testGetCarriedWeight() {
        Position position = Position.newPosition(10, 10, board);
        Weight batteryWeight = new Weight(100, unitOfMass.g);
        Battery battery = new Battery(position, energy, batteryWeight);
        Battery battery2 = new Battery(position, energy, batteryWeight);
        Battery battery3 = new Battery(position, energy, batteryWeight);

        robot.setPosition(position);

        assertEquals(0, robot.getCarriedWeight(unitOfMass.g));

        robot.pickup(battery);

        assertEquals(batteryWeight.getMassIn(unitOfMass.g),
                robot.getCarriedWeight(unitOfMass.g));

        robot.pickup(battery2);

        assertEquals(2 * batteryWeight.getMassIn(unitOfMass.g),
                robot.getCarriedWeight(unitOfMass.g));

        robot.pickup(battery3);

        assertEquals(3 * batteryWeight.getMassIn(unitOfMass.g),
                robot.getCarriedWeight(unitOfMass.g));
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
