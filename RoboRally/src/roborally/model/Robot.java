package roborally.model;

import java.util.*;
import be.kuleuven.cs.som.annotate.*;
import roborally.IRobot;
// TODO
public class Robot implements IRobot {
	// TODO
	public Robot(long x, long y, int orientation, double energy) {
		this.setX(x);
		this.setY(y);
		this.setOrientation(orientation);
		this.setEnergy(energy);
	}

	/**
	 * Move one step in the direction this robot is facing.
	 * 
	 * @effect If this robot has sufficient energy it will move. | !(getEnergy()
	 *         >= 500) || (setY(getY() - 1) || setX(getX() + 1) | || setY(getY()
	 *         + 1) || setX(getX() - 1))
	 * @throws IllegalStateException
	 *             The robot has insufficient energy to move. | getEnergy() <
	 *             500
	 */
	public void move() throws IllegalStateException {
		if (getEnergy() < 500)
			throw new IllegalStateException();

		switch (getOrientation()) {
		case UP:
			setY(getY() - 1);
			break;
		case RIGHT:
			setX(getX() + 1);
			break;
		case DOWN:
			setY(getY() + 1);
			break;
		case LEFT:
			setX(getX() - 1);
			break;
		}
	}

	/**
	 * Turn this robot clockwise 90 degrees.
	 * 
	 * @post The new orientation is the old orientation plus 90 degrees
	 *       clockwise. | new.getOrientation() =
	 *       setOrientation((getOrientation() + 1) % 4)
	 */
	public void turnClockwise() throws IllegalStateException {
		if (getEnergy() >= 100)
			setOrientation((getOrientation() + 1) % 4);
	}

	/**
	 * Recharge this robots energy.
	 * 
	 * @param energyAmount
	 *            The amount of energy to be added to this robots energy.
	 * @pre The energyAmount to recharge is less than the difference between
	 *      getMaxEnergy() and getEnergy(). | energyAmount <= getMaxEnergy() -
	 *      getEnergy()
	 * @post The energy of this robot will be recharged by energyAmount. |
	 *       new.getEnergy() = this.getEnergy() + energyAmount
	 */
	public void recharge(double energyAmount) {
		assert (getEnergy() + energyAmount <= getMaxEnergy());
		setEnergy(getEnergy() + energyAmount);
	}
	
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
	 * 			| if (orientation == getOrientation()) orientation = orientation \ getOrientation()
	 * 			| if (orientation % 2 != getOrientation() % 2) orientation = orientation \ getOrientation()
	 * @effect setEnergy(getEnergy() - getEnergyRequiredToReach(x,y))
	 * @throws IllegalStateException
	 * 			This robot has insufficient energy to reach the given location.
	 *            | getEnergy() < getEnergyRequired()
	 */
	public void moveTo(long x, long y) throws IllegalStateException {
		if (getEnergy() < getEnergyRequiredToReach(x, y))
			throw new IllegalStateException();
		setEnergy(getEnergy() - getEnergyRequiredToReach(x, y));
		setX(x);
		setY(y);
		
		int xOrientation = LEFT;
		int yOrientation = DOWN;
		if (x > getX())
			xOrientation = RIGHT;
		if (y < getY())
			yOrientation = UP;
		
		if (x == getX())
			setOrientation(yOrientation);
		else if (y == getY())
			setOrientation(xOrientation);
		else {
			if (xOrientation == getOrientation())
				setOrientation(yOrientation);
			else if (yOrientation == getOrientation())
				setOrientation(xOrientation);
			else if (xOrientation % 2 == getOrientation() % 2)
				setOrientation(xOrientation);
			else if (yOrientation % 2 == getOrientation() % 2)
				setOrientation(yOrientation);
		}
	}
	
