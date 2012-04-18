package roborally.model;

import roborally.model.Energy.unitOfPower;

//TODO: energy nominally
//TODO: orientation totally
//TODO: position defensively

public class Robot extends Element {
    
    public Robot() {
        energy = new Energy(100, unitOfPower.Ws);
        maxEnergy = new Energy(100, unitOfPower.Ws);
        orientation = Orientation.UP;
        position = new Position(3, 3, new Board(6, 6));
        energy.getClass();
        maxEnergy.getClass();
        orientation.getClass();
        position.getClass();
    }
    
    //TODO: query amountofenergy in Ws
    //TODO: query amountofenergy as fraction
    //TODO: recharge robot with certain amount of energy
    //TODO: turn robot 90 CW or CCW costs 100 Ws
    //TODO: inspect orientation
    //TODO: inspect position
    //TODO: setposition? override?
    //TODO: move costs 500 Ws plus 50 Ws/ kg of carry
    //TODO: getminimumenergyrequiredtoreach
    //TODO: movenextto
    //TODO: shoot costs 1000Ws
    //TODO: pickup
    //TODO: use
    //TODO: drop
    //TODO: return i'th heaviest item in constant time
    
    private final Energy energy;
    private final Energy maxEnergy;
    private final Orientation orientation;
    private Position position;
    
}
