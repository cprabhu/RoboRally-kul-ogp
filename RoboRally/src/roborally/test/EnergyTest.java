package roborally.test;

import roborally.model.auxiliary.Energy;
import roborally.model.auxiliary.Energy.unitOfPower;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Ben Adriaenssens <ben.adriaenssens@student.kuleuven.be>, Toon Nolten <toon.nolten@student.kuleuven.be>
 */
public class EnergyTest {

    @Test
    public void testEnergy() {
        new Energy(2500, unitOfPower.foe);
    }

    @Test
    public void testCompareTo() {
        Energy smallEnergy = new Energy(14, unitOfPower.Ws);
        Energy middleEnergy = new Energy(370, unitOfPower.Wh);
        Energy middleEnergy2 = new Energy(370, unitOfPower.Wh);
        Energy bigEnergy = new Energy(54, unitOfPower.foe);

        assertEquals(-1, smallEnergy.compareTo(middleEnergy));
        assertEquals(-1, smallEnergy.compareTo(bigEnergy));

        assertEquals(0, middleEnergy.compareTo(middleEnergy2));
        assertEquals(0, middleEnergy2.compareTo(middleEnergy));

        assertEquals(1, bigEnergy.compareTo(smallEnergy));
        assertEquals(1, bigEnergy.compareTo(middleEnergy));
    }

    @Test
    public void testAddEnergy() {
        Energy smallEnergy = new Energy(0, unitOfPower.Ws);
        Energy testEnergy = new Energy(100, unitOfPower.Wh);

        Energy addEnergy = new Energy(100, unitOfPower.Wh);
        smallEnergy.addEnergy(addEnergy);

        assertEquals(0, smallEnergy.compareTo(testEnergy));
    }

    @Test
    public void testRemoveEnergy() {
        Energy bigEnergy = new Energy(1000, unitOfPower.Wh);
        Energy testEnergy = new Energy(100, unitOfPower.Wh);

        Energy removeEnergy = new Energy(900, unitOfPower.Wh);
        bigEnergy.removeEnergy(removeEnergy);

        assertEquals(0, bigEnergy.compareTo(testEnergy));
    }

    @Test
    public void testRecharge() {
        Energy testEnergy = new Energy(100, unitOfPower.Wh);
        Energy smallEnergy = new Energy(10, unitOfPower.Ws);
        Energy bigEnergy = new Energy(100, unitOfPower.Wh);
        Energy maxEnergy = new Energy(150, unitOfPower.Wh);
        Energy zeroEnergy = new Energy(0, unitOfPower.Ws);

        assertEquals(-1, testEnergy.compareTo(maxEnergy));

        testEnergy.recharge(smallEnergy, maxEnergy);

        assertTrue(testEnergy.compareTo(maxEnergy) < 1);
        assertEquals(0, smallEnergy.compareTo(zeroEnergy));

        testEnergy = bigEnergy;
        testEnergy.recharge(bigEnergy, maxEnergy);

        assertFalse(testEnergy.compareTo(maxEnergy) > 1);
        assertFalse(bigEnergy.compareTo(zeroEnergy) < 1);
    }

    @Test
    public void testGetAmountOfEnergy() {
        assertEquals(2500,
                new Energy(2500, unitOfPower.Ws).getAmountOfEnergy(), 0.01);
    }

    @Test
    public void testTerminate() {
        Energy testEnergy = new Energy(100, unitOfPower.Wh);
        testEnergy.terminate();

        assertTrue(testEnergy.isTerminated());
    }

    @Test
    public void testIsTerminated() {
        Energy testEnergy = new Energy(100, unitOfPower.Wh);
        assertFalse(testEnergy.isTerminated());

        testEnergy.terminate();

        assertTrue(testEnergy.isTerminated());
    }

}