	// TODO
	public void moveNextTo(IRobot robot2) throws IllegalArgumentException {
		if (robot2 == null)
			throw new IllegalArgumentException();
		
		List<Long[][]> minManhattanPairs = new ArrayList<Long[][]>();
		long minManhattanDistance = getManhattanDistance(getX(), getY(), robot2.getX(), robot2.getY());
		
		for (Long[] robot1Coord : getBoundaryInQuadrant(robot2.getX(), robot2.getY())) {
			for (Long[] robot2Coord : robot2.getBoundaryInQuadrant(getX(), getY())) {
				Long[][] pair = {robot1Coord, robot2Coord};
				long manhattanDistance = getManhattanDistance(pair[0][0], pair[0][1], pair[1][0], pair[1][1]);
				
				if (manhattanDistance == minManhattanDistance)					
					minManhattanPairs.add(pair);
				
				else if (manhattanDistance < minManhattanDistance) {
					minManhattanPairs = new ArrayList<Long[][]>();
					minManhattanPairs.add(pair);
				}
					
			}
		}
		
		Long [][] firstPair = minManhattanPairs.get(0);
		double minPairEnergy = getEnergyRequiredToReach(firstPair[0][0], firstPair[0][1])
								+ robot2.getEnergyRequiredToReach(firstPair[1][0], firstPair[1][1]);
		List<Long[][]> minEnergyPairs = new ArrayList<Long [][]>();
		
		for (Long[][] pair : minManhattanPairs) {
			double pairEnergy = getEnergyRequiredToReach(pair[0][0], pair[0][1])
									+ robot2.getEnergyRequiredToReach(pair[1][0], pair[1][1]);
			if (pairEnergy == minPairEnergy)
				minEnergyPairs.add(pair);
			
			else if (pairEnergy < minPairEnergy) {
				minEnergyPairs = new ArrayList<Long[][]>();
				minEnergyPairs.add(pair);
			}
				
		}
		
		firstPair = minEnergyPairs.get(0);
		double minExpendedEnergyDifference = Math.abs(
				getEnergyRequiredToReach(firstPair[0][0], firstPair[0][1])
				- robot2.getEnergyRequiredToReach(firstPair[1][0], firstPair[1][1]));
		List<Long[][]> optimalPairs = new ArrayList<Long[][]>();
		
		for (Long[][] pair : minEnergyPairs) {
			double expendedEnergyDifference = Math.abs(
					getEnergyRequiredToReach(firstPair[0][0], firstPair[0][1])
					- robot2.getEnergyRequiredToReach(firstPair[1][0], firstPair[1][1]));
			if (expendedEnergyDifference == minExpendedEnergyDifference)
				optimalPairs.add(pair);
			else if (expendedEnergyDifference < minExpendedEnergyDifference) {
				optimalPairs = new ArrayList<Long[][]>();
				optimalPairs.add(pair);
			}
		}
		
		Long[][] optimalPair = optimalPairs.get(0);
		
		moveTo(optimalPair[0][0], optimalPair[0][1]);
		robot2.moveTo(optimalPair[1][0], optimalPair[1][0]);
	}

