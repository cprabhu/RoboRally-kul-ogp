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

	}

	public double getEnergyRequiredToReach(long x, long y) {
		double energyRequired = 0;
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
	 * 			| (x < 0) || (x > getMaxBoardDimension())
	 */
	public void setX(long x) throws IllegalArgumentException {
		if (x < 0 || x > getMaxBoardDimension())
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
	 * 			| (y < 0) || (y > getMaxBoardDimension())
	 */
	public void setY(long y) throws IllegalArgumentException {
		if (y < 0 || y > getMaxBoardDimension())
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
	

	private final static int UP = 0;
	private final static int RIGHT = 1;
	private final static int DOWN = 2;
	private final static int LEFT = 3;
	private final static double MAXENERGY = 20000;
	private final static long MAXBOARDDIMENSION = Long.MAX_VALUE;
	private long x;
	private long y;
	private int orientation;
	private double energy;
}