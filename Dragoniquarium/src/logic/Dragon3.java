package logic;

import java.awt.Color;
import java.awt.Graphics2D;

import render.DrawingUtility;
import render.GameAnimation;

public class Dragon3 extends DamageableObject {
	
//	public boolean layingEgg = false;
	private GameAnimation walkingAnimation;
	private GameAnimation guardingAnimation;
	
	public Dragon3(int x, int y, int z) {
		super(x, y, 55, z, 2, 1, 0);
		stateTime = 200;
		walkingAnimation = DrawingUtility.createDragon1Animation();
		guardingAnimation = DrawingUtility.createDragon1AnimationLayingEgg();
	}
	
	
	@Override
	public void move() {
		if(destroyed) return;
		
		
		walkingAnimation.updateAnimation();
		if(state == 3) {
			guardingAnimation.updateAnimation();
		}
		
		if(hasDestination) {
			if(contains(xDestination,yDestination)) {
				hasDestination = false;
				reachDestination();
				return ;
			}
			
			x += (xDestination - x) * speed;
			y += (yDestination - y) * speed;
			return ;
		}
		if(state == 1) {
			stateTime--;
			if(stateTime == 0) {
				state = 3;
				stateTime = RandomUtility.random(150, 400);
				defense = 5;
			}
		} else if(state == 3) {
			stateTime--;
			if(stateTime == 0) {
				state = 1;
				defense = 0;
				stateTime = RandomUtility.random(100, 150);
			}
		}
		
		calculateXaxis();
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		
		if(state == 3) {
			g2d.setColor(Color.BLUE);
			g2d.fillOval((int)x-radius, (int)y-radius, radius*2, radius*2);
//			guardingAnimation.draw(g2d, (int)x-radius, (int)y-radius-8, isLeft);
		}
		walkingAnimation.draw(g2d, (int)x-radius, (int)y-radius, isLeft);
	}
}
