package roborally.program;

import roborally.model.Robot;

/**
 * This class for representing the energy-at-least condition.
 * 
 * @author Ben Adriaenssens (ben.adriaenssens@student.kuleuven.be) - WtkCws,
 *         Toon Nolten (toon.nolten@student.kuleuven.be) - CwsElt.
 */
class EnergyAtLeast extends BasicCondition {

    /**
     * Initializes this EnergyAtLeast condition.
     * 
     * @param energyAmount
     *      The energy to compare the robots energy to.
     * @param robot
     *            The robot for which the condition will be evaluated.
     */
    EnergyAtLeast(double energyAmount, Robot robot) {
        this.energyAmount = energyAmount;
        this.robot = robot;
    }

    /**
     * Evaluate this EnergyAtLeast condition.
     * 
     * @return true if the robot has at least energyAmount energy in Ws.
     * @return false otherwise.
     */
    @Override
    boolean evaluate() {
        if (robot != null && robot.getAmountOfEnergy() >= energyAmount)
            return true;
        return false;
    }

    /*
     * (non-Javadoc)
     * @see roborally.program.Condition#toString()
     */
    @Override
    public String toString() {
        return "(energy-at-least " + String.format("%.0f", energyAmount) + ")";
    }

    private double energyAmount;
    private Robot robot;
}
