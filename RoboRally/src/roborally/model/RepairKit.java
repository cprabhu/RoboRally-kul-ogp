package roborally.model;

import roborally.model.Energy.unitOfPower;

public class RepairKit extends Item {

    public RepairKit() {
        super();
        energy = new Energy(1000, unitOfPower.Ws);
    }

    public RepairKit(Position position, Weight weight, Energy energy) {
        super(position, weight);
        this.energy = new Energy(1000, unitOfPower.Ws);
    }

    @Override
    public void hit() {
        if (energy != null)
            energy.addEnergy(new Energy(500, unitOfPower.Ws));
        energy = new Energy(500, unitOfPower.Ws);
    }

    @Override
    public void use(Robot robot) {
        Energy increaseEnergy = new Energy(energy.getAmountOfEnergy() / 2,
                unitOfPower.Ws);
        robot.increaseMaxEnergy(increaseEnergy);
        energy = new Energy(increaseEnergy.getAmountOfEnergy() * 2,
                unitOfPower.Ws);
    }

    private Energy energy;
}
