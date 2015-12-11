package logic;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;



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
	protected List <GameAnimation> onScreenAnimation = new ArrayList<GameAnimation>();
	
	private static final int SPAWN_DELAY = 100;
	private int spawnDelayCounter ;
	
	public static boolean enemyOnScreen = false; 
	public EnemyObject enemy ; //MonsterObject
	
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
		player = new PlayerStatus();
//		playerStatus = new PlayerStatus();
		RenderableHolder.getInstance().add(player);
//		RenderableHolder.getInstance().add(playerStatus);
		Button button = new Button(1, 30, 480, 30, 30, 100);
		onScreenButton.add(button);
		RenderableHolder.getInstance().add(button);
		spawnDelayCounter = 0;
		
	}
	
	//Called before enter the game loop
	public synchronized void onStart(){
		player = new PlayerStatus();
//		playerStatus = new PlayerStatus();
		RenderableHolder.getInstance().add(player);
//		RenderableHolder.getInstance().add(playerStatus);
		Button button = new Button(1, 30, 480, 30, 30, 100);
		onScreenButton.add(button);
		RenderableHolder.getInstance().add(button);
		spawnDelayCounter = 0;
	}
	
	//Called after exit the game loop
	public synchronized void onExit(){
		onScreenObject.clear();
		onScreenAttack.clear();
		onScreenButton.clear();
		RenderableHolder.getInstance().clear();
	}
	
	public void logicUpdate() {
		
//		player.update();
//		System.out.println("logic update");
		
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
		
		
		// move
		for(TargetObject obj : onScreenObject) {
			obj.move();
			enemyOnScreen = false;
			if(obj instanceof EnemyObject) {
//				enemyOnScreen = true;
				((EnemyObject)obj).attack(onScreenAttack, zCounter);
			}
			
			// check out of screen
//			if (obj.x < 0 || obj.x > 1024 || obj.y < 0 || obj.y >= 900 ) {
//				obj.destroyed = true;
//			}
		}
		
		// lay Egg
		for(TargetObject obj : onScreenObject) {
			if( obj instanceof Dragon1 ) {
				Dragon1 temp = (Dragon1)obj; 
				if(temp.layingEgg) {
					temp.layingEgg = false;
					createEgg(temp.x, temp.y-5);
				}
			} // else if( obj instanceof)
		}
		
		// attack objects do damage
		for(AttackObject obj : onScreenAttack) {
			obj.move();
			if(obj.attackType == 1) {
				for(TargetObject target : onScreenObject) {
					if(target.destroyed || target instanceof EnemyObject) continue;
					if(target instanceof DamageableObject && target.contains(obj.x, obj.y)) {
						((DamageableObject)target).hit(obj.getAttack());
						
						// if hit guardian dragon 
						/*if(target instanceof Dragon3) {
							obj.destroyed = true;
							break;
						}*/
					}
				}
				
			} else if(obj.attackType == 2) {
				for(TargetObject target : onScreenObject) {
					if(target.destroyed ) continue;
					if(target instanceof EnemyObject && target.contains(obj.x, obj.y)) {
						((DamageableObject)target).hit(obj.getAttack());
					}
				}
				/*if( enemy.contains(obj.x, obj.y)) {
					// TODO
					obj.destroyed = true;
				}*/
			}
		}
		
		// attack object type 1 is clicked
		// check shoot and grab
		TargetObject target = null;
//		TargetObject grabbedObject = null;
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
//				onScreenAnimation.add(DrawingUtility.createShootingAnimationAt(
//				input.InputUtility.getMouseX(), input.InputUtility.getMouseY()));
			}
		}
		
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
			TargetObject newEnemy = new Enemy1(500, 300, 30, zCounter);
//			onScreenObject.add(newEnemy);
//			RenderableHolder.getInstance().add(newEnemy);
		}
		if (spawnDelayCounter >= SPAWN_DELAY ) {
			spawnDelayCounter = 0;
//			TargetObject egg = new Egg1(RandomUtility.random(0, 1000), 600, 10);
			TargetObject egg = new Dragon1(RandomUtility.random(300, 700), 0, zCounter);
//			onScreenObject.add(egg);
//			RenderableHolder.getInstance().add(egg);
		}
		zCounter++;
		if(zCounter == Integer.MAX_VALUE-1){
			zCounter = Integer.MIN_VALUE+1;
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
		// find egg
		for(TargetObject target : onScreenObject){
			if(/*target instanceof Egg1 &&*/ target.contains(x, y)){
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
		if(obj != null || !enemyOnScreen) {
			return obj;
		}
		// find enemy
		TargetObject target = enemy;
		if(target.contains(x, y)){
			obj = target;
			obj.setPointerOver(true);
		} else{
			target.setPointerOver(false);
		}
		return obj;
	}
	
	public void createEgg(double x, double y) {
		TargetObject egg = new Egg1((int)x, (int)y);
		onScreenObject.add(egg);
		RenderableHolder.getInstance().add(egg);
	}
	
	
}
