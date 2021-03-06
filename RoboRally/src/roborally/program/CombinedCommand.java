package roborally.program;

import roborally.model.Robot;

/**
 * This class represents a combined command.
 * 
 * @author Ben Adriaenssens (ben.adriaenssens@student.kuleuven.be) - WtkCws,
 *         Toon Nolten (toon.nolten@student.kuleuven.be) - CwsElt.
 */
abstract class CombinedCommand extends Command {

    /**
     * Initialize this CombinedCommand with a robot
     * 
     * @param robot
     *      The robot that will execute this command.
     */
    protected CombinedCommand(Robot robot) {
        super(robot);
    }

    abstract boolean isBusy();
}
