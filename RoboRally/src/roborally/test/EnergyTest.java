package roborally.test;

import roborally.model.*;
import roborally.model.Energy.unitOfPower;

import static org.junit.Assert.*;

import org.junit.Test;

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

}
