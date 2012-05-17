package roborally.program;

import java.util.List;

import roborally.model.Robot;

class If extends CombinedCommand {

    If(String commandString, Robot robot) {
        super(commandString, robot);

        List<String> ifString = Program.allSubParenthesed(commandString);

        condition = Condition.newCondition(ifString.get(0), robot);
        conditionSatisfiedCommand = newCommand(ifString.get(1), robot);
        conditionNotSatisfiedCommand = newCommand(ifString.get(2), robot);
    }

    @Override
    void execute() {
        if (robot != null) {
            if (conditionSatisfiedCommand instanceof Seq
                    && ((Seq) conditionSatisfiedCommand).isBusy()
                    || conditionNotSatisfiedCommand instanceof Seq
                    && ((Seq) conditionNotSatisfiedCommand).isBusy()) {
                if (branch == 1)
                    conditionSatisfiedCommand.execute();
                else if (branch == 2)
                    conditionNotSatisfiedCommand.execute();
            } else if (condition.evaluate()) {
                branch = 1;
                conditionSatisfiedCommand.execute();
            } else {
                branch = 2;
                conditionNotSatisfiedCommand.execute();
            }
        }
    }

    private final Condition condition;
    private final Command conditionSatisfiedCommand;
    private final Command conditionNotSatisfiedCommand;
    private int branch = 0;
}
