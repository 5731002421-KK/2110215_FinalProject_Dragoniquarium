package logic;

import java.awt.Color;
import java.awt.Graphics2D;

public class AttackObject extends TargetObject {

	
	private int attack;
	private int life = 3;
	private double speed;
	
	public int attackType;
	/*
	 * type 1 : monster to dragon
	 * type 2 : dragon 2 to monster
	 * type 3 : dragon 4 to monster
	 * type 4 : dragon 5 to monster
	 */
	
	public int topBorder = 20;
	public int bottomBorder = 650;
	public int rightBorder = 1240;
	public int leftBorder = 200;
	
	public AttackObject(double x, double y, int radius, int z, int attack, 
			double xDes, double yDes, double speed, int attackType) {
		super(x, y, radius, z);
		this.attack = attack;
		this.speed = speed;
		this.attackType = attackType;
		this.xDestination = xDes;
		this.yDestination = yDes;
		generateSpeed();
	}
	
	public void hitByPlayer() {
		life--;
		if(life <= 0) {
			destroyed = true;
		}
	}
	
	public int getAttack() {
		return attack;
	}

	private void generateSpeed() {
		xSpeed = (xDestination - x)/ Math.hypot(xDestination - x, yDestination - y) * speed;
		ySpeed = (yDestination - y)/ Math.hypot(xDestination - x, yDestination - y) * speed;
	}
	
	@Override
	public void move() {
		x += xSpeed;
		y += ySpeed;
		if(x > rightBorder || x < leftBorder || y < topBorder || y > bottomBorder) {
			destroyed = true;
		}
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		if(attackType >= 2) {
			g2d.setColor(Color.BLACK);
		} else {
			g2d.setColor(Color.ORANGE);
		}
		g2d.fillOval((int)(x-radius), (int)(y-radius), 2*radius, 2*radius);
		
	}
	
	@Override
	public void generateMovingDestination(double curX, double curY) {}
	
	@Override
	public void reachDestination() {}
	
	

}
