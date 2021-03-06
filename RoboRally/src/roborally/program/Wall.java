package roborally.program;

import roborally.model.Robot;

/**
 * A Class for representing the condition wall, which tests if there is a wall
 * to the right of the robot.
 * 
 * @author Ben Adriaenssens (ben.adriaenssens@student.kuleuven.be) - WtkCws,
 *         Toon Nolten (toon.nolten@student.kuleuven.be) - CwsElt.
 */
class Wall extends BasicCondition {

    /**
     * Initializes the wall condition with a robot.
     * 
     * @param robot
     *      The robot for which the condition will be evaluated.
     */
    Wall(Robot robot) {
        this.robot = robot;
    }

    /**
     * Evaluate the condition wall.
     * 
     * @return true if there is a wall to the right of the robot.
     * @return false otherwise.
     */
    @Override
    boolean evaluate() {
        if (robot != null
                && robot.getOrientation().turnClockwise90()
                        .nextPosition(robot.getPosition()) != null
                && robot.getOrientation().turnClockwise90()
                        .nextPosition(robot.getPosition())
                        .getElementsOf(roborally.model.Wall.class).size() > 0)
            return true;
        return false;
    }

    /*
     * (non-Javadoc)
     * @see roborally.program.Condition#toString()
     */
    @Override
    public String toString() {
        return "(wall)";
    }

    private Robot robot;
}