	/**
	 * Get the minimal energy this robot requires to reach a given position.
	 * 
	 * @param x
	 *            | The target position's x coordinate.
	 * @param y
	 *            | The target position's y coordinate.
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
	public double getEnergyRequiredToReach(long x, long y) {

		double energyRequired = (Math.abs(x - getX()) + Math.abs(y - getY())) * 500;

		int xDirection = LEFT;
		int yDirection = UP;

		if (x > getX())
			xDirection = RIGHT;
		if (y > getY())
			yDirection = DOWN;

		// if (x == getX()) {
		// if (getOrientation() % 2 == 1)
		// energyRequired += 100;
		// else if (getOrientation() != yDirection)
		// energyRequired += 200;
		// }
		// else if ( y == getY()) {
		// if (getOrientation() % 2 == 0)
		// energyRequired += 100;
		// else if (getOrientation() != xDirection)
		// energyRequired += 200;
		// }
		// else {
		// if (getOrientation() == xDirection || getOrientation() == yDirection)
		// energyRequired += 100;
		// else {
		// energyRequired += 200;
		// }
		// }

		// This array is used to avoid a meaningless amount of energy requiered
		// to turn.
		double[] energyArray = { 0, 100, 200, 100 };
		// There is an exceptional case where xTurnEnergy becomes 3 while it
		// should be 1. This case is covered by the energyArray.
		double xTurnEnergy = energyArray[Math
				.abs(getOrientation() - xDirection)];
		// There is an exceptional case where yTurnEnergy becomes 3 while it
		// should be 1. This case is covered by the energyArray.
		double yTurnEnergy = energyArray[Math
				.abs(getOrientation() - yDirection)];

		if (x == getX())
			energyRequired += yTurnEnergy;
		else if (y == getY())
			energyRequired += xTurnEnergy;
		else
			energyRequired += Math.max(xTurnEnergy, yTurnEnergy);

		return energyRequired;
	}

	// TODO
	public List<Long[]> getBoundaryInQuadrant(long x2, long y2) {
		List<Long[]> boundary = new ArrayList<Long[]>();
		
		int incrementX = -1;
		int incrementY = -1;
		if (getX() < x2)
			incrementX = 1;
		if (getY() < y2)
			incrementY = 1;
		
		for (long x1 = getX(); x1 != x2; x1 += incrementX) {
			for (long y1 = getY(); y1 != y2; y1 += incrementY) {
				if (! (getEnergyRequiredToReach(x1, y1) < getEnergy()))
					break;
				if (getEnergyRequiredToReach(x1 + incrementX, y1) > getEnergy()
					|| getEnergyRequiredToReach(x1, y1 + incrementY) > getEnergy()) {
						Long[] coord = {x1, y1};
						boundary.add(coord);
				}
			}
		}
		
		return boundary;
	}
	
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
	public void setX(long x) throws IllegalArgumentException {
		if (!isValidCoordinate(x))
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
	 *            The new y coordinate.
	 * @post The y coordinate of this robot will equal y. | new.getY() = y
	 * @throws IllegalArgumentException
	 *             The given y coordinate is out of range. | !
	 *             isValidCoordinate(y)
	 */
	public void setY(long y) throws IllegalArgumentException {
		if (!isValidCoordinate(y))
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
	 *            The new orientation.
	 * @post This robots new orientation will be the given orientation. |
	 *       new.orientation = orientation
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
	 *            The new amount of energy.
	 * @post This robots energy will equal the given energy. | new.getEnergy() =
	 *       energy
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
	 * @return The ration of getEnergy() over getMaxEnergy(). | result =
	 *         getEnergy()/getMaxEnergy()
	 */
	public double getEnergyRatio() {
		return getEnergy() / getMaxEnergy();
	}

	/**
	 * Get the maximum amount of energy.
	 */
	@Basic
	@Immutable
	public double getMaxEnergy() {
		return MAXENERGY;
	}

	/**
	 * Get the maximum board dimension.
	 */
	@Basic
	@Immutable
	public long getMaxBoardDimension() {
		return MAXBOARDDIMENSION;
	}

	public final static int UP = 0;
	public final static int RIGHT = 1;
	public final static int DOWN = 2;
	public final static int LEFT = 3;

	/**
	 * Get the manhattan distance between two sets of coordinates.
	 * 
	 * @param x1
	 *            x coordinate of the first position.
	 * @param y1
	 *            y coordinate of the first position.
	 * @param x2
	 *            x coordinate of the second position.
	 * @param y2
	 *            y coordinate of the second position.
	 * @return The manhattan distance between the two sets of coordinates. |
	 *         Math.abs(x1 - x2) + Math.abs(y1 - y2)
	 * @throws IllegalArgumentException
	 *             One or more of the given coordinates is out of range.
	 */
	private long getManhattanDistance(long x1, long y1, long x2, long y2)
			throws IllegalArgumentException {
		if (!(isValidCoordinate(x1) && isValidCoordinate(y1)
				&& isValidCoordinate(x2) && isValidCoordinate(y2)))
			throw new IllegalArgumentException();
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}
	
	
	/**
	 * Check the validity of a coordinate.
	 * 
	 * @param c
	 *            Coordinate to check.
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