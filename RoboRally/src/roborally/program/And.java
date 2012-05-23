package roborally.program;

import roborally.model.Robot;

/**
 * This class represents the and operator.
 * 
 * @author Ben Adriaenssens (ben.adriaenssens@student.kuleuven.be) - WtkCws,
 *         Toon Nolten (toon.nolten@student.kuleuven.be) - CwsElt.
 */
class And extends CombinedCondition {

    /**
     * Initializes this and operator with a string and a robot.
     * 
     * @param conditionString
     *      The conditionString holds the list of conditions for the and
     *      operator.
     * @param robot
     *      The robot for which the conditions will be evaluated.
     */
    And(String conditionString, Robot robot) {
        for (String subCondition : Program.allSubParenthesed(conditionString
                .substring(5, conditionString.length()))) {
            conditions.add(newCondition(subCondition, robot));
        }
    }

    /**
     * Evaluate this And's list of conditions and return true if all the
     * conditions evaluate to true.
     * 
     * @return true if all this And's conditions evaluate to true.
     * @return false otherwise.
     */
    @Override
    boolean evaluate() {
        for (Condition condition : conditions)
            if (!condition.evaluate())
                return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * @see roborally.program.Condition#toString()
     */
    @Override
    public String toString() {
        String conditionString = "(and";
        for (Condition condition : conditions)
            conditionString += " " + condition.toString();
        conditionString += ")";

        return conditionString;
    }
}
