package roborally.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

public class Weight {
    
    public Weight(int mass, unitOfMass unit){
        this.mass = mass * unit.getConversionFactor(unitOfMass.g);
        this.isTerminated = false;
    }

    public enum unitOfMass {
        g(1), kg(1000), tonne(1000000);

        private final int GRAMS;

        unitOfMass(int grams) {
            this.GRAMS = grams;
        }

        public int getConversionFactor(unitOfMass to) {
            return this.GRAMS / to.GRAMS;
        }
    }
    
    public int compareTo(Weight weight) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;

        if (this.mass == weight.mass)
            return EQUAL;
        else if (this.mass < weight.mass)
            return BEFORE;
        else
            return AFTER;
    }

    public void addWeight(Weight weight) {
        mass += weight.mass;
    }

    public void removeWeight(Weight weight) {
        mass -= weight.mass;
    }
    
    public void terminate() {
        mass = 0;
        this.isTerminated = true;
    }

    @Raw
    @Basic
    public boolean isTerminated() {
        return isTerminated;
    }
    
    private boolean isTerminated;
    
    private int mass;
}
