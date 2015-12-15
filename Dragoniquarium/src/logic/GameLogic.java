package logic;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;




import render.DrawingUtility;
import render.GameAnimation;
import main.Main;
import input.InputUtility;
import render.RenderableHolder;

public class GameLogic {
	
	private static GameLogic instance;
	private PlayerStatus player;
	protected List <TargetObject> onScreenObject =  new CopyOnWriteArrayList<TargetObject>();
	protected List <AttackObject> onScreenAttack = new CopyOnWriteArrayList<AttackObject>();
	protected List <Button> onScreenButton = new ArrayList<Button>();
	protected List <GameAnimation> onScreenAnimation = new CopyOnWriteArrayList<GameAnimation>();
	
	private static final int SPAWN_DELAY = 100;
	private int spawnDelayCounter ;
	
	public static boolean enemyOnScreen = false; 
	public TargetObject targetEnemy ; //MonsterObject
	
	public static GameLogic getInstance() {
		return instance;
	}
	
	/*
	 * Reserved z
	 * MIN_VALUE : background
	 * MAX_VALUE-1 : animation effect
	 * MAX_VALUE : player's status
	 */
	private static int zCounter = Integer.MIN_VALUE+1;
	

	public GameLogic() {
//		player = new PlayerStatus();
//		playerStatus = new PlayerStatus();
//		RenderableHolder.getInstance().add(player);
//		RenderableHolder.getInstance().add(playerStatus);
//		Button button = new Button(1, 30, 480, 30, 30, 100);
//		onScreenButton.add(button);
//		RenderableHolder.getInstance().add(button);
//		spawnDelayCounter = 0;
		
	}
	
	//Called before enter the game loop
	public synchronized void onStart(){
		player = new PlayerStatus();
//		playerStatus = new PlayerStatus();
		RenderableHolder.getInstance().add(player);
//		RenderableHolder.getInstance().add(playerStatus);
		createButton();
		spawnDelayCounter = 0;
	}
	
	//Called after exit the game loop
	public synchronized void onExit(){
		onScreenObject.clear();
		onScreenAttack.clear();
		onScreenButton.clear();
		onScreenAnimation.clear();
		RenderableHolder.getInstance().clear();
	}
	
	public void logicUpdate() {
		
//		player.update();
//		System.out.println("logic update");
		
		
		//Paused
//		if(InputUtility.getKeyTriggered(KeyEvent.VK_ENTER)){
//			if(player.isPause()) player.setPause(false);
//			else player.setPause(true);
//		}
		if(player.isPause()){
			return;
		}
		
		// clear destroyed object
		for(TargetObject obj : onScreenObject) {
			if (obj.isDestroyed()) {
				onScreenObject.remove(obj);
				RenderableHolder.getInstance().getRenderableList().remove(obj);
			}
		}
		
		// clear destroy attack
		for(AttackObject obj : onScreenAttack) {
			if (obj.isDestroyed()) {
				onScreenAttack.remove(obj);
				RenderableHolder.getInstance().getRenderableList().remove(obj);
			}
		}
		
		// clear and update animation attack
		for(GameAnimation anim : onScreenAnimation) {
			if (!anim.isPlaying()) {
				onScreenAnimation.remove(anim);
				RenderableHolder.getInstance().getAnimationList().remove(anim);
			}
			anim.updateAnimation();
		}
			
		// check if any enemy on screen
		checkEnemyOnScreen();
		
		// move and attack
		for(TargetObject obj : onScreenObject) {
			obj.move();
//			enemyOnScreen = false;
			if(obj instanceof EnemyObject) {
//				enemyOnScreen = true;
				((EnemyObject)obj).attack(onScreenAttack, zCounter);
			} else if(obj instanceof Dragon2) {
				((Dragon2)obj).attack(onScreenAttack, targetEnemy, zCounter);
			} else if(obj instanceof Dragon4) {
				((Dragon4)obj).attack(onScreenAttack, targetEnemy, zCounter);
			} else if(obj instanceof Dragon5) {
				((Dragon5)obj).attack(onScreenAttack, targetEnemy, zCounter);
			}
			
		}
		
		
		dragon1CreateEgg();
		
		processAttack();
		
		processShootAndCollect();
		
		
		// dragon attack
		// update fire ball
		
		// push button
		Button targetButton = null;
		boolean click = false;
		if(input.InputUtility.isMouseLeftClicked() ){
			click = true;
		}
		
		targetButton = getButtonAt(InputUtility.getMouseX(), InputUtility.getMouseY());
		if(targetButton != null && click ){
			targetButton.click(onScreenObject, player, zCounter);
//			Main.goToTitle();
		}
		
		
		spawnDelayCounter++;
		
		/*if (spawnDelayCounter >= SPAWN_DELAY ) {
			AttackObject atk = new AttackObject(RandomUtility.random(300, 700), 200, 10, 
										zCounter, 1, RandomUtility.random(300, 700), 600, 3, 1);
			onScreenAttack.add(atk);
			RenderableHolder.getInstance().add(atk);
		}*/
		if (spawnDelayCounter >= SPAWN_DELAY ) {
			spawnDelayCounter = -500;
			TargetObject newEnemy = new Enemy1(500, 300, zCounter);
			onScreenObject.add(newEnemy);
			RenderableHolder.getInstance().add(newEnemy);
			GameAnimation anim = DrawingUtility.createWarppingAnimation(500, 300);
			onScreenAnimation.add(anim);
			RenderableHolder.getInstance().add(anim);
		}
		if (spawnDelayCounter >= SPAWN_DELAY ) {
			spawnDelayCounter = 0;
			TargetObject egg = new Dragon1(RandomUtility.random(300, 700), 0, zCounter);
			onScreenObject.add(egg);
			RenderableHolder.getInstance().add(egg);
		}
		zCounter++;
		if(zCounter == Integer.MAX_VALUE-1){
			zCounter = Integer.MIN_VALUE+1;
		}
	}
	
	
	
	
	// TODO end logic update
	
