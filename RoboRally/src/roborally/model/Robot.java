package roborally.model;

import be.kuleuven.cs.som.annotate.*;
import roborally.IRobot;

public class Robot implements IRobot {

	public Robot(long x, long y, int orientation, double energy) {
		this.setX(x);
		this.setY(y);
		this.setOrientation(orientation);
		this.setEnergy(energy);
	}

	/**
	 * Move one step in the direction this robot is facing.
	 * 
	 * @effect If this robot has sufficient energy it will move.
	 * 			| !(getEnergy() >= 500) || (setY(getY() - 1) || setX(getX() + 1)
	 * 			| || setY(getY() + 1) || setX(getX() - 1))
	 * @throws IllegalStateException
	 * 			The robot has insufficient energy to move.
	 * 			| getEnergy() < 500
	 */
	public void move() throws IllegalStateException {
		if (getEnergy() < 500) 
			throw new IllegalStateException();
		
		switch(getOrientation()) {
		case UP: setY(getY() - 1);
			break;
		case RIGHT: setX(getX() + 1);
			break;
		case DOWN: setY(getY() + 1);
			break;
		case LEFT: setX(getX() - 1);
			break;
		}
	}

	/**
	 * Turn this robot clockwise 90 degrees.
	 * 
	 * @post The new orientation is the old orientation plus 90 degrees
	 * 			clockwise.
	 * 			| new.getOrientation() = setOrientation((getOrientation() + 1) % 4)
	 */
	public void turnClockwise() throws IllegalStateException{
		if (getEnergy() >= 100)
			setOrientation((getOrientation() + 1) % 4);
	}
	
	/**
	 * Turn this robot counterclockwise 90 degrees.
	 * 
	 * @effect The new orientation is the old orientation minus 90 degrees
	 * 			counterclockwise.
	 * 			| new.getOrientation() = setOrientation((getOrientation() + 3) % 4)
	 */
	public void turnCounterClockwise() {
		if (getEnergy() >= 100)
			setOrientation((getOrientation() +3) % 4);
	}

	/**
	 * Recharge this robots energy.
	 * 
	 * @param energyAmount
	 * 			The amount of energy to be added to this robots energy.
	 * @pre The energyAmount to recharge is less than the difference between
	 * 			getMaxEnergy() and getEnergy().
	 * 			| energyAmount <= getMaxEnergy() - getEnergy()
	 * @post The energy of this robot will be recharged by energyAmount.
	 * 			| new.getEnergy() = this.getEnergy() + energyAmount
	 */
	public void recharge(double energyAmount) {
		assert (getEnergy() + energyAmount <= getMaxEnergy());
		setEnergy(getEnergy() + energyAmount);
	}

	public void moveNextTo(IRobot robot2) {
		moveTo(robot2.getY(),robot2.getX());
	}

