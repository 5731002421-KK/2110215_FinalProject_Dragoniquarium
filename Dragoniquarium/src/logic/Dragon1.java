package logic;

import java.awt.Graphics2D;

import javax.swing.Box.Filler;

import render.DrawingUtility;
import render.GameAnimation;
import render.Resource;

public class Dragon1 extends DamageableObject {
	
	public boolean layingEgg = false;
	private GameAnimation walkingAnimation;
	private GameAnimation layingAnimation;
	
	public Dragon1(int x, int y, int z) {
		super(x, y, 25, z, 2, 1, 0);
		stateTime = 200;
		walkingAnimation = DrawingUtility.createDragon1Animation();
		layingAnimation = DrawingUtility.createDragon1AnimationLayingEgg();
	}
	
	@Override
	public void move() {
		if(destroyed) return;
		
		
		if(state == 1) {
			walkingAnimation.updateAnimation();
		} else if(state == 3) {
			layingAnimation.updateAnimation();
		}
		
//		if(GameLogic.enemyOnScreen) {
//			performStateAction();
//			return ;
//		}
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
				stateTime = 60;
				layingAnimation.setCurrentFrame(0);
			}
		} else if(state == 3) {
			stateTime--;
			if(stateTime == 15) {
				layingEgg = true;
			}
			
			if(stateTime == 0) {
//				layEgg();
				
				state = 1;
				stateTime = 500;
			}
			return ;
		}
		
		calculateXaxis();
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		
		// TODO
//		g2d.drawImage(Resource.egg1Sprite, null, (int)x-radius, (int)y-radius);
		if(state == 1) {
			walkingAnimation.draw(g2d, (int)x-radius, (int)y-radius, isLeft);
		} else if(state == 3) {
			int temp = (int)x-radius;
			if(isLeft) {
				temp += 7;				
			} else {
				temp -= 7;
			}
			layingAnimation.draw(g2d, temp, (int)y-radius-8, isLeft);
		}
	}

	@Override
	protected void performStateAction() {
		// TODO Auto-generated method stub
		
	}
	
	

}
