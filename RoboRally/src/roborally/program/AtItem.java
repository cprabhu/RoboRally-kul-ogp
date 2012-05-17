package roborally.program;

import roborally.model.*;

class AtItem extends BasicCondition {

    AtItem(Robot robot) {
        super("(at-item)");
        this.robot = robot;
    }

    @Override
    boolean evaluate() {
        if (robot != null && robot.getPosition() != null
                && robot.getPosition().getElementsOf(Item.class).size() > 0)
            return true;
        return false;
    }

    private Robot robot;
}
