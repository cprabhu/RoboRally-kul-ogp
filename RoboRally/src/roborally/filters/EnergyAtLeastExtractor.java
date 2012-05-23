package roborally.filters;

import roborally.model.Element;
import roborally.model.auxiliary.EnergyElement;

/**
 * @author Ben Adriaenssens (ben.adriaenssens@student.kuleuven.be) - WtkCws,
 *         Toon Nolten (toon.nolten@student.kuleuven.be) - CwsElt.
 */
public class EnergyAtLeastExtractor implements BooleanExtractor {

    public EnergyAtLeastExtractor(double energyAmount) {
        this.energyAmount = energyAmount;
    }

    @Override
    public boolean isSatisfied(Element element) {
        try {
            if (((EnergyElement) element).getAmountOfEnergy() >= energyAmount)
                return true;
        } catch (ClassCastException ccexc) {

        }
        return false;
    }

    private final double energyAmount;
}
