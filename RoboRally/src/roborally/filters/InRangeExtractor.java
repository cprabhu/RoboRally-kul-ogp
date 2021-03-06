package roborally.filters;

import roborally.model.Element;
import roborally.model.auxiliary.Position;

/**
 * @author Ben Adriaenssens (ben.adriaenssens@student.kuleuven.be) - WtkCws,
 *         Toon Nolten (toon.nolten@student.kuleuven.be) - CwsElt.
 */
public class InRangeExtractor implements BooleanExtractor {

    public InRangeExtractor(long minX, long maxX, long minY, long maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    @Override
    public boolean isSatisfied(Element element) {
        Position position = element.getPosition();
        if (position.X >= minX && position.X <= maxX && position.Y >= minY
                && position.Y <= maxY)
            return true;
        return false;
    }

    private final long minX;
    private final long maxX;
    private final long minY;
    private final long maxY;
}
