package roborally.model;

import roborally.IFacade;
import roborally.IRobot;

public class Facade implements IFacade {
	/**
	 * Create a new Robot at coordinate (<code>x</code>, <code>y</code>) looking
	 * at <code>orientation</code> with <code>energy</code> watt-second.
	 * 
	 * This method must return <code>null</code> if the given parameters are
	 * invalid (e.g. negative energy).
	 * 
	 * <p>
	 * 0, 1, 2, 3 respectively represent up, right, down and left.
	 * </p>
	 */
	public IRobot createRobot(long x, long y, int orientation, double energy) {
		return new Robot(x, y, orientation, energy);
	}

	/**
	 * Return the x-coordinate of <code>robot</code>.
	 */
	public long getX(IRobot robot) {
		return robot.getX();
	}

	/**
	 * Return the y-coordinate of <code>robot</code>.
	 */
	public long getY(IRobot robot) {
		return robot.getY();
	}

	/**
	 * Return the orientation (either 0, 1, 2 or 3) of <code>robot</code>.
	 * 
	 * <p>
	 * 0, 1, 2, 3 respectively represent up, right, down and left.
	 * </p>
	 */
	public int getOrientation(IRobot robot) {
		return robot.getOrientation();
	}

    /**
     * Move <code>robot</code> one step in its current direction if the robot
     * has sufficient energy. Do not modify the state of the robot if it has
     * insufficient energy.
     */
    public void move(IRobot robot) {
        try {
            robot.move();
        } catch (IllegalStateException exc) {
            System.err.println("IllegalStateException:" +
            		" This robot has insufficient energy.");
        }
    }

	/**
	 * Turn <code>robot</code> 90 degrees in clockwise direction if the robot
	 * has sufficient energy. Do not modify the state of the robot if it has
	 * insufficient energy.
	 */
	public void turnClockwise(IRobot robot) {
		robot.turnClockwise();
	}

	/**
	 * Return the current energy in watt-second of <code>robot</code>.
	 */
	public double getEnergy(IRobot robot) {
		return robot.getEnergy();
	}

	/**
	 * Add <code>energyAmount</code> (expressed in watt-second) to
	 * <code>robot</code>. If <code>energyAmount</code> is negative or if adding
	 * <code>energyAmount</code> would cause the robot to exceed its maximum
	 * energy level, do not modify the state of the robot.
	 */
	public void recharge(IRobot robot, double energyAmount) {
		robot.recharge(energyAmount);
	}

	/**
	 * Return whether your implementations of
	 * <code>getEnergyRequiredToReach</code> and <code>moveNextTo</code> take
	 * into account the fact that turning consumes energy (required to score
	 * 16+). The return value of this method determines the expected return
	 * value of <code>getEnergyRequiredToReach</code> and the expected effect of
	 * <code>moveNextTo</code> in the test suite.
	 * 
	 * This method must return either 0 or 1.
	 */
	public int isGetEnergyRequiredToReachAndMoveNextTo16Plus() {
		return 1;
	}

	/**
	 * Return the minimum amount of energy required by <code>robot</code> to
	 * reach coordinate (<code>x</code>, <code>y</code>).
	 * 
	 * <ul>
	 * <li>If <code>isGetEnergyRequiredToReachAndMoveNextTo16Plus</code> returns
	 * 0, the result of this method should not include the energy required for
	 * turning. That is, the result of this method must only be optimal in the
	 * number of moves (not in the total energy required).</li>
	 * <li>If <code>isGetEnergyRequiredToReachAndMoveNextTo16Plus</code> returns
	 * 1, the result of this method must include the energy required for
	 * turning. That is, the result must both be optimal in the energy required
	 * by both turns and moves.</li>
	 * </ul>
	 */
	public double getEnergyRequiredToReach(IRobot robot, long x, long y) {
		return robot.getEnergyRequiredToReach(x, y);
	}

	/**
	 * Move <code>robot</code> as close as possible to <code>robot2</code>
	 * consuming as little energy as possible.
	 * 
	 * <ul>
	 * <li>If <code>isGetEnergyRequiredToReachAndMoveNextTo16Plus</code> returns
	 * 0, this method should not take into account the fact that turning
	 * requires energy. That is, the total number of moves performed by
	 * <code>robot</code> and <code>robot2</code> must be minimized.</li>
	 * <li>If <code>isGetEnergyRequiredToReachAndMoveNextTo16Plus</code> returns
	 * 1, this method must take into account the fact that turning requires
	 * energy. That is, the total amount of energy consumed by
	 * <code>robot</code> and <code>robot2</code> must be minimal.</li>
	 * </ul>
	 */
	public void moveNextTo(IRobot robot, IRobot robot2) {
		robot.moveNextTo(robot2);
	}
}
