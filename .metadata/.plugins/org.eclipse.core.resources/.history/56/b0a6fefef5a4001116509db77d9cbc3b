package roborally.model;

import roborally.model.auxiliary.Position;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * The class battery represents an element.
 * 
 * @author Ben Adriaenssens <ben.adriaenssens@student.kuleuven.be> - WtkCws,
 *         Toon Nolten <toon.nolten@student.kuleuven.be> - CwsElt.
 */
public abstract class Element {

    /**
     * Initializes this Element.
     * 
     * @post ...
     *      | new.isTerminated() = false.
     */
    Element() {
        this.isTerminated = false;
    }

    /**
     * Initializes this Element.
     * 
     * @param position
     *      The position of this Element.
     * @effect ...
     *      | setPosition(position)
     * @post ...
     *      | new.isTerminated() = false.
     */
    Element(Position position) {
        setPosition(position);
        this.isTerminated = false;
    }

    /**
     * Set the position of this Element to the given position if the position
     * exists and the position can contain this Element.
     * 
     * @param position
     *      The position to set for this Element.
     * @effect ...
     *      | positionCanContainElement == (!isTerminated() && position != null
     *      |   && position.canContainElement(this))
     *      | if (positionCanContainElement && this.position != null)
     *      |   removePosition()
     * @effect ...
     *      | if (positionCanContainElement)
     *      |   position.addElement(this)
     * @post ...
     *      | if (positionCanContainElement)
     *      |   this.position = position
     */
    public void setPosition(Position position) {
        if (!isTerminated() && position != null
                && position.canContainElement(this)) {
            if (this.position != null)
                removePosition();
            position.addElement(this);
            this.position = position;
        }
    }

    /**
     * Return this Element's position.
     */
    @Basic
    public Position getPosition() {
        return position;
    }

    /**
     * Remove this Element's position.
     * 
     * @effect ...
     *      | If (this.getPosition() != null
     *      |   && this.getPosition().getElements.contains(this))
     *      |   this.getPosition().removeElement(this)
     * @post ...
     *      | new.getPosition() = null
     */
    public void removePosition() {
        if (position != null && position.getElements().contains(this))
            this.position.removeElement(this);
        position = null;
    }

    /**
     * Hit this Element.
     */
    public abstract void hit();

    /**
     * Terminate this Element.
     * 
     * @effect ...
     *      | if (this.getPosition() != null)
     *      |   this.getPosition().removeElement(this)
     * @post ...
     *      | new.getPosition() = null
     * @post ...
     *      | new.isTerminated() = true
     */
    public void terminate() {
        if (position != null)
            position.removeElement(this);
        position = null;
        this.isTerminated = true;
    }

    /**
     * Check if this Element is terminated.
     */
    @Raw
    @Basic
    public boolean isTerminated() {
        return isTerminated;
    }

    protected boolean isTerminated;
    protected Position position;
}
