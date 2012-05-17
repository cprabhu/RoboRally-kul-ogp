package roborally.filters;

import roborally.model.Element;
import roborally.model.auxiliary.EnergyElement;

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
