package roborally.test;

import static org.junit.Assert.*;

import org.junit.*;

import roborally.model.Weight;

public class WeightTest {

    @Before
    public void setUp() {
        weight = new Weight(100, Weight.unitOfMass.g);
    }

    @Test
    public void testWeight() {
        assertNotNull(weight);
    }

    @Test
    public void testCompareTo() {
        Weight smallWeight = new Weight(50, Weight.unitOfMass.g);
        Weight equalWeight = new Weight(100, Weight.unitOfMass.g);
        Weight bigWeight = new Weight(10, Weight.unitOfMass.kg);

        assertEquals(1, weight.compareTo(smallWeight));
        assertEquals(0, weight.compareTo(equalWeight));
        assertEquals(-1, weight.compareTo(bigWeight));
    }

    @Test
    public void testAddWeight() {
        Weight weight2 = new Weight(100, Weight.unitOfMass.g);
        Weight weight3 = new Weight(200, Weight.unitOfMass.g);

        assertEquals(0, weight.compareTo(weight2));

        weight.addWeight(weight2);

        assertEquals(0, weight.compareTo(weight3));
    }

    @Test
    public void testRemoveWeight() {
        Weight weight2 = new Weight(100, Weight.unitOfMass.g);
        Weight weight3 = new Weight(50, Weight.unitOfMass.g);

        assertEquals(0, weight.compareTo(weight2));

        weight.removeWeight(weight3);

        assertEquals(0, weight.compareTo(weight3));
    }

    @Test
    public void testTerminate() {
        weight.terminate();

        assertTrue(weight.isTerminated());
    }

    @Test
    public void testIsTerminated() {
        assertFalse(weight.isTerminated());

        weight.terminate();

        assertTrue(weight.isTerminated());
    }

    private Weight weight;
}
