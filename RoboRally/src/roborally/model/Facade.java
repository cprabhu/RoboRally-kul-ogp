package roborally.model;

import roborally.IFacade;

import java.util.Set;

/**
 * Implement this interface to connect your code to the user interface.
 * 
 * <ul>
 * <li>Connect your classes to the user interface by creating a class named <code>Facade</code> that implements <code>IFacade</code>. The header
 *     of the class <code>Facade</code> should look as follows:
 *     <p><code>class Facade implements IFacade&ltBoardImpl, RobotImpl, WallImpl, BatteryImpl&gt { ... }</code></p>
 *     The code snippet shown above assumes that your classes representing boards, robots, walls and batteries are respectively named
 *     <code>BoardImpl</code>, <code>RobotImpl</code>, <code>WallImpl</code> and <code>BatteryImpl</code>. Consult the
 *     <a href="http://docs.oracle.com/javase/tutorial/java/IandI/createinterface.html">Java tutorial</a> for more information on interfaces.</li>
 * <li>Modify the code between <code>&ltbegin&gt</code> and <code>&ltend&gt</code> in RoboRally.java: instantiate the generic arguments with
 *     your own classes and replace <code>new roborally.model.Facade()</code> with <code>new yourpackage.Facade()</code>.
 * <li>You may assume that only non-null objects returned by <code>createBoard</code>, <code>createRobot</code>, <code>createWall</code> and <code>createBattery</code>
 *     are passed to <code>putRobot</code>, <code>getBatteryX</code>, <code>getWallY</code>, <code>move</code>, etc.</li>
 * <li>The methods in this interface should not throw exceptions (unless specified otherwise in the documentation of a method). Prevent precondition violations for nominal methods (by checking before calling a method that its precondition holds)
 *   and catch exceptions for defensive methods. If a problem occurs (e.g. insufficient energy to move, trying to use a battery not held by the robot, ...), do not modify the program state and print an error message on standard error (<code>System.err</code>).</li>
 * <li>The rules described above and the documentation described below for each method apply only to the class implementing IFacade. Your classes for representing boards, robots, walls and batteries should follow the rules described in the assignment.</li>
 * <li>Do not modify the signatures of the methods defined in this interface. You can however add additional methods, as long as these additional methods do not overload the existing ones. Each additional method should of course
 *     be implemented in your class <code>Facade</code>.</li>
 * </ul> 
 */
public class Facade implements IFacade<Board, Robot, Wall, Battery> {

    /**
     * Create a new board with the given <code>width</code> and <code>height</code>. 
     * 
     * This method must return <code>null</code> if the given <code>width</code> and <code>height</code> are invalid. 
     */
    // TODO: Implementatie
    public Board createBoard(long width, long height) {
        return null;
    }

    /**
     * Merge <code>board1</code> and <code>board2</code>. 
     */
    // TODO: Implementatie
    public void merge(Board board1, Board board2) {

    }

    /**
     * Create a new battery with initial energy equal to <code>initialEnergy</code> and weight equal to <code>weight</code>. 
     * 
     * This method must return <code>null</code> if the given parameters are invalid (e.g. negative weight). 
     */
    // TODO: Implementatie
    public Battery createBattery(double initialEnergy, int weight) {
        return null;
    }

    /**
     * Put <code>battery</code> at position (<code>x</code>, <code>y</code>) on <code>board</code> (if possible).
     */
    // TODO: Implementatie
    public void putBattery(Board board, long x, long y, Battery battery) {

    }

    /**
     * Return the x-coordinate of <code>battery</code>.
     * 
     * This method must throw <code>IllegalStateException</code> if <code>battery</code> is not placed on a board.
     */
    // TODO: Implementatie
    public long getBatteryX(Battery battery) throws IllegalStateException {
        return 0;
    }

    /**
     * Return the y-coordinate of <code>battery</code>.
     * 
     * This method must throw <code>IllegalStateException</code> if <code>battery</code> is not placed on a board.
     */
    // TODO: Implementatie
    public long getBatteryY(Battery battery) throws IllegalStateException {
        return 0;
    }

