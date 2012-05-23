package roborally.program;

import roborally.model.Robot;

/**
 * This class represents the or operator.
 * 
 * @author Ben Adriaenssens <ben.adriaenssens@student.kuleuven.be> - WtkCws,
 *         Toon Nolten <toon.nolten@student.kuleuven.be> - CwsElt.
 */
class Or extends CombinedCondition {

    /**
     * Initializes the or operator with a conditionString and a robot.
     * 
     * @param conditionString
     *      The conditionString containing the list of conditions.
     * @param robot
     *      The executing robot.
     */
    Or(String conditionString, Robot robot) {
        for (String subCondition : Program.allSubParenthesed(conditionString
                .substring(4, conditionString.length()))) {
            conditions.add(newCondition(subCondition, robot));
        }
    }

    /**
     * The method evaluate evaluates the conditions.
     * 
     * @return true if at least one of the conditions is true.
     * @return false otherwise.
     */
    @Override
    boolean evaluate() {
        for (Condition condition : conditions)
            if (condition.evaluate())
                return true;
        return false;
    }

    /*
     * (non-Javadoc)
     * @see roborally.program.Condition#toString()
     */
    @Override
    public String toString() {
        String conditionString = "(or";
        for (Condition condition : conditions)
            conditionString += " " + condition.toString();
        conditionString += ")";

        return conditionString;
    }
}
