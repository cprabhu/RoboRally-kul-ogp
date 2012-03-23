package roborally;

import java.util.List;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

public interface IRobot {

    /**
     * Move one step in the direction this robot is facing.
     * 
     * @effect If this robot has sufficient energy it will move.
     *          | !(getEnergy() >= 500) || ((setY(getY() - 1) || setX(getX() + 1)
     *          | || setY(getY() + 1) || setX(getX() - 1))
     *          | && setEnergy(getEnergy() - 500))
     * @throws IllegalStateException
     *          The robot has insufficient energy to move.
     *          | getEnergy() < 500
     */
    public abstract void move() throws IllegalStateException;

    /**
     * Turn this robot clockwise 90 degrees.
     * 
     * @post The new orientation is the old orientation plus 90 degrees
     *       clockwise. | new.getOrientation() =
     *       setOrientation((getOrientation() + 1) % 4)
     */
    public abstract void turnClockwise();

    /**
     * Recharge this robots energy.
     * 
     * @param energyAmount
     *            The amount of energy to be added to this robots energy.
     * @pre The energyAmount to recharge is greater than zero and less than the
     *         difference between getMaxEnergy() and getEnergy().
     *         | energyAmount <= getMaxEnergy() - getEnergy()
     *         | && energyAmount > 0
     * @post The energy of this robot will be recharged by energyAmount.
     *         | new.getEnergy() = this.getEnergy() + energyAmount
     */
    public abstract void recharge(double energyAmount);

    /**
     * Move this robot to given location if it has sufficient energy.
     * 
     * @param x
     *            x coordinate of location.
     * @param y
     *            y coordinate of location.
     * @effect setX(x)
     * @effect setY(y)
     * @effect setOrientation(orientation)
     * 			orientation is the direction the robot should face.
     * 			| orientation = {UP, RIGHT, DOWN, LEFT}
     * 			| if (x <= getX()) orientation = orientation \{RIGHT}
     * 			| if (x >= getX()) orientation = orientation \{LEFT}
     * 			| if (y <= getY()) orientation = orientation \{UP}
     * 			| if (y >= getY()) orientation = orientation \{DOWN}
     * 			| if (orientation == getOrientation())
     *          |   orientation = orientation \ getOrientation()
     * 			| if (orientation % 2 != getOrientation() % 2)
     *          |   orientation = orientation \ getOrientation()
     * @effect setEnergy(getEnergy() - getEnergyRequiredToReach(x,y))
     * @throws IllegalStateException
     *         This robot has insufficient energy to reach the given location.
     *         | getEnergy() < getEnergyRequired()
     */
    public abstract void moveTo(long x, long y) throws IllegalStateException;

    // TODO
    public abstract void moveNextTo(IRobot robot2)
            throws IllegalArgumentException;

    /**
     * Get the minimal energy this robot requires to reach a given position.
     * 
     * @param x
               | The target position's x coordinate.
     * @param y
     *         | The target position's y coordinate.
     * @pre The x and y coordinates have to be valid.
     *         | isValidCoordinate(x) && isValidCoordinate(y)
     * @return The minimal energy this robot requires to reach the given
     *         position. | energyToMove = (Math.abs(x - getX()) + Math.abs(y -
     *         getY())) * 500 | xTurnDistance = Math.abs(getOrientation() -
     *         xDirection) | yTurnDistance = Math.abs(getOrientation() -
     *         yDirection) | xDirection = LEFT | yDirection = UP | if(x >
     *         getX()) xDirection = RIGHT | if(y > getY()) yDirection = DOWN |
     *         if (x == getX()) | if (yTurnDistance == 0) | energyToTurn = 0 |
     *         else if (yTurnDistance == 2) | energyToTurn = 200 | else |
     *         energyToTurn = 100 | else if (y == getY()) | if (xTurnDistance ==
     *         0) | energyToTurn = 0 | else if (xTurnDistance == 2) |
     *         energyToTurn = 200 | else | energyToTurn = 100 | else | if
     *         (xTurnDistance == 3) | xTurnDistance = 1 | if (yTurnDistance ==
     *         3) | yTurnDistance = 1 | if
     *         (Math.max(xTurnDistance,yTurnDistance) == 0) | energyToTurn = 0 |
     *         else if (Math.max(xTurnDistance,yTurnDistance) == 2) |
     *         energyToTurn = 200 | else | energyToTurn = 100 | | result ==
     *         energyToMove + energyToTurn
     * @note No matter which path the robot takes it will allways have to move
     *       the same horizontal and vertical distance. Which can be calculated
     *       with the mathematical formula for perpendicular distance: |a-b|
     * @note A robot will allways have to turn either 0, 1 or 2 quarter turns to
     *       move closer to a target. The 'turn distance' between the target's
     *       direction and the robot's orientation needs to be calculated to
     *       determine the number of quarter turns.
     */
    public abstract double getEnergyRequiredToReach(long x, long y);

    // TODO
    public abstract List<Long[]> getReachableInQuadrant(long x2, long y2);

    /**
     * Set the x coordinate of this robot.
     * 
     * @param x
     *            The new x coordinate.
     * @post The x coordinate of this robot will equal x. | new.getX() = x
     * @throws IllegalArgumentException
     *             The given x coordinate is out of range. | !
     *             isValidCoordinate(x)
     */
    public abstract void setX(long x) throws IllegalArgumentException;

    /**
     * Get the x coordinate of this robot.
     */
    @Basic
    public abstract long getX();

    /**
     * Set the y coordinate of this robot.
     * 
     * @param y
     *            The new y coordinate.
     * @post The y coordinate of this robot will equal y. | new.getY() = y
     * @throws IllegalArgumentException
     *             The given y coordinate is out of range. | !
     *             isValidCoordinate(y)
     */
    public abstract void setY(long y) throws IllegalArgumentException;

    /**
     * Get the y coordinate of this robot.
     */
    @Basic
    public abstract long getY();

    /**
     * Set the this robots orientation to the given orientation.
     * 
     * @param orientation
     *            The new orientation.
     * @post This robots new orientation will be the given orientation if the
     *         orientation is valid, the old orientation otherwise, unless
     *         the old orientation is not valid then it will be UP.
     *         | if (isValidOrientation(orientation)
     *         |   new.orientation = orientation;
     *         | else if (isValidOrietation(getOrientation()))
     *         |   new.orientation = getOrientation()
     *         | else
     *         |   new.orientation = UP
     */
    public abstract void setOrientation(int orientation);

    /**
     * Get the orientation of this robot.
     */
    @Basic
    public abstract int getOrientation();

    /**
     * Set the energy to the given amount.
     * 
     * @param energy
     *            The new amount of energy.
     * @post This robots energy will equal the given energy. | new.getEnergy() =
     *       energy
     */
    public abstract void setEnergy(double energy);

    /**
     * Get the current energy of this robot.
     */
    @Basic
    public abstract double getEnergy();

    /**
     * Get this robots current energy as a ratio over the maximum energy.
     * 
     * @return The ration of getEnergy() over getMaxEnergy(). | result =
     *         getEnergy()/getMaxEnergy()
     */
    public abstract double getEnergyRatio();

    /**
     * Get the maximum amount of energy.
     */
    @Basic
    @Immutable
    public abstract double getMaxEnergy();

}