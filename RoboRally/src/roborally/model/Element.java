package roborally.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

public abstract class Element {
    
    Element(){
        
    }
    
    Element(Position position){
        this.position = position;
    }

    public void setPosition(Position position) {
        if(this.position != null)
            this.position.removeElement(this);
        position.addElement(this);
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }
    
    public void terminate() {
        if(position != null)
            position.removeElement(this);
        this.isTerminated = true;
    }

    @Raw
    @Basic
    public boolean isTerminated() {
        return isTerminated;
    }
    
    private boolean isTerminated;
    protected Position position;
}