	private void checkEnemyOnScreen() {
		if(targetEnemy != null && !targetEnemy.isDestroyed()) {
			return ;
		}
		targetEnemy = null;
		enemyOnScreen = false;
		for(TargetObject obj : onScreenObject) {
			if (obj instanceof EnemyObject) {
				targetEnemy = obj;
				enemyOnScreen = true;
				break ;
			}
		}
	}
	
	
	private void dragon1CreateEgg() {
		// lay Egg
		for(TargetObject obj : onScreenObject) {
			if( obj instanceof Dragon1 ) {
				Dragon1 temp = (Dragon1)obj; 
				if(temp.layingEgg) {
					temp.layingEgg = false;
					createEgg(temp.x, temp.y-obj.radius);
				}
			} // else if( obj instanceof)
		}
	}
	
	private void processAttack() {
		// attack objects do damage
		for(AttackObject obj : onScreenAttack) {
			obj.move();
			if(obj.attackType == 1) {
				for(TargetObject target : onScreenObject) {
					if(target.destroyed || target instanceof EnemyObject) continue;
					if(target instanceof DamageableObject && target.contains(obj.x, obj.y, obj.radius)) {
						((DamageableObject)target).hit(obj.getAttack());
						// if hit guardian dragon 
						if(target instanceof Dragon3 || target instanceof Dragon4 || target instanceof Dragon5) {
							obj.destroyed = true;
							GameAnimation anim = DrawingUtility.createAttack1DestroyAt((int)obj.x, (int)obj.y);
							onScreenAnimation.add(anim);
							RenderableHolder.getInstance().add(anim);
							break;
						}
					}
				}
				
			} else if(obj.attackType == 2 || obj.attackType == 3 || obj.attackType == 4) {
				for(TargetObject target : onScreenObject) {
					if(target.destroyed ) continue;
					if(target instanceof EnemyObject && target.contains(obj.x, obj.y, obj.radius)) {
						((DamageableObject)target).hit(obj.getAttack());
						obj.destroyed = true;
						GameAnimation anim;
						if(obj.attackType == 2) {
							anim = DrawingUtility.createAttack2DestroyAt((int)obj.x, (int)obj.y);
						} else if(obj.attackType == 3) {
							createEgg(obj.x, obj.y);
							anim = DrawingUtility.createAttack3DestroyAt((int)obj.x, (int)obj.y);
						} else {
							anim = DrawingUtility.createAttack4DestroyAt((int)obj.x, (int)obj.y);
						}
						
						onScreenAnimation.add(anim);
						RenderableHolder.getInstance().add(anim);
						break;
					}
				}
			} 
		}
	}
	
