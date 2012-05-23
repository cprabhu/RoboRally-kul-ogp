package roborally.test;

import static org.junit.Assert.*;

import org.junit.Test;

import roborally.filters.InRangeExtractor;
import roborally.model.*;
import roborally.model.auxiliary.Energy;
import roborally.model.auxiliary.Energy.unitOfPower;

public class InRangeExtractorTest {

    @Test
    public void testInRangeExtractor() {
        InRangeExtractor inRangeExtractor1 = null;
        assertNull(inRangeExtractor1);

        inRangeExtractor1 = new InRangeExtractor(2, 3, 5, 9);

        assertNotNull(inRangeExtractor1);
    }

    @Test
    public void testIsSatisfied() {
        assertTrue(inRangeExtractor.isSatisfied(new Battery(Position
                .newPosition(3, 4, board), new Energy(3600, unitOfPower.Ws))));
        assertFalse(inRangeExtractor.isSatisfied(new Battery(Position
                .newPosition(10, 12, board), new Energy(1200, unitOfPower.Ws))));
        assertTrue(inRangeExtractor.isSatisfied(new RepairKit(Position
                .newPosition(4, 5, board), null, new Energy(6489,
                unitOfPower.Ws))));
        assertFalse(inRangeExtractor
                .isSatisfied(new RepairKit(Position.newPosition(0, 3, board),
                        null, new Energy(3, unitOfPower.Ws))));
        assertTrue(inRangeExtractor.isSatisfied(new Wall(Position.newPosition(
                4, 4, board))));
        assertFalse(inRangeExtractor.isSatisfied(new SurpriseBox(Position
                .newPosition(6, 12, board))));
    }

    private final InRangeExtractor inRangeExtractor = new InRangeExtractor(3,
            6, 2, 5);
    private final Board board = new Board(36, 28);
}
