package logic;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;

import render.IRenderable;
//import lib.ConfigurableOption;
import logic.RandomUtility;;

public abstract class TargetObject implements IRenderable{

	protected double x,y;
	protected int z;
	protected int radius;
	protected boolean destroyed = false;
	protected double xSpeed, ySpeed = 0;
//	protected int movingType;
	// 1 is normal, 2 is vertical only, 3 is horizontal only
	
	protected double xDestination;
	protected double yDestination;
	
	protected static final AlphaComposite transcluentWhite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
	protected static final AlphaComposite opaque = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
//	protected int xDistance;
//	protected int yDistance;
	
	protected boolean isPointerOver = false;

	protected int currentAction;
	
	public TargetObject(double x, double y, int radius, int z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.radius = radius;
//		this.xSpeed = xSpeed;
//		this.ySpeed = ySpeed;
//		this.movingType = movingType;
		
		//TODO - generate first destination - maybe in subclass
		//TODO - how to fade in ?
		
//		this.x = movingParameter[0];
//		this.y = movingParameter[1];
		
	}
	
	public abstract void generateMovingDestination(double curX, double curY);
	public abstract void move();
	public abstract void reachDestination();
	
	public boolean contains(double x, double y){
		return Math.hypot(x-this.x, y-this.y) <= radius+6;
	}
	
	public boolean contains(double x, double y, double radius){
		return Math.hypot(x-this.x, y-this.y) <= this.radius+radius+6;
	}
	
	public void setPointerOver(boolean isPointerOver) {
		this.isPointerOver = isPointerOver;
	}
	

	@Override
	public int getZ() {
		return this.z;
	}

	@Override
	public boolean isVisible() {
		return !destroyed;
	}
	
	public boolean isDestroyed() {
		return destroyed;
	}
		
}
