package roborally.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

public class Battery extends Element {

    public Battery() {
        super();
        this.maxEnergy = new Energy(5000, Energy.unitOfPower.Ws);
        this.energy = maxEnergy;
        this.weight = new Weight(100, Weight.unitOfMass.g);
    }

    public Battery(Position position, Energy energy) {
        super(position);
        maxEnergy = new Energy(5000, Energy.unitOfPower.Ws);
        assert (energy != null);
        assert (energy.compareTo(new Energy(0, Energy.unitOfPower.Ws)) > -1);
        assert (energy.compareTo(maxEnergy) < 1);
        this.energy = energy;
        this.weight = new Weight(100, Weight.unitOfMass.g);
    }

    public Battery(Position position, Energy energy, Weight weight) {
        this(position, energy);
        this.weight.removeWeight(new Weight(100, Weight.unitOfMass.g));
        this.weight.addWeight(weight);

    }

    public void charge(Energy energy, Energy maxEnergy) {
        energy.recharge(this.energy, maxEnergy);
    }

    public Energy getEnergy() {
        return energy;
    }

    public Weight getWeight() {
        return weight;
    }

    public final Energy getMaxEnergy() {
        return maxEnergy;
    }

    public void terminate() {
        energy.terminate();
        maxEnergy.terminate();
        weight.terminate();
        super.terminate();
    }

    @Raw
    @Basic
    public boolean isTerminated() {
        return isTerminated;
    }

    private Energy energy;
    private final Energy maxEnergy;
    private final Weight weight;
}
