package roborally.program;

import java.util.List;

import roborally.model.Robot;

class While extends CombinedCommand {

    While(String commandString, Robot robot) {
        super(commandString, robot);

        List<String> whileString = Program.allSubParenthesed(commandString);

        condition = Condition.newCondition(whileString.get(0), robot);
        command = newCommand(whileString.get(1), robot);
    }

    @Override
    void execute() {
        if (robot != null)
            if (command instanceof Seq && ((Seq) command).isBusy())
                command.execute();
            else if (condition.evaluate()) {
                command.execute();
                robot.getProgram().decrementProgramCounter();
            }
    }

    private Condition condition;
    private Command command;
}
