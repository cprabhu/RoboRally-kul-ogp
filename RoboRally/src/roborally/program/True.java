package roborally.program;

class True extends BasicCondition {

    True() {
        super("(true)");
    }

    @Override
    boolean evaluate() {
        return true;
    }
}
