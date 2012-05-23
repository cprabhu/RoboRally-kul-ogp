package roborally.test;

import static org.junit.Assert.*;

import org.junit.*;

import roborally.model.*;
import roborally.model.auxiliary.Energy;
import roborally.model.auxiliary.Weight;
import roborally.model.auxiliary.Energy.unitOfPower;
import roborally.model.auxiliary.Weight.unitOfMass;

/**
 * @author Ben Adriaenssens <ben.adriaenssens@student.kuleuven.be>, Toon Nolten <toon.nolten@student.kuleuven.be>
 */
public class BatteryTest {

    @Before
    public void setUp() {
        battery = new Battery();
        battery2 = new Battery(POSITION, ENERGY);
        battery3 = new Battery(POSITION, ENERGY, WEIGHT);
    }

    @Test
    public void testBattery() {
        assertNotNull(battery);
        assertEquals(0,
                battery.getEnergy().compareTo(new Energy(1000, unitOfPower.Ws)));
        assertEquals(0,
                battery.getWeight().compareTo(new Weight(100, unitOfMass.g)));

        assertNotNull(battery2);
        assertEquals(0, battery2.getEnergy().compareTo(ENERGY));
        assertEquals(0,
                battery2.getWeight().compareTo(new Weight(100, unitOfMass.g)));

        assertNotNull(battery3);
        assertEquals(0, battery3.getEnergy().compareTo(ENERGY));
        assertEquals(0,
                battery3.getWeight().compareTo(new Weight(200, unitOfMass.g)));
    }

    @Test
    public void testFalseBattery() {
        Energy negEnergy = new Energy(-500, unitOfPower.Ws);
        Energy exceedMaxEnergy = new Energy(500, unitOfPower.foe);
        try {
            new Battery(POSITION, null);
        } catch (AssertionError err) {
            System.err.println("testFalseBattery 1: "
                    + "This is expected behaviour, "
                    + "it means the assert energy != null has failed");
        }
        try {
            new Battery(POSITION, negEnergy);
        } catch (AssertionError err) {
            System.err.println("testFalseBattery 2: "
                    + "This is expected behaviour, "
                    + "it means the assert energy != null has failed");
        }
        try {
            new Battery(POSITION, exceedMaxEnergy);
        } catch (AssertionError err) {
            System.err.println("testFalseBattery 3: "
                    + "This is expected behaviour, "
                    + "it means the assert energy != null has failed");
        }
    }

    @Test
    public void testBatteryPositionEnergy() {
        assertNotNull(battery2);
        assertEquals(0, battery2.getEnergy().compareTo(ENERGY));
        assertEquals(0,
                battery.getWeight().compareTo(new Weight(100, unitOfMass.g)));
    }

    @Test
    public void testBatteryPositionEnergyWeight() {
        assertNotNull(battery2);
        assertEquals(0, battery2.getEnergy().compareTo(ENERGY));
        assertEquals(0,
                battery.getWeight().compareTo(new Weight(100, unitOfMass.g)));
    }

    @Test
    public void testGetEnergy() {
        assertEquals(0,
                battery.getEnergy().compareTo(new Energy(1000, unitOfPower.Ws)));
        assertEquals(0, battery2.getEnergy().compareTo(ENERGY));
        assertEquals(0, battery3.getEnergy().compareTo(ENERGY));
    }

    @Test
    public void testGetMaxEnergy() {
        Energy maxEnergy = new Energy(5000, unitOfPower.Ws);
        assertEquals(0, battery.getMaxEnergy().compareTo(maxEnergy));
        assertEquals(0, battery2.getMaxEnergy().compareTo(maxEnergy));
        assertEquals(0, battery3.getMaxEnergy().compareTo(maxEnergy));
    }

    @Test
    public void testSetPosition() {
        Position position = Position.newPosition(3, 2, BOARD);

        battery.setPosition(position);

        assertTrue(battery.getPosition().equals(position));
        assertTrue(position.containsElement(battery));
    }

    @Test
    public void testGetPosition() {
        assertFalse(battery.getPosition() == POSITION);
        assertEquals(POSITION, battery2.getPosition());
    }

    @Test
    public void testUse() {
        Energy zeroEnergy = new Energy(0, unitOfPower.Ws);
        Energy emptyEnergy = new Energy(0, unitOfPower.Ws);
        Energy thousandEnergy = new Energy(1000, unitOfPower.Ws);
        Battery battery4 = new Battery(POSITION, new Energy(5000,
                unitOfPower.Ws));
        Robot testRobot = new Robot(new Energy(5000, unitOfPower.Ws),
                Orientation.UP);
        Robot emptyRobot = new Robot(emptyEnergy, Orientation.UP);
        Robot maxRobot = new Robot(new Energy(16000, unitOfPower.Ws),
                Orientation.UP);

        battery2.use(testRobot);

        assertEquals(0, zeroEnergy.compareTo(battery2.getEnergy()));

        battery2.use(emptyRobot);

        assertEquals(0, emptyEnergy.compareTo(zeroEnergy));
        assertEquals(0, battery2.getEnergy().compareTo(zeroEnergy));

        battery4.use(maxRobot);

        assertEquals(0, thousandEnergy.compareTo(battery4.getEnergy()));
    }

    @Test
    public void testTerminate() {
        assertFalse(battery.isTerminated());
        assertFalse(battery2.isTerminated());
        assertFalse(battery3.isTerminated());

        battery.terminate();
        battery2.terminate();
        battery3.terminate();

        assertTrue(battery.isTerminated());
        assertTrue(battery2.isTerminated());
        assertFalse(POSITION.containsElement(battery2));
        assertTrue(battery3.isTerminated());
        assertFalse(POSITION.containsElement(battery3));
    }

    @Test
    public void testIsTerminated() {
        battery.terminate();

        assertTrue(battery.isTerminated());
        assertFalse(battery2.isTerminated());
    }

    private Battery battery;
    private Battery battery2;
    private Battery battery3;
    private final Energy ENERGY = new Energy(100, Energy.unitOfPower.Ws);
    private final Weight WEIGHT = new Weight(200, Weight.unitOfMass.g);
    private final Board BOARD = new Board(4, 5);
    private final Position POSITION = Position.newPosition(2, 3, BOARD);
}
