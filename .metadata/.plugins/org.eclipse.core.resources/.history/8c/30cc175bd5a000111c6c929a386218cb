package roborally.program;

import roborally.model.Robot;

class Move extends BasicCommand {

    Move(Robot robot) {
        super("(move)", robot);
    }

    @Override
    void execute() {
        if (robot != null
                && robot.getAmountOfEnergy() >= robot.getEnergyToMove())
            try {
                robot.move();
            } catch (IllegalStateException e) {
                System.err.println("Move.execute():"
                        + " robot.move() IllegalStateException.");
            }
    }
}
