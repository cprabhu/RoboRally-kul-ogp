package roborally.model;

public class Energy {
    public Energy(double amount, unitOfPower unit) {
        this.amountOfEnergy = amount * unit.getConversionFactor(unitOfPower.Ws);
    }

    public enum unitOfPower {
        Ws(1), Wh(3600), kWh(3600000), foe(Math.pow(10, 44)); // foe = 10^44
                                                              // joule

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

    private double amountOfEnergy;
}
