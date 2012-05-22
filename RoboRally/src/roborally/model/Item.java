package roborally.model;

import be.kuleuven.cs.som.annotate.Basic;
import roborally.model.auxiliary.Position;
import roborally.model.auxiliary.Weight;
import roborally.model.auxiliary.Weight.unitOfMass;

/**
 * This class represents an item.
 * 
 * @invar getWeight() != null
 * 
 * @author Ben Adriaenssens <ben.adriaenssens@student.kuleuven.be> - WtkCws,
 *         Toon Nolten <toon.nolten@student.kuleuven.be> - CwsElt.
 */
public abstract class Item extends Element {

    /**
     * Initializes this Item.
     * 
     * @effect ...
     *      | super()
     * @post ...
     *      | new.getWeight() = new Weight(100, unitOfMass.g)
     */
    Item() {
        super();
        weight = new Weight(100, unitOfMass.g);
    }

    /**
     * Initializes this Item with a position.
     * 
     * @param position
     *      The position of this Item.
     * @effect ...
     *      | super(position)
     * @post ...
     *      | new.getWeight() = new Weight(100, unitOfMass.g)
     */
    Item(Position position) {
        super(position);
        weight = new Weight(100, unitOfMass.g);
    }

    /**
     * Initializes this Item with a position and a weight.
     * 
     * @param position
     *      The position of this Item.
     * @param weight
     *      The weight of this Item.
     * @effect ...
     *      | super(position)
     * @post ...
     *      | new.getWeight() = weight
     */
    Item(Position position, Weight weight) {
        super(position);
        this.weight = weight;
    }

    /**
     * Returns the weight of this item.
     */
    @Basic
    public Weight getWeight() {
        return weight;
    }

    /**
     * This method is executed when this Item is hit.
     */
    public abstract void hit();

    /**
     * This method lets the robot use this item.
     * 
     * @param robot
     *      The robot to use this Item.
     */
    public abstract void use(Robot robot);

    protected final Weight weight;
}
