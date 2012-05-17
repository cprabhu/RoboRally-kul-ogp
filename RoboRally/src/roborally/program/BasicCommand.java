package roborally.program;

import roborally.model.Robot;

abstract class BasicCommand extends Command {

    protected BasicCommand(String commandString, Robot robot) {
        super(commandString, robot);
    }
}
