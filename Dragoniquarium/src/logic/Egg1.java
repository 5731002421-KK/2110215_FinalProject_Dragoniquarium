package logic;

import java.awt.Color;
import java.awt.Graphics2D;

import render.DrawingUtility;

public class Egg1 extends CollectibleObject {
	
	public Egg1(int x, int y) {
		super(x, y, 21, Integer.MAX_VALUE-2, 30);
	}

	@Override
	public void draw(Graphics2D g2d) {
//		g2d.drawImage(Resource.egg1Sprite, null, (int)x-radius, (int)y-radius);
		g2d.setColor(Color.YELLOW);
		g2d.fillOval((int)x-radius, (int)y-radius, 2*radius, 2*radius);
		g2d.drawImage(DrawingUtility.egg, null, (int)x-radius+2, (int)y-radius-4);
		// TODO
	}

	@Override
	public void collect(PlayerStatus player) {
		player.addEgg(reward);
	}

	@Override
	public void reachDestination() {
		if(movingUp) {
			movingUp = false;
			
			generateMovingDestination(x, y);
		} else {
			destroyed = true;
		}	
	}
	
	
	
}
