package roborally.program;

import roborally.model.Robot;

abstract class CombinedCommand extends Command {

    protected CombinedCommand(String commandString, Robot robot) {
        super(commandString, robot);
    }
}