    /** 
     * Create a new Robot looking at <code>orientation</code> with <code>energy</code> watt-second.
     * 
     * This method must return <code>null</code> if the given parameters are invalid (e.g. negative energy). 
     *  
     * <p>0, 1, 2, 3 respectively represent up, right, down and left.</p>
     */
    // TODO: Implementatie
    public Robot createRobot(int orientation, double initialEnergy) {
        return null;
    }

    /**
     * Put <code>robot</code> at position (<code>x</code>, <code>y</code>) on <code>board</code> (if possible).
     */
    // TODO: Implementatie
    public void putRobot(Board board, long x, long y, Robot robot) {

    }

    /**
     * Return the x-coordinate of <code>robot</code>.
     * 
     * This method must throw <code>IllegalStateException</code> if <code>robot</code> is not placed on a board.
     */
    // TODO: Implementatie
    public long getRobotX(Robot robot) throws IllegalStateException {
        return 0;
    }

    /**
     * Return the y-coordinate of <code>robot</code>.
     * 
     * This method must throw <code>IllegalStateException</code> if <code>robot</code> is not placed on a board.
     */
    // TODO: Implementatie
    public long getRobotY(Robot robot) throws IllegalStateException {
        return 0;
    }

    /**
     * Return the orientation (either 0, 1, 2 or 3) of <code>robot</code>. 
     * 
     * <p>0, 1, 2, 3 respectively represent up, right, down and left.</p>
     */
    // TODO: Implementatie
    public int getOrientation(Robot robot) {
        return 0;
    }

    /**
     * Return the current energy in watt-second of <code>robot</code>.
     */
    // TODO: Implementatie
    public double getEnergy(Robot robot) {
        return 0;
    }

    /**
     * Move <code>robot</code> one step in its current direction if the robot has sufficient energy. Do not modify the state of the robot
     * if it has insufficient energy.
     */
    // TODO: Implementatie
    public void move(Robot robot) {

    }

    /**
     * Turn <code>robot</code> 90 degrees in clockwise direction if the robot has sufficient energy. Do not modify the state of the robot
     * if it has insufficient energy.
     */
    // TODO: Implementatie
    public void turn(Robot robot) {

    }

    /**
     * Return the set of batteries that <code>robot</code> is carrying.
     */
    // TODO: Implementatie
    public Set<Battery> getPossessions(Robot robot) {
        return null;
    }

    /**
     * Make <code>robot</code> pick up <code>battery</code> (if possible).
     */
    // TODO: Implementatie
    public void pickUp(Robot robot, Battery battery) {

    }

    /**
     * Make <code>robot</code> use <code>battery</code> (if possible).
     */
    // TODO: Implementatie
    public void use(Robot robot, Battery battery) {

    }

    /**
     * Make <code>robot</code> drop <code>battery</code> (if possible).
     */
    // TODO: Implementatie
    public void drop(Robot robot, Battery battery) {

    }

    /**
     * Return whether your implementation of <code>isMinimalCostToReach</code> takes into account other robots, walls and turning (required to score 17+). The return
     * value of this method determines the expected return value of <code>isMinimalCostToReach</code> in the test suite.
     * 
     * This method must return either 0 or 1.
     */
    // TODO: Implementatie
    public int isMinimalCostToReach17Plus() {
        return 0;
    }

    /**
     * Return the minimal amount of energy required for <code>robot</code> to reach (<code>x</code>, </code>y</code>) taking into account the robot's current load and energy level. Do not take into account
     * shooting and picking up/using/dropping batteries. 
     * <p>
     * The expected return value of this method depends on <code>isMinimalCostToReach17Plus</code>:
     * <ul>
     * <li>If <code>isMinimalCostToReach17Plus</code> returns <code>0</code>, then <code>getMinimalCostToReach</code> will only be called if there are no obstacles in the rectangle
     * covering <code>robot</code> and the given position. Moreover, the result of this method should not include the energy required for turning.</li>
     * <li>If <code>isMinimalCostToReach17Plus</code> returns <code>1</code>, then <code>getMinimalCostToReach</code> must take into account obstacles (i.e. walls, other robots) and the 
     * fact that turning consumes energy. This method must return <code>-1</code> if the given position is not reachable because of obstacles.</li>
     * </ul>
     * </p>
     * In any case, this method must return <code>-1</code> if <code>robot</code> is not placed on a board. Moreover, this method must return <code>-2</code> if <code>robot</code> has
     * insufficient energy to reach (<code>x</code>, <code>y</code>).
     */
    // TODO: Implementatie
    public double getMinimalCostToReach(Robot robot, long x, long y) {
        return 0;
    }

