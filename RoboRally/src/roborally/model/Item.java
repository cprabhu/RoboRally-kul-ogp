package roborally.model;

import roborally.model.Weight.unitOfMass;

public abstract class Item extends Element {

    Item() {
        super();
        weight = new Weight(100, unitOfMass.g);
    }

    Item(Position position) {
        super(position);
        weight = new Weight(100, unitOfMass.g);
    }

    Item(Position position, Weight weight) {
        super(position);
        this.weight = weight;
    }

    public Weight getWeight() {
        return weight;
    }

    public abstract void hit();

    public abstract void use(Robot robot);

    protected final Weight weight;
}