	/**
	 * Get the minimal energy this robot requires to reach a given position.
	 * 
	 * ???@pre x, y > 0???
	 * @param x
	 * 			| The target position's x coordinate.
	 * @param y
	 * 			| The target position's y coordinate.
	 * @return The minimal energy this robot requires to reach the given position.
	 * 			| energyToMove = (Math.abs(x - getX()) + Math.abs(y - getY())) * 500
	 * 			| xTurnDistance = Math.abs(getOrientation() - xDirection)
	 * 			| yTurnDistance = Math.abs(getOrientation() - yDirection)
	 * 			| xDirection = LEFT
	 * 			| yDirection = UP
	 * 			| if(x > getX()) xDirection = RIGHT
	 * 			| if(y > getY()) yDirection = DOWN
	 * 			| if (x == getX())
	 * 			|	if (yTurnDistance == 0)
	 * 			|		energyToTurn = 0
	 * 			|	else if (yTurnDistance == 2)
	 * 			|		energyToTurn = 200
	 * 			|	else
	 * 			| 		energyToTurn = 100
	 * 			| else if (y == getY())
	 * 			|	if (xTurnDistance == 0)
	 * 			|		energyToTurn = 0
	 * 			|	else if (xTurnDistance == 2)
	 * 			|		energyToTurn = 200
	 * 			|	else
	 * 			| 		energyToTurn = 100
	 * 			| else
	 * 			|	if (xTurnDistance == 3)
	 * 			|		xTurnDistance = 1
	 * 			|	if (yTurnDistance == 3)
	 * 			|		yTurnDistance = 1
	 * 			|	if (Math.max(xTurnDistance,yTurnDistance) == 0)
	 * 			|		energyToTurn = 0
	 * 			|	else if (Math.max(xTurnDistance,yTurnDistance) == 2)
	 * 			|		energyToTurn = 200
	 * 			|	else
	 * 			| 		energyToTurn = 100
	 * 			|
	 * 			| result == energyToMove + energyToTurn
	 * @note No matter which path the robot takes it will allways have to move
	 * 			the same horizontal and vertical distance. Which can be
	 * 			calculated with the mathematical formula for perpendicular distance: |a-b|
	 * @note A robot will allways have to turn either 0, 1 or 2 quarter turns
	 * 			to move closer to a target. The 'turn distance' between the
	 * 			target's direction and the robot's orientation needs to be
	 * 			calculated to determine the number of quarter turns.
	 */
	public double getEnergyRequiredToReach(long x, long y) {
		
		double energyRequired = (Math.abs(x - getX()) + Math.abs(y - getY())) * 500;
		
		int xDirection = LEFT;
		int yDirection = UP;
		
		if (x > getX())
			xDirection = RIGHT;
		if(y > getY())
			yDirection = DOWN;
		
//		if (x == getX()) {
//			if (getOrientation() % 2 == 1)
//				energyRequired += 100;
//			else if (getOrientation() != yDirection)
//				energyRequired += 200;
//		}
//		else if ( y == getY()) {
//			if (getOrientation() % 2 == 0)
//				energyRequired += 100;
//			else if (getOrientation() != xDirection)
//				energyRequired += 200;
//		}
//		else {
//			if (getOrientation() == xDirection || getOrientation() == yDirection)
//				energyRequired += 100;
//			else {
//				energyRequired += 200;
//			}
//		}
		
		// This array is used to avoid a meaningless amount of energy requiered to turn.
		double[] energyArray = {0,100,200,100};
		// There is an exceptional case where xTurnEnergy becomes 3 while it should be 1. This case is covered by the energyArray.
		double xTurnEnergy = energyArray[Math.abs(getOrientation() - xDirection)];
		// There is an exceptional case where yTurnEnergy becomes 3 while it should be 1. This case is covered by the energyArray.
		double yTurnEnergy = energyArray[Math.abs(getOrientation() - yDirection)];
			
		if (x == getX())
			energyRequired += yTurnEnergy;
		else if (y == getY())
			energyRequired += xTurnEnergy;
		else
			energyRequired += Math.max(xTurnEnergy, yTurnEnergy);

		return energyRequired;
	}

	/**
	 * Set the x coordinate of this robot.
	 * 
	 * @param x
	 * 			The new x coordinate.
	 * @post The x coordinate of this robot will equal x.
	 * 			| new.getX() = x
	 * @throws IllegalArgumentException
	 * 			The given x coordinate is out of range.
	 * 			| ! isValidCoordinate(x)
	 */
	public void setX(long x) throws IllegalArgumentException {
		if (! isValidCoordinate(x))
			throw new IllegalArgumentException();
		this.x = x;
	}

	/**
	 * Get the x coordinate of this robot.

	 */
	@Basic
	public long getX() {
		return x;
	}

	/**
	 * Set the y coordinate of this robot.
	 * 
	 * @param y
	 * 			The new y coordinate.
	 * @post  The y coordinate of this robot will equal y.
	 * 			| new.getY() = y
	 * @throws IllegalArgumentException
	 * 			The given y coordinate is out of range.
	 * 			| ! isValidCoordinate(y)
	 */
	public void setY(long y) throws IllegalArgumentException {
		if (! isValidCoordinate(y))
			throw new IllegalArgumentException();
		this.y = y;
	}

