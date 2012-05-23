package roborally.program;

import roborally.model.*;

/**
 * This class for representing the pickup-and-use command.
 * 
 * @author Ben Adriaenssens (ben.adriaenssens@student.kuleuven.be) - WtkCws,
 *         Toon Nolten (toon.nolten@student.kuleuven.be) - CwsElt.
 */
class PickupAndUse extends BasicCommand {

    /**
     * Initializes this PickupAndUse command with a robot.
     * 
     * @param robot
     *      The robot that will execute this PickupAndUse command.
     */
    PickupAndUse(Robot robot) {
        super(robot);
    }

    /**
     * Let the robot pickup and use a random item.
     */
    @Override
    void execute() {
        Item itemToPickupAndUse = null;
        if (robot != null && robot.getPosition() != null) {
            for (Element item : robot.getPosition().getElementsOf(Item.class)) {
                itemToPickupAndUse = (Item) item;
                break;
            }
            if (itemToPickupAndUse != null)
                try {
                    robot.pickup(itemToPickupAndUse);
                    robot.use(itemToPickupAndUse);
                } catch (IllegalArgumentException e) {
                } catch (IllegalStateException e) {
                }
        }
    }

    /*
     * (non-Javadoc)
     * @see roborally.program.Command#toString()
     */
    @Override
    public String toString() {
        return "(pickup-and-use)";
    }
}
