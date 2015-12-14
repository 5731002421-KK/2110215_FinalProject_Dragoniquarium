package logic;

import java.awt.Graphics2D;
import java.util.List;

import render.DrawingUtility;
import render.GameAnimation;
import render.RenderableHolder;

public class Dragon4 extends DamageableObject{

	private GameAnimation flyingAnimation;
	private GameAnimation attackingAnimation;
	
	private int attackTickCount = 0;
	private int attackDelay = 100;
	
	private boolean attacking = false;
	
	public Dragon4(int x, int y, int z) {
		super(x, y, 25, z, 1, 10, 1);
		stateTime = 200;
		flyingAnimation = DrawingUtility.createDragon4Animation();
		attackingAnimation = DrawingUtility.createDragon4AnimationAttack();
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
					stateTime = 100;
					attackingAnimation.setCurrentFrame(0);
				} else {
					stateTime = 100;
				}
				
			}
		} else if(state == 3) {
			stateTime--;
			if(stateTime == 40) {
				attacking = true;
			}
			
			if(stateTime == 0) {
				if(GameLogic.enemyOnScreen) {
					stateTime = 100;
				} else {
					state = 1;
					stateTime = 100;
					isLeft = RandomUtility.random(0, 1) == 0 ? true : false;
					tickCountX = tickNeedX-1;
				}
				
			}
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
		
		AttackObject atk = new AttackObject(x, y, 15, zCounter, 5, targetEnemy.x, targetEnemy.y, 5, 3);
//		AttackObject atk = new AttackObject(x, y, 5, zCounter, 1, targetEnemy.x, targetEnemy.y, 4, 4);
		onScreenAttack.add(atk);
		RenderableHolder.getInstance().add(atk);

	}
	
	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
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
