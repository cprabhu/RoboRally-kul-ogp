package roborally.program;

import roborally.model.*;

class PickupAndUse extends BasicCommand {

    PickupAndUse(Robot robot) {
        super("(pickup-and-use)", robot);
    }

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
}
