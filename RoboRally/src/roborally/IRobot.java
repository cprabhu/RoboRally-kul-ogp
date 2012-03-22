package roborally;

import java.util.*;

public interface IRobot {

	public abstract void move();
	
	public abstract void turnClockwise();
	
	public abstract void recharge(double energyAmount);
	
	public abstract void moveTo(long x, long y);
	
	public abstract void moveNextTo(IRobot robot2);
	
	public abstract double getEnergyRequiredToReach(long x, long y);
	
	public abstract void setX(long x);
	
	public abstract long getX();
	
	public abstract void setY(long y);
	
	public abstract long getY();
	
	public abstract void setOrientation(int orientation);
	
	public abstract int getOrientation();
	
	public abstract void setEnergy(double energy);
	
	public abstract double getEnergy();
	
	public abstract double getEnergyRatio();
	
	public abstract List<Long[]> getReachableInQuadrant(long x2, long y2);
}