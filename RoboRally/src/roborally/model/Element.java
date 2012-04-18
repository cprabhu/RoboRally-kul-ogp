package roborally.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

public abstract class Element {
    
    Element(){
        this.isTerminated = false;

    }
    
    //TODO: isValidPosition (wall)?
    Element(Position position){
        setPosition(position);
        this.isTerminated = false;

    }

    public void setPosition(Position position) {
        if(this.position != null)
            removePosition();
        position.addElement(this);
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }
    
    public void removePosition() {
        if (position != null && position.getElements().contains(this))
            this.position.removeElement(this);
        position = null;
    }
    
    public void terminate() {
        if(position != null)
            position.removeElement(this);
        position = null;
        this.isTerminated = true;
    }

    @Raw
    @Basic
    public boolean isTerminated() {
        return isTerminated;
    }
    
    protected boolean isTerminated;
    protected Position position;
}