    /**
     * Return whether your implementation of <code>moveNextTo</code> takes into account other robots, walls and the fact that turning consumes energy (required to score 18+). The return
     * value of this method determines the expected effect of <code>moveNextTo</code> in the test suite.
     * 
     * This method must return either 0 or 1.
     */
    // TODO: Implementatie
    public int isMoveNextTo18Plus() {
        return 0;
    }

    /**
     * Move <code>robot</code> as close as possible (expressed as the manhattan distance) to <code>other</code> given their current energy and load. If multiple optimal (in distance) solutions
     * exist, select the solution that requires the least amount of total energy. Both robots can move and turn to end up closer to each other. Do not take into account shooting and picking up/using/dropping
     * batteries.  
     * <p>
     * The expected return value of this method depends on <code>isMoveNextTo18Plus</code>:
     * <ul>
     * <li>If <code>isMoveNextTo18Plus</code> returns <code>0</code>, then <code>moveNextTo</code> will only be called if there are no obstacles in the rectangle
     * covering <code>robot</code> and <code>other</code>. Moreover, your implementation must be optimal only in the number of moves (i.e. ignore the fact that turning consumes energy).</li>
     * <li>If <code>isMoveNextTo18Plus</code> returns <code>1</code>, then <code>moveNextTo</code> must take into account obstacles (i.e. walls, other robots) and the 
     * fact that turning consumes energy.</li>
     * </ul>
     * </p>
     * Do not change the state if <code>robot</code> and <code>other</code> are not located on the same board.
     */
    // TODO: Implementatie
    public void moveNextTo(Robot robot, Robot other) {
        
    }

    /**
     * Make <code>robot</code> shoot in the orientation it is currently facing (if possible).
     * 
     * Students working on their own are allowed to throw <code>UnsupportedOperationException</code>.
     */
    // TODO: Implementatie
    public void shoot(Robot robot) throws UnsupportedOperationException {

    }

    /**
     * Create a new wall.
     * 
     * Students working on their own are allowed to throw <code>UnsupportedOperationException</code>.
     */
    // TODO: Implementatie
    public Wall createWall() throws UnsupportedOperationException {
        return null;
    }

    /**
     * Put <code>robot</code> at position (<code>x</code>, <code>y</code>) on <code>board</code> (if possible).
     * 
     * Students working on their own are allowed to throw <code>UnsupportedOperationException</code>.
     */
    // TODO: Implementatie
    public void putWall(Board board, long x, long y, Wall wall)
            throws UnsupportedOperationException {

    }

    /**
     * Return the x-coordinate of <code>wall</code>.
     * 
     * This method must throw <code>IllegalStateException</code> if <code>wall</code> is not placed on a board.
     * 
     * Students working on their own are allowed to throw <code>UnsupportedOperationException</code>.
     */
    // TODO: Implementatie
    public long getWallX(Wall wall) throws IllegalStateException,
            UnsupportedOperationException {
        return 0;
    }

    /**
     * Return the y-coordinate of <code>wall</code>.
     * 
     * This method must throw <code>IllegalStateException</code> if <code>wall</code> is not placed on a board.
     * 
     * Students working on their own are allowed to throw <code>UnsupportedOperationException</code>.
     */
    // TODO: Implementatie
    public long getWallY(Wall wall) throws IllegalStateException,
            UnsupportedOperationException {
        return 0;
    }

    /**
     * Return a set containing all robots on <code>board</code>.
     */
    // TODO: Implementatie
    public Set<Robot> getRobots(Board board) {
        return null;
    }

    /**
     * Return a set containing all batteries on <code>board</code>.
     */
    // TODO: Implementatie
    public Set<Battery> getBatteries(Board board) {
        return null;
    }

    /**
     * Return a set containing all walls on <code>board</code>.
     * 
     * Students working on their own are allowed to throw <code>UnsupportedOperationException</code>.
     */
    // TODO: Implementatie
    public Set<Wall> getWalls(Board board) throws UnsupportedOperationException {
        return null;
    }
}
