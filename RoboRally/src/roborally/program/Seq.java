package roborally.program;

import java.util.*;

import roborally.model.Robot;

class Seq extends CombinedCommand {

    Seq(String commandString, Robot robot) {
        super(commandString, robot);

        for (String command : Program.allSubParenthesed(commandString))
            sequence.add(newCommand(command, robot));
    }

    @Override
    void execute() {
        if (robot != null) {
            sequence.get(seqCounter).execute();
            seqCounter++;
            if (seqCounter == sequence.size()) {
                seqCounter = 0;
                isBusy = false;
            } else {
                robot.getProgram().decrementProgramCounter();
                isBusy = true;
            }
        }
    }

    boolean isBusy() {
        return isBusy;
    }

    private final List<Command> sequence = new ArrayList<Command>();
    private int seqCounter = 0;
    private boolean isBusy = false;
}