	/**
	 * Get the y coordinate of this robot.
	 */
	@Basic
	public long getY() {
		return y;
	}

	/**
	 * Set the this robots orientation to the given orientation.
	 * 
	 * @param orientation
	 * 			The new orientation.
	 * @post This robots new orientation will be the given orientation.
	 * 			| new.orientation = orientation
	 */
	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	/**
	 * Get the orientation of this robot.
	 */
	@Basic
	public int getOrientation() {
		return orientation;
	}

	/**
	 * Set the energy to the given amount.
	 * 
	 * @param energy
	 * 			The new amount of energy.
	 * @post This robots energy will equal the given energy.
	 * 			| new.getEnergy() = energy
	 */
	public void setEnergy(double energy) {
		this.energy = energy;
	}

	/**
	 * Get the current energy of this robot.
	 */
	@Basic
	public double getEnergy() {
		return energy;
	}
	
	/**
	 * Get this robots current energy as a ratio over the maximum energy.
	 * 
	 * @return The ration of getEnergy() over getMaxEnergy().
	 * 			| result = getEnergy()/getMaxEnergy()
	 */
	public double getEnergyRatio() {
		return getEnergy()/getMaxEnergy();
	}
	
	/**
	 * Get the maximum amount of energy.
	 */
	@Basic @Immutable
	public double getMaxEnergy(){
		return MAXENERGY;
	}
	
	/**
	 * Get the maximum board dimension.
	 */
	@Basic @Immutable
	public long getMaxBoardDimension() {
		return MAXBOARDDIMENSION;
	}
	
	public final static int UP = 0;
	public final static int RIGHT = 1;
	public final static int DOWN = 2;
	public final static int LEFT = 3;
	
	/**
	 * Move this robot to given location if it has sufficient energy.
	 * 
	 * @param x
	 * 			x coordinate of location.
	 * @param y
	 * 			y coordinate of location
	 * 			This robot has insufficient energy to reach the given location.
	 * 			| getEnergy() < getEnergyRequired()
	 * @effect setX(x)
	 * @effect setY(y)
	 * @effect setEnergy(getEnergy() - getEnergyRequiredToReach(x,y))
	 * @throws IllegalStateException
	 */
	private void moveTo(long x, long y) throws IllegalStateException {
		if (getEnergy() < getEnergyRequiredToReach(x,y))
			throw new IllegalStateException();
		setEnergy(getEnergy() - getEnergyRequiredToReach(x, y));
		setX(x);
		setY(y);
	}
	
	/**
	 * Get the manhattan distance between two sets of coordinates.
	 * 
	 * @param x1
	 * 			x coordinate of the first position.
	 * @param y1
	 * 			y coordinate of the first position.
	 * @param x2
	 * 			x coordinate of the second position.
	 * @param y2
	 * 			y coordinate of the second position.
	 * @return The manhattan distance between the two sets of coordinates.
	 * 			| Math.abs(x1 - x2) + Math.abs(y1 - y2)
	 * @throws IllegalArgumentException
	 * 			One or more of the given coordinates is out of range.
	 */
	private long getManhattanDistance(long x1, long y1, long x2, long y2) throws IllegalArgumentException{
		if (! (isValidCoordinate(x1) && isValidCoordinate(y1)
				&& isValidCoordinate(x2) && isValidCoordinate(y2)))
			throw new IllegalArgumentException();
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}
	
	/**
	 * Check the validity of a coordinate.
	 * @param c
	 * 			Coordinate to check.
	 */
	@Basic
	private boolean isValidCoordinate(long c) {
		return c > 0 || c < getMaxBoardDimension();
	}

	
	private final static double MAXENERGY = 20000;
	private final static long MAXBOARDDIMENSION = Long.MAX_VALUE;
	private long x;
	private long y;
	private int orientation;
	private double energy;
}