package roborally.model;

import java.util.*;
import be.kuleuven.cs.som.annotate.*;
import roborally.IRobot;

/**
 * A class for representing robots with a position an orientation and an amount
 * of energy.
 * 
 * @invar The position is a valid position that consists of an x and a y
 *      coordinate.
 *      | isValidCoordinate(getX()) && isValidCoordinate(getY())
 * @invar The orientation is a valid orientation.
 *      | isValidOrientation(getOrientation())
 * @invar The amount of energy is a valid amount.
 *      | isValidEnergy(getEnergy())
 * 
 * @author Ben Adriaenssens (ben.adriaenssens@student.kuleuven.be) - WtkCws,
 *          Toon Nolten (toon.nolten@student.kuleuven.be) - CwsElt
 */
public class Robot implements IRobot {

    /**
     * Construct a robot given a starting position, orientation and energy.
     * 
     * @param x
     *      X coordinate for this robot.
     * @param y
     *      Y coordinate for this robot.
     * @param orientation
     *      Orientation for this robot.
     * @param energy
     *      Energy for this robot.
     */
    public Robot(long x, long y, int orientation, double energy) {
        this.setX(x);
        this.setY(y);
        this.setOrientation(orientation);
        if (isValidEnergy(energy))
            this.setEnergy(energy);
        else
            this.setEnergy(getMaxEnergy());
    }

