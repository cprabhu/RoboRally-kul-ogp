package roborally.test;

import static org.junit.Assert.*;

import org.junit.Test;

import roborally.filters.EnergyAtLeastExtractor;
import roborally.model.*;
import roborally.model.auxiliary.Energy;
import roborally.model.auxiliary.Energy.unitOfPower;

public class EnergyAtLeastExtractorTest {

    @Test
    public void testEnergyAtLeastExtractor() {
        EnergyAtLeastExtractor energyAtLeastExtractor1 = null;
        assertNull(energyAtLeastExtractor1);

        energyAtLeastExtractor1 = new EnergyAtLeastExtractor(0);

        assertNotNull(energyAtLeastExtractor1);
    }

    @Test
    public void testIsSatisfied() {
        assertTrue(energyAtLeastExtractor.isSatisfied(new Battery(null,
                new Energy(3600, unitOfPower.Ws))));
        assertFalse(energyAtLeastExtractor.isSatisfied(new Battery(null,
                new Energy(1200, unitOfPower.Ws))));
        assertTrue(energyAtLeastExtractor.isSatisfied(new RepairKit(null, null,
                new Energy(6489, unitOfPower.Ws))));
        assertFalse(energyAtLeastExtractor.isSatisfied(new RepairKit(null,
                null, new Energy(3, unitOfPower.Ws))));
        assertTrue(energyAtLeastExtractor.isSatisfied(new Robot(new Energy(
                15423, unitOfPower.Ws), Orientation.LEFT)));
        assertFalse(energyAtLeastExtractor.isSatisfied(new Robot(new Energy(
                256, unitOfPower.Ws), Orientation.DOWN)));

        assertFalse(energyAtLeastExtractor.isSatisfied(new Wall()));
        assertFalse(energyAtLeastExtractor.isSatisfied(new SurpriseBox()));
    }

    private final EnergyAtLeastExtractor energyAtLeastExtractor = new EnergyAtLeastExtractor(
            2300);
}
