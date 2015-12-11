package logic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import render.DrawingUtility;
import render.GameAnimation;
import render.RenderableHolder;

public class Enemy1 extends EnemyObject {

	private int attackTickCount = 0;
	private int attackDelay = 100;
	
	private GameAnimation flyingAnimation;
	private GameAnimation attackingAnimation;
	
	private boolean attacking = false;
	
	public Enemy1(int x, int y, int radius, int z) {
		super(x, y, radius, z, 1, 20, 0, 100);
		stateTime = 30;
		flyingAnimation = DrawingUtility.createDragon1Animation();
		attackingAnimation = DrawingUtility.createDragon1AnimationLayingEgg();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void move() {
		if(destroyed) return;
		if(state == 1) {
			flyingAnimation.updateAnimation();
		} else if(state == 3) {
			attackingAnimation.updateAnimation();
		}
		
		if(hasDestination) {
			moveIn();
			return ;
		}
		
		attacking = false;
		
		if(state == 1) {
			stateTime--;
			if(stateTime == 0) {
				state = 3;
				stateTime = 100;
				attackingAnimation.setCurrentFrame(0);
			}
		} else if(state == 3) {
			stateTime--;
			if(stateTime == 40) {
				attacking = true;
			}
			
			if(stateTime == 0) {
				state = 1;
				stateTime = 100;
			}
		}
		
//		if(state == 1) {
			calculateXaxis();
//		}
		calculateYaxis();
	}
	
	@Override
	void attack(List <AttackObject> onScreenAttack, int zCounter) {
//		if(attackTickCount < attackDelay) {
////			attackTickCount++;
//			return ;
//		}
		if(!attacking) {
			return ;
		}
		
		attackTickCount = 0;
		for(int i=0;i<3;i++) {
			AttackObject atk = new AttackObject(x, y, 10, zCounter, 1,
					RandomUtility.random(leftBorder, rightBorder), bottomBorder, 3, 1);
			onScreenAttack.add(atk);
			RenderableHolder.getInstance().add(atk);
		}
	}

	@Override
	public void draw(Graphics2D g2d) {
//		g2d.setColor(Color.BLUE);
//		g2d.fillOval((int)(x-radius), (int)(y-radius), 2*radius, 2*radius);
		if(state == 1) {
			flyingAnimation.draw(g2d, (int)x-radius, (int)y-radius, isLeft);
		} else if(state == 3) {
			int temp = (int)x-radius;
			if(isLeft) {
				temp += 7;				
			} else {
				temp -= 7;
			}
			attackingAnimation.draw(g2d, temp, (int)y-radius-8, isLeft);
		}
	}


}
