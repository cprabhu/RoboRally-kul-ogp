package roborally.program;

import roborally.model.Robot;

class EnergyAtLeast extends BasicCondition {

    EnergyAtLeast(double energyAmount, Robot robot) {
        super("(energy-at-least " + energyAmount + ")");
        this.energyAmount = energyAmount;
        this.robot = robot;
    }

    @Override
    boolean evaluate() {
        if (robot != null && robot.getAmountOfEnergy() >= energyAmount)
            return true;
        return false;
    }

    private double energyAmount;
    private Robot robot;
}
