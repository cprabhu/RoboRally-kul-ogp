package roborally.model;

import roborally.model.Energy.unitOfPower;

public enum Orientation {
    UP, RIGHT, DOWN, LEFT;

    public Orientation turnClockwise90() {
        switch (this) {
        case UP:
            return RIGHT;
        case RIGHT:
            return DOWN;
        case DOWN:
            return LEFT;
        case LEFT:
            return UP;
        default:
            return UP;
        }
    }

    public Orientation turnCounterClockwise90() {
        switch (this) {
        case UP:
            return LEFT;
        case RIGHT:
            return UP;
        case DOWN:
            return RIGHT;
        case LEFT:
            return DOWN;
        default:
            return UP;
        }
    }

    public Orientation turn180() {
        switch (this) {
        case UP:
            return DOWN;
        case RIGHT:
            return LEFT;
        case DOWN:
            return UP;
        case LEFT:
            return RIGHT;
        default:
            return UP;
        }
    }

    public Energy getEnergyRequiredForOrientation(Orientation orientation,
            Energy energyToTurn) {
        Energy turnEnergy = new Energy(0, unitOfPower.Ws);

        if (this.turnClockwise90() == orientation
                || this.turnCounterClockwise90() == orientation)
            turnEnergy.addEnergy(energyToTurn);
        if (this.turn180() == orientation) {
            turnEnergy.addEnergy(energyToTurn);
            turnEnergy.addEnergy(energyToTurn);
        }

        return turnEnergy;
    }
    
    public Position nextPosition(Position currentPosition) {
        long x = currentPosition.X;
        long y = currentPosition.Y;
        Board board = currentPosition.BOARD;
        
        switch (this) {
        case UP:
            return Position.newPosition(x, y - 1, board);
        case RIGHT:
            return Position.newPosition(x + 1, y, board);
        case DOWN:
            return Position.newPosition(x, y + 1, board);
        case LEFT:
            return Position.newPosition(x - 1, y, board);
        default:
            return currentPosition;
        }
    }
}
