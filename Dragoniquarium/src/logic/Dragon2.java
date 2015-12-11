package logic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import render.DrawingUtility;
import render.GameAnimation;
import render.RenderableHolder;

public class Dragon2 extends DamageableObject {
	
	private GameAnimation flyingAnimation;
	private GameAnimation attackingAnimation;
	
	private int attackTickCount = 0;
	private int attackDelay = 100;
	
	private boolean attacking = false;
	
	public Dragon2(int x, int y, int z) {
		super(x, y, 25, z, 1, 1, 0);
		stateTime = 200;
		flyingAnimation = DrawingUtility.createDragon1Animation();
		attackingAnimation = DrawingUtility.createDragon1AnimationLayingEgg();
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
				if(GameLogic.enemyOnScreen) {
					state = 3;
					stateTime = 30;
					attackingAnimation.setCurrentFrame(0);
				} else {
					stateTime = 100;
				}
				
			}
		} else if(state == 3) {
			stateTime--;
			if(stateTime == 15) {
				attacking = true;
			}
			
			if(stateTime == 0) {
				if(GameLogic.enemyOnScreen) {
					stateTime = 30;
				} else {
					state = 1;
					stateTime = 100;
					isLeft = RandomUtility.random(0, 1) == 0 ? true : false;
					tickCountX = tickNeedX-1;
				}
				
			}
//			return ;
		}
		
		if(state == 1) {
			calculateXaxis();
		}
		
		calculateYaxis();
	}
	
	public void attack(List <AttackObject> onScreenAttack, TargetObject targetEnemy, int zCounter ) {
		if(targetEnemy == null) {
			return ;
		}
		
		if(targetEnemy.x > x) {
			isLeft = false;
		} else {
			isLeft = true;
		}
		
		if(!attacking) {
			return ;
		}
		
//		if(attackTickCount < attackDelay) {
//			attackTickCount++;
//			return ;
//		}
//		
//		attackTickCount = 0;
		
		
		AttackObject atk = new AttackObject(x, y, 10, zCounter, 1, targetEnemy.x, targetEnemy.y, 7, 2);
		onScreenAttack.add(atk);
		RenderableHolder.getInstance().add(atk);

	}
	
	@Override
	public void draw(Graphics2D g2d) {
//		g2d.setColor(Color.GREEN);
//		g2d.drawOval((int)x-radius, (int)y-radius, 2*radius, 2*radius);	
		
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

	@Override
	protected void performStateAction() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