	private void processShootAndCollect() {
		// attack object type 1 is clicked
		// check shoot and grab
		TargetObject target = null;
//				TargetObject grabbedObject = null;
		if(!player.isDisplayingArea(InputUtility.getMouseX(), InputUtility.getMouseY())){
			boolean shoot = false;
			if(input.InputUtility.isMouseLeftClicked() ){
				shoot = true;
			}
			
			target = getTopMostTargetAt(InputUtility.getMouseX(), InputUtility.getMouseY());
			if(target != null && shoot ){
				
				if(target instanceof CollectibleObject)	{
					((CollectibleObject)target).grab(player);
				}
				else if(target instanceof AttackObject) {
					((AttackObject)target).hitByPlayer();
				}
				else if(target instanceof EnemyObject) {
					((EnemyObject)target).isChased(InputUtility.getMouseX(), InputUtility.getMouseY());
					((EnemyObject)target).hit(1);
					// target instance of Monster
				}
				
			} else if (enemyOnScreen && shoot) {
				// shoot animation
//						onScreenAnimation.add(DrawingUtility.createShootingAnimationAt(
//						input.InputUtility.getMouseX(), input.InputUtility.getMouseY()));
			}
		}
	}
	
	private Button getButtonAt(int x, int y) {
		Button but = null;
		for(Button target : onScreenButton) {
			if(target.contains(x, y)) {
				but = target;
				but.setPointerOver(true);
				break;
			}else{
				target.setPointerOver(false);
			}
		}
		return but;
	}
	
	private TargetObject getTopMostTargetAt(int x,int y){
		TargetObject obj = null;
		// find attack first
		for(TargetObject target : onScreenAttack){
			if(target.contains(x, y)){
				if(obj != null){
					if(target.getZ() > obj.getZ()){
						obj.setPointerOver(false);
						obj = target;
						obj.setPointerOver(true);
					}
				}else{
					obj = target;
					obj.setPointerOver(true);
				}
			}else{
				target.setPointerOver(false);
			}
		}
		if(obj != null) {
			return obj;
		}
		// find other object
		for(TargetObject target : onScreenObject){
			if(target.contains(x, y)){
				if(obj != null){
					if(target.getZ() > obj.getZ()){
						obj.setPointerOver(false);
						obj = target;
						obj.setPointerOver(true);
					}
				}else{
					obj = target;
					obj.setPointerOver(true);
				}
			}else{
				target.setPointerOver(false);
			}
		}
//		if(obj != null || !enemyOnScreen) {
//			return obj;
//		}
		// find enemy
	/*	TargetObject target = targetEnemy;
		if(target.contains(x, y)){
			obj = target;
			obj.setPointerOver(true);
		} else{
			target.setPointerOver(false);
		}*/
		return obj;
	}
	
	public void createEgg(double x, double y) {
		TargetObject egg = new Egg1((int)x, (int)y);
		onScreenObject.add(egg);
		RenderableHolder.getInstance().add(egg);
	}
	
	private void createButton() {
		
		Button button = new Button(1, 15, 432, 30, 30, 100);
		onScreenButton.add(button);
		RenderableHolder.getInstance().add(button);
		
		button = new Button(2, 15, 330, 30, 30, 100);
		onScreenButton.add(button);
		RenderableHolder.getInstance().add(button);
		
		button = new Button(3, 15, 228, 30, 30, 100);
		onScreenButton.add(button);
		RenderableHolder.getInstance().add(button);
		
		button = new Button(4, 15, 126, 30, 30, 100);
		onScreenButton.add(button);
		RenderableHolder.getInstance().add(button);
		
		button = new Button(5, 15, 27, 30, 30, 100);
		onScreenButton.add(button);
		RenderableHolder.getInstance().add(button);
		
		// end game button
		button = new Button(6, 15, 540, 30, 30, 100);
		onScreenButton.add(button);
		RenderableHolder.getInstance().add(button);
	}
}
