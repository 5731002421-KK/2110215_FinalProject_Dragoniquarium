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
		walkingAnimation = DrawingUtility.createDragon3Animation();
		guardingAnimation = DrawingUtility.createDragon3AnimationDef();
	}
	
	
	@Override
	public void move() {
		if(destroyed) return;
		
		
		if(state == 1) {
			walkingAnimation.updateAnimation();
		} else if(state == 3) {
			if(guardingAnimation.getCurrentFrame()!= guardingAnimation.getFrameCount()-1)
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
				radius = 95;
				stateTime = RandomUtility.random(150, 400) + 1000;
				defense = 5;
				guardingAnimation.setCurrentFrame(0);
			}
			calculateXaxis();
		} else if(state == 3) {
			stateTime--;
			if(stateTime == 0) {
				radius = 55;
				state = 1;
				defense = 0;
				stateTime = RandomUtility.random(100, 150);
			}
		}
		
		
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		
//		if(state == 3) {
//			g2d.setColor(Color.BLUE);
//			g2d.fillOval((int)x-radius, (int)y-radius, radius*2, radius*2);
//			guardingAnimation.draw(g2d, (int)x-radius, (int)y-radius-8, isLeft);
//		}
			
		int tempX = (int)x-radius;
		int tempY = (int)y-radius;
		
		if(state == 1) {
			walkingAnimation.draw(g2d, tempX, tempY, isLeft);
		} else if(state == 3) {
			guardingAnimation.draw(g2d, tempX-75, tempY+30, isLeft);
		}
//		walkingAnimation.draw(g2d, (int)x-radius, (int)y-radius, isLeft);
	}
}
