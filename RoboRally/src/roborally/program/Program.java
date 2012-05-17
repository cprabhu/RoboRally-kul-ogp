package roborally.program;

import java.util.*;

import roborally.model.Robot;

public class Program {

    public Program(String programString, Robot executingRobot) {
        this.programString = programString;
        commands = new ArrayList<Command>();

        for (String commandString : allSubParenthesed(programString))
            commands.add(Command.newCommand(commandString, executingRobot));
    }

    public void step() {
        commands.get(programCounter).execute();
        programCounter++;
    }

    @Override
    public String toString() {
        return programString;
    }

    void decrementProgramCounter() {
        programCounter--;
    }

    static List<String> allSubParenthesed(String string) {
        List<String> allSubParenthesed = new ArrayList<String>();
        string = string.substring(string.indexOf(" ") + 1, string.length() - 1);

        int parenthesesCounter = 0;
        int beginIndex = 0;
        for (int endIndex = 0; endIndex < string.length(); endIndex++) {
            if ("(".equals(string.charAt(endIndex)))
                parenthesesCounter++;
            else if (")".equals(string.charAt(endIndex)))
                parenthesesCounter++;
            if (parenthesesCounter == 0) {
                allSubParenthesed.add(string
                        .substring(beginIndex, endIndex + 1));
                beginIndex = endIndex + 2;
                endIndex += 2;
            }
        }

        return allSubParenthesed;
    }

    private int programCounter;
    private String programString;
    private final List<Command> commands;
}
