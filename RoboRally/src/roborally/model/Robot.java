package roborally.model;

import roborally.model.Energy.unitOfPower;
import roborally.model.Weight.unitOfMass;
import java.util.*;

// TODO: energy nominally
// TODO: orientation totally
// TODO: position defensively

public class Robot extends Element {

    public Robot(Energy initialEnergy, Orientation initialOrienation) {
        energy = initialEnergy;
        maxEnergy = new Energy(20000, unitOfPower.Ws);
        orientation = initialOrienation;
        position = null;
        carryWeight = null;
    }

    public void recharge(Energy chargeEnergy) {
        energy.recharge(chargeEnergy, maxEnergy);
    }

    // TODO: Implementatie getEnergyRequiredToReach
    public Energy getEnergyRequiredToReach(Position destination) {
        return energy;
    }

    public double getEnergyRequiredToReachWs(Position destination) {
        return getEnergyRequiredToReach(destination).getAmountOfEnergy();
    }

    public double getAmountOfEnergy() {
        return energy.getAmountOfEnergy();
    }

    public double getFractionOfEnergy() {
        return energy.getAmountOfEnergy() / maxEnergy.getAmountOfEnergy();
    }

    public void turnClockwise90() {
        if (energyToTurn.compareTo(energy) < 1) {
            orientation.turnClockwise90();
            energy.removeEnergy(energyToTurn);
        }
    }

    public void turnCounterClockwise90() {
        if (energyToTurn.compareTo(energy) < 1) {
            orientation.turnCounterClockwise90();
            energy.removeEnergy(energyToTurn);
        }
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public int getOrientationInt() {
        return orientation.ordinal();
    }

    public void move() {
        Energy energyToMove = new Energy(500, unitOfPower.Ws);
        Energy weightEnergy = new Energy(50 * getCarriedWeight(unitOfMass.kg),
                unitOfPower.Ws);
        energyToMove.addEnergy(weightEnergy);
        if (energyToMove.compareTo(energy) < 1) {
            Position nextPosition = orientation.nextPosition(position);
            setPosition(nextPosition);
            energy.removeEnergy(energyToMove);
        }
    }

    public void moveNextTo(Robot robot2) {

    }

    public Position getPosition() {
        return position;
    }

    public void shoot() {
        energy.removeEnergy(new Energy(1000, unitOfPower.Ws));
        Position bulletPosition = position;
        while (position.BOARD.isValidPosition(bulletPosition)
                && !position.BOARD.isOccupiedPosition(bulletPosition)) {
            bulletPosition = orientation.nextPosition(bulletPosition);
        }

        int item = new Random().nextInt(bulletPosition.getElements().size());
        int i = 0;
        for (Element elem : bulletPosition.getElements()) {
            if (i == item)
                elem.terminate();
            i = i + 1;
        }
    }

    public void pickup(Battery battery) {
        if (battery != null)
            if (position.equals(battery.getPosition())) {
                for (int i = 0; i < batteries.size(); i++) {
                    if (battery.getWeight().compareTo(
                            batteries.get(i).getWeight()) > -1) {
                        batteries.add(i, battery);
                        break;
                    }
                }
                battery.removePosition();
            }
    }

    public void use(Battery battery) {
        if (battery != null) {
            if (battery.isTerminated())
                batteries.remove(battery);
            if (batteries.contains(battery))
                battery.charge(energy, maxEnergy);
        }
    }

    public void drop(Battery battery) {
        if (battery != null) {
            batteries.remove(battery);
            battery.setPosition(position);
        }
    }

    public Battery ithHeaviestElement(int ordinal) {
        return batteries.get(ordinal - 1);
    }
    
    public Set<Battery> getPossesions() {
        Set<Battery> possesions = new HashSet<Battery>();
        possesions.addAll(batteries);
        return possesions;
    }

    public int getCarriedWeight(unitOfMass unit) {
        int carriedWeight = 0;
        for (Battery battery : batteries)
            carriedWeight += battery.getWeight().getMassIn(unit);
        return carriedWeight;
    }

    private final Energy energy;
    private final Energy maxEnergy;
    private static final Energy energyToTurn = new Energy(100, unitOfPower.Ws);
    private final Orientation orientation;
    private Position position;
    private final Weight carryWeight;
    private final List<Battery> batteries = new ArrayList<Battery>();

}