    /**
     * Move one step in the direction this robot is facing.
     * 
     * @effect If this robot has sufficient energy it will move.
     *      | !(getEnergy() >= 500) || ((setY(getY() - 1) || setX(getX() + 1)
     *      | || setY(getY() + 1) || setX(getX() - 1))
     *      | && setEnergy(getEnergy() - 500))
     * @throws IllegalStateException
     *      The robot has insufficient energy to move.
     *      | getEnergy() < 500
     */
    public void move() throws IllegalStateException {
        if (getEnergy() < 500)
            throw new IllegalStateException();

        setEnergy(getEnergy() - 500);
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
     *      clockwise.
     *      | new.getOrientation() = setOrientation((getOrientation() + 1) % 4)
     */
    public void turnClockwise() {
        if (getEnergy() >= 100)
            setOrientation((getOrientation() + 1) % 4);
    }

    /**
     * Recharge this robots energy with a given amount.
     * 
     * @param energyAmount
     *      The amount of energy to be added to this robots energy.
     * @pre The energyAmount to recharge is greater than zero and less than the
     *      difference between getMaxEnergy() and getEnergy().
     *      | energyAmount > 0
     *      | && energyAmount <= getMaxEnergy() - getEnergy()
     * @post The energy of this robot will be recharged by energyAmount.
     *      | new.getEnergy() = this.getEnergy() + energyAmount
     */
    public void recharge(double energyAmount) {
        assert (getEnergy() + energyAmount <= getMaxEnergy());
        assert (energyAmount > 0);
        setEnergy(getEnergy() + energyAmount);
    }

    /**
     * Move this robot to given location if it has sufficient energy.
     * 
     * @param x
     *      X coordinate of location.
     * @param y
     *      Y coordinate of location.
     * @effect setX(x)
     * @effect setY(y)
     * @effect setOrientation(orientation)
     *      orientation is the direction the robot should face.
     *      | orientation = {UP, RIGHT, DOWN, LEFT}
     *      | if (x <= getX()) orientation = orientation \{RIGHT}
     *      | if (x >= getX()) orientation = orientation \{LEFT}
     *      | if (y <= getY()) orientation = orientation \{UP}
     *      | if (y >= getY()) orientation = orientation \{DOWN}
     *      | if (orientation == getOrientation())
     *      |   orientation = orientation \ getOrientation()
     *      | if (orientation % 2 != getOrientation() % 2)
     *      |   orientation = orientation \ getOrientation()
     * @effect setEnergy(getEnergy() - getEnergyRequiredToReach(x,y))
     * @throws IllegalStateException
     *      This robot has insufficient energy to reach the given location.
     *      | getEnergy() < getEnergyRequired()
     */
    public void moveTo(long x, long y) throws IllegalStateException {
        if (getEnergy() < getEnergyRequiredToReach(x, y))
            throw new IllegalStateException();

        if (!(x == getX() && y == getY())) {
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

            setEnergy(getEnergy() - getEnergyRequiredToReach(x, y));
            setX(x);
            setY(y);
        }
    }

    /**
     * Move robots next to each other unless they have insufficient energy, then
     * make a best effort.
     * 
     * @param robot2
     *      Robot that this robot should end up next to.
     * @post This robot will be next to another robot or both robots will have
     *      made their best effort to minimize their manhattan distance
     *      without standing on top of each other.
     *      | manhattanDistance = getManhattanDistance(new.getX(), new.getY(),
     *      |                       (new robot2).getX(), (new robot2).getY())
     *      | consumedEnergy = this.getEnergyToReach(new.getX(), new.getY)
     *      |                   + robot2.getEnergyToReach((new robot2).getX(),
     *      |                                             (new robot2).getY())
     *      | for (robot1coord : this.getReachableInQuadrant())
     *      |   for (robot2coord : this.getReachableInQuadrant())
     *      |       manhattan = getManhattanDistance(robot1coord[0],
     *      |                                           robot1coord[1],
     *      |                                           robot2coord[0],
     *      |                                           robot2coord[1])
     *      |       energy = this.getEnergyToReach(robot1coord[0],
     *      |                                       robot1coord[1])
     *      |                   + robot2.getEnergyToReach(robot2coord[0],
     *      |                                               robot2coord[1])
     *      |       (manhattan == 0 || manhattan >= manhattanDistance)
     *      |       && (!(manhattan == manhattDistance)
     *      |       || (energy >= consumedEnergy))
     * @throws IllegalArgumentException
     *      The given robot is not a robot.
     *      | robot2 == null
     */
    public void moveNextTo(IRobot robot2) throws IllegalArgumentException {
        if (robot2 == null)
            throw new IllegalArgumentException();

        List<Long[][]> minPairs = new ArrayList<Long[][]>();
        long minManhattanDistance = Math.max(getManhattanDistance(getX(), getY(),
                robot2.getX(), robot2.getY()), 1);
        double minPairEnergy = Math.max(getEnergyRequiredToReach(getX(), getY())
                + robot2.getEnergyRequiredToReach(robot2.getX(), robot2.getY()),
                600);
        for (Long[] robot1Coord : getReachableInQuadrant(robot2.getX(),
                robot2.getY())) {
            for (Long[] robot2Coord : robot2.getReachableInQuadrant(getX(),
                    getY())) {
                Long[][] pair = { robot1Coord, robot2Coord };
                long manhattanDistance = getManhattanDistance(pair[0][0],
                        pair[0][1], pair[1][0], pair[1][1]);
                double pairEnergy = getEnergyRequiredToReach(pair[0][0],
                        pair[0][1])
                        + robot2.getEnergyRequiredToReach(pair[1][0],
                                pair[1][1]);
                if (manhattanDistance == minManhattanDistance
                        && pairEnergy == minPairEnergy)
                    minPairs.add(pair);

                else if (manhattanDistance < minManhattanDistance
                        && manhattanDistance > 0
                        || (manhattanDistance == minManhattanDistance
                                && pairEnergy < minPairEnergy)) {
                    minManhattanDistance = manhattanDistance;
                    minPairEnergy = pairEnergy;
                    minPairs = new ArrayList<Long[][]>();
                    minPairs.add(pair);
                }

            }
        }

        if (minPairs.size() > 0) {
            Long[][] firstPair = minPairs.get(0);
            double minExpendedEnergyDifference = Math
                    .abs(getEnergyRequiredToReach(firstPair[0][0],
                            firstPair[0][1])
                            - robot2.getEnergyRequiredToReach(firstPair[1][0],
                                    firstPair[1][1]));
            List<Long[][]> optimalPairs = new ArrayList<Long[][]>();

            for (Long[][] pair : minPairs) {
                double expendedEnergyDifference = Math
                        .abs(getEnergyRequiredToReach(firstPair[0][0],
                                firstPair[0][1])
                                - robot2.getEnergyRequiredToReach(
                                        firstPair[1][0], firstPair[1][1]));
                if (expendedEnergyDifference == minExpendedEnergyDifference)
                    optimalPairs.add(pair);
                else if (expendedEnergyDifference < minExpendedEnergyDifference) {
                    minExpendedEnergyDifference = expendedEnergyDifference;
                    optimalPairs = new ArrayList<Long[][]>();
                    optimalPairs.add(pair);
                }
            }

            Long[][] optimalPair = optimalPairs.get(0);

            moveTo(optimalPair[0][0], optimalPair[0][1]);
            robot2.moveTo(optimalPair[1][0], optimalPair[1][1]);
        }
    }

    /**
     * Get the minimal energy this robot requires to reach a given position.
     * 
     * @param x
     *      | The target position's x coordinate.
     * @param y
     *      | The target position's y coordinate.
     * @pre The x and y coordinates have to be valid.
     *      | isValidCoordinate(x) && isValidCoordinate(y)
     * @return The minimal energy this robot requires to reach the given
     *      position.
     *      | energyToMove = (Math.abs(x - getX()) + Math.abs(y - getY())) * 500
     *      | xTurnDistance = Math.abs(getOrientation() - xDirection)
     *      | yTurnDistance = Math.abs(getOrientation() - yDirection)
     *      | xDirection = LEFT 
     *      | yDirection = UP 
     *      | if(x > getX()) xDirection = RIGHT
     *      | if(y > getY()) yDirection = DOWN
     *      | if (x == getX())
     *      |   if (yTurnDistance == 0)
     *      |        energyToTurn = 0
     *      |   else if (yTurnDistance == 2)
     *      |        energyToTurn = 200
     *      |   else
     *      |        energyToTurn = 100
     *      | else if (y == getY())
     *      |   if (xTurnDistance == 0)
     *      |       energyToTurn = 0
     *      |   else if (xTurnDistance == 2)
     *      |       energyToTurn = 200
     *      |   else
     *      |       energyToTurn = 100
     *      | else
     *      |   if (xTurnDistance == 3)
     *      |       xTurnDistance = 1
     *      |   if (yTurnDistance == 3)
     *      |       yTurnDistance = 1
     *      |   if (Math.max(xTurnDistance, yTurnDistance) == 0)
     *      |       energyToTurn = 0
     *      |   else if (Math.max(xTurnDistance, yTurnDistance) == 2)
     *      |       energyToTurn = 200
     *      |   else
     *      |       energyToTurn = 100
     *      |
     *      | result == energyToMove + energyToTurn
     * @note No matter which path the robot takes it will allways have to move
     *      the same horizontal and vertical distance. Which can be calculated
     *      with the mathematical formula for perpendicular distance: |a-b|
     * @note A robot will allways have to turn either 0, 1 or 2 quarter turns to
     *      move closer to a target. The 'turn distance' between the target's
     *      direction and the robot's orientation needs to be calculated to
     *      determine the number of quarter turns.
     */
    public double getEnergyRequiredToReach(long x, long y) {

        double energyRequired =
            (Math.abs(x - getX()) + Math.abs(y - getY())) * 500;

        int xDirection = LEFT;
        int yDirection = UP;
        if (x > getX())
            xDirection = RIGHT;
        if (y > getY())
            yDirection = DOWN;

        // This array is used to avoid a meaningless amount of energy required
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

        if (x == getX() && y == getY())
            energyRequired = 0;
        else if (x == getX())
            energyRequired += yTurnEnergy;
        else if (y == getY())
            energyRequired += xTurnEnergy;
        else
            energyRequired += Math.max(xTurnEnergy, yTurnEnergy);

        return energyRequired;
    }

    /**
     * Get all positions within the rectangular area defined by this robot and
     * the given coordinates.
     * 
     * @param x2
     *      The given x coordinate.
     * @param y2
     *      The given y coordinate.
     * @return A list of all the positions this robot can reach within the
     *      rectangular area defined by this robot and the given coordinates.
     *      | for (position : result)
     *      |   getEnergyRequiredToReach(position[0], position[1]) <= getEnergy()
     *      |   && (position[0] in getX()..x2 && position[1] in getY()..y2)
     */
    public List<Long[]> getReachableInQuadrant(long x2, long y2) {
        List<Long[]> reachable = new ArrayList<Long[]>();

        int incrementX = -1;
        int incrementY = -1;
        if (getX() < x2)
            incrementX = 1;
        if (getY() < y2)
            incrementY = 1;

        // for (long x1 = getX(); x1 != x2; x1 += incrementX) {
        // for (long y1 = getY(); y1 != y2; y1 += incrementY) {
        // if (!(getEnergyRequiredToReach(x1, y1) < getEnergy()))
        // break;
        // if (getEnergyRequiredToReach(x1 + incrementX, y1) > getEnergy()
        // || getEnergyRequiredToReach(x1, y1 + incrementY) > getEnergy()) {
        // Long[] coord = { x1, y1 };
        // boundary.add(coord);
        // }
        // }
        // }
        //
        // return boundary;
        if (getX() == x2 && getY() == y2 && getEnergy() >= 500) {
            long x = getX();
            long y = getY();
            
            if (isValidCoordinate(x + 1) && isValidCoordinate(y)) {
                Long[] onTop = {x + 1, y};
                reachable.add(onTop);
            }
            if (isValidCoordinate(x - 1) && isValidCoordinate(y)) {
                Long[] onTop = {x - 1, y};
                reachable.add(onTop);
            }
            if (isValidCoordinate(x) && isValidCoordinate(y + 1)) {
                Long[] onTop = {x, y + 1};
                reachable.add(onTop);
            }
            if (isValidCoordinate(x) && isValidCoordinate(y - 1)) {
                Long[] onTop = {x, y - 1};
                reachable.add(onTop);
            }
            
        }
            
        for (long x1 = getX(); x1 != x2 + incrementX; x1 += incrementX) {
            for (long y1 = getY(); y1 != y2 + incrementY; y1 += incrementY) {
                if (getEnergyRequiredToReach(x1, y1) < getEnergy()) {
                    Long[] coord = {x1, y1};
                    reachable.add(coord);
                }
            }
        }

        return reachable;

    }

    /**
     * Set the x coordinate of this robot.
     * 
     * @param x
     *      The new x coordinate.
     * @post The x coordinate of this robot will equal x.
     *      | new.getX() = x
     * @throws IllegalArgumentException
     *      The given x coordinate is out of range.
     *      | ! isValidCoordinate(x)
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
     *      The new y coordinate.
     * @post The y coordinate of this robot will equal y.
     *      | new.getY() = y
     * @throws IllegalArgumentException
     *      The given y coordinate is out of range.
     *      | ! isValidCoordinate(y)
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
     *      The new orientation.
     * @post This robots new orientation will be the given orientation if the
     *      orientation is valid, the old orientation otherwise, unless
     *      the old orientation is not valid then it will be UP.
     *      | if (isValidOrientation(orientation)
     *      |   new.orientation = orientation;
     *      | else if (isValidOrietation(getOrientation()))
     *      |   new.orientation = getOrientation()
     *      | else
     *      |   new.orientation = UP
     */
    public void setOrientation(int orientation) {
        if (isValidOrientation(orientation))
            this.orientation = orientation;
        else if (!isValidOrientation(getOrientation()))
            this.orientation = UP;
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
     *      The new amount of energy.
     * @post This robots energy will equal the given energy. | new.getEnergy() =
     *      energy
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
     *      | result = getEnergy()/getMaxEnergy()
     */
    public double getEnergyRatio() {
        return getEnergy() / getMaxEnergy();
    }

    /**
     * Get the maximum amount of energy.
     */
    @Basic
    @Immutable
    public final double getMaxEnergy() {
        return MAXENERGY;
    }

    /**
     * Get the maximum board dimension.
     */
    @Basic
    @Immutable
    public static final long getMaxBoardDimension() {
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
     *      X coordinate of the first position.
     * @param y1
     *      Y coordinate of the first position.
     * @param x2
     *      X coordinate of the second position.
     * @param y2
     *      Y coordinate of the second position.
     * @return The manhattan distance between the two sets of coordinates.
     *      | Math.abs(x1 - x2) + Math.abs(y1 - y2)
     * @throws IllegalArgumentException
     *      One or more of the given coordinates is out of range.
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
     *      Coordinate to check.
     */
    @Basic
    private boolean isValidCoordinate(long c) {
        return c >= 0 && c < getMaxBoardDimension();
    }

    /**
     * Check the validity of an orientation.
     * 
     * @param orientation
     *      Orientation to check
     */
    @Basic
    private boolean isValidOrientation(int orientation) {
        if (0 <= orientation && orientation <= 3)
            return true;
        return false;
    }

    /**
     * Check the validity of an amount of energy.
     * 
     * @param energy
     *      Amount of energy to check.
     */
    @Basic
    @Immutable
    private boolean isValidEnergy(double energy) {
        if (0 <= energy && energy <= getMaxEnergy())
            return true;
        return false;
    }

    private final double MAXENERGY = 20000;
    private static final long MAXBOARDDIMENSION = Long.MAX_VALUE;
    private long x;
    private long y;
    private int orientation;
    private double energy;
}