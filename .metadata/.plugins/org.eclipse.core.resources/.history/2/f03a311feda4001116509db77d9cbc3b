package roborally.model.auxiliary;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * @author Ben Adriaenssens <<ben.adriaenssens@student.kuleuven.be>>, Toon Nolten <toon.nolten@student.kuleuven.be>
 */

// TODO: NOTE "NoDoc p. 2"

public class Energy {
    public Energy(double amount, unitOfPower unit) {
        this.amountOfEnergy = amount * unit.getConversionFactor(unitOfPower.Ws);
        this.isTerminated = false;
    }

    public enum unitOfPower {
        Ws(1), Wh(3600), kWh(3600000), J(1), kJ(1000), foe(Math.pow(10, 44));
        // foe = 10^44

        private final double WATTSECONDS;

        unitOfPower(double wattSeconds) {
            this.WATTSECONDS = wattSeconds;
        }

        public double getConversionFactor(unitOfPower to) {
            return this.WATTSECONDS / to.WATTSECONDS;
        }
    }

    public int compareTo(Energy energy) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;

        if (this.amountOfEnergy == energy.amountOfEnergy)
            return EQUAL;
        else if (this.amountOfEnergy < energy.amountOfEnergy)
            return BEFORE;
        else
            return AFTER;
    }

    public void addEnergy(Energy energy) {
        amountOfEnergy += energy.amountOfEnergy;
    }

    public void removeEnergy(Energy energy) {
        amountOfEnergy -= energy.amountOfEnergy;
    }

    public void recharge(Energy energy, Energy maxEnergy) {
        this.addEnergy(energy);
        if (this.compareTo(maxEnergy) == 1) {
            energy.amountOfEnergy = amountOfEnergy - maxEnergy.amountOfEnergy;
            amountOfEnergy = maxEnergy.amountOfEnergy;
        } else
            energy.amountOfEnergy = 0;
    }

    public double getAmountOfEnergy() {
        return amountOfEnergy;
    }

    public boolean isValidEnergy(Energy maxEnergy) {
        if (compareTo(new Energy(0, Energy.unitOfPower.Ws)) >= 0
                && compareTo(maxEnergy) <= 0)
            return true;
        return false;
    }

    public void terminate() {
        amountOfEnergy = 0;
        this.isTerminated = true;
    }

    @Raw
    @Basic
    public boolean isTerminated() {
        return isTerminated;
    }

    private boolean isTerminated;

    private double amountOfEnergy;
}
