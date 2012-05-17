package roborally.program;

import roborally.model.Robot;

class Not extends CombinedCondition {

    Not(String conditionString, Robot robot) {
        super(conditionString);

        condition = newCondition(
                conditionString.substring(4, conditionString.length() - 1),
                robot);
    }

    @Override
    boolean evaluate() {
        return !condition.evaluate();
    }

    private Condition condition;
}
