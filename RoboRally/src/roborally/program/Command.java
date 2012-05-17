package roborally.program;

import java.util.Scanner;

import roborally.model.Robot;

abstract class Command {

    protected Command(String commandString, Robot robot) {
        this.commandString = commandString;
        this.robot = robot;
    }

    static Command newCommand(String commandString, Robot robot)
            throws IllegalArgumentException {
        Scanner commandScanner = new Scanner(commandString);
        String command = commandScanner.next("[a-z-]");
        if (command.equals("move"))
            return new Move(robot);
        else if (command.equals("turn"))
            return new Turn(commandScanner.next(), robot);
        else if (command.equals("shoot"))
            return new Shoot(robot);
        else if (command.equals("pickup-and-use"))
            return new PickupAndUse(robot);
        else if (command.equals("if"))
            return new If(commandString, robot);
        else if (command.equals("while"))
            return new While(commandString, robot);
        else if (command.equals("seq"))
            return new Seq(commandString, robot);
        throw new IllegalArgumentException();
    }

    abstract void execute();

    @Override
    public String toString() {
        return commandString;
    }

    private String commandString;
    protected Robot robot;
}
