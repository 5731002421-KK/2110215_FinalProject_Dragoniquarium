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
	
	public Dragon2(int x, int y, int z) {
		super(x, y, 25, z, 1, 1, 0);
		stateTime = 200;
//		flyingAnimation = DrawingUtility.createDragon1Animation();
//		attackingAnimation = DrawingUtility.createDragon1AnimationLayingEgg();
	}
	
	public void attack(List <AttackObject> onScreenAttack, TargetObject targetEnemy, int zCounter ) {
		if(targetEnemy == null) {
			return ;
		}
		if(attackTickCount < attackDelay) {
			attackTickCount++;
			return ;
		}
		
		attackTickCount = 0;
		AttackObject atk = new AttackObject(x, y, 10, zCounter, 1, targetEnemy.x, targetEnemy.y, 7, 2);
		onScreenAttack.add(atk);
		RenderableHolder.getInstance().add(atk);

	}
	
	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.GREEN);
		g2d.drawOval((int)x-radius, (int)y-radius, 2*radius, 2*radius);	
	}

	@Override
	protected void performStateAction() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
