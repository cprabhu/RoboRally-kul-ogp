package roborally.program;

import roborally.model.*;

class CanHitRobot extends BasicCondition {

    CanHitRobot(Robot robot) {
        super("(can-hit-robot)");
        this.robot = robot;
    }

    @Override
    boolean evaluate() {
        if (robot != null) {
            Position bulletPosition = robot.getOrientation().nextPosition(
                    robot.getPosition());
            while (robot.getPosition() != null
                    && robot.getPosition().BOARD
                            .isValidPosition(bulletPosition)
                    && !robot.getPosition().BOARD
                            .isOccupiedPosition(bulletPosition))
                bulletPosition = robot.getOrientation().nextPosition(
                        bulletPosition);
            if (bulletPosition != null
                    && bulletPosition.getElementsOf(Robot.class).size() > 0)
                return true;
        }
        return false;
    }

    private Robot robot;
}
