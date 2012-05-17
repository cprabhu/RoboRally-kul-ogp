package roborally.program;

import roborally.model.Robot;

class Wall extends BasicCondition {

    Wall(Robot robot) {
        super("(wall)");
        this.robot = robot;
    }

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

    private Robot robot;
}
