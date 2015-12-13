package render;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import logic.PlayerStatus;


public class DrawingUtility {

	protected static final Font standardFont = new Font("Tahoma",Font.BOLD,30);
	protected static final Font smallFont = new Font("Tahoma",Font.PLAIN,9);
	
	private static BufferedImage getImage(String directory){
		try {
			ClassLoader loader = DrawingUtility.class.getClassLoader();
			BufferedImage image = ImageIO.read(loader.getResource(directory));
			return image;
					
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static final BufferedImage egg = getImage("res/Egg.png");
	
	protected static final BufferedImage bg = getImage("res/Background.jpg");
	protected static final BufferedImage ui = getImage("res/UI.png");
	protected static final BufferedImage titleBackGround = getImage("res/titleBackGround.jpg");
	protected static final BufferedImage startButton = getImage("res/Egg1.png");
	protected static final BufferedImage highScoreButton = getImage("res/Egg1.png");
	protected static final BufferedImage timeLineDragon = getImage("res/TimeLineDragon.png");
	
	protected static final BufferedImage dragon1 = getImage("res/dragon1.png");
	protected static final BufferedImage dragon1_egg = getImage("res/dragon1_egg.png");
	
//	protected static final BufferedImage gun = getImage("res/img/gun.png");
//	protected static final BufferedImage gun_inf = getImage("res/img/gun_inf.png");
//	protected static final BufferedImage shootAnim = getImage("res/img/shootAnim.png");
	
	protected static final AlphaComposite transcluentWhite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
	protected static final AlphaComposite opaque = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
	
//	public static BufferedImage getShootanim() {
//		return shootAnim;
//	}
	
	public static BufferedImage getButton(String name) {
		if(name.equalsIgnoreCase("start")) {
			return startButton;
		} else if(name.equalsIgnoreCase("high score")) {
			return highScoreButton;
		}
		return null;
	}
	
	public static void drawShootableObject(Graphics2D g2,int x,int y,int radius,String name,boolean isPointerOver){
		
		g2.setColor(Color.BLACK);
		g2.fillOval(x-radius-2, y-radius-2, radius*2+4, radius*2+4);
		
		if(name.equalsIgnoreCase("simple")) {
			g2.setColor(Color.BLUE);
		} else if(name.equalsIgnoreCase("splitter")) {
			g2.setColor(Color.RED);
		} else if(name.equalsIgnoreCase("small")) {
			g2.setColor(Color.YELLOW);
		}
		
		g2.fillOval(x-radius, y-radius, radius*2, radius*2);
		
		if(isPointerOver){
			g2.setComposite(transcluentWhite);
			g2.setColor(Color.WHITE);
			g2.fillOval(x-radius, y-radius, radius*2, radius*2);
			g2.setComposite(opaque);
		}
	}
	
/*	public static void drawItemGun(Graphics2D g2,int x,int y,int radius,String name,boolean isPointerOver){
		g2.setColor(Color.BLACK);
		g2.fillOval(x-radius-2, y-radius-2, radius*2+4, radius*2+4);
		g2.setColor(Color.GRAY);
		g2.fillOval(x-radius, y-radius, radius*2, radius*2);
		
		if(name.equalsIgnoreCase("gun")) {
			g2.drawImage(gun, null, x-15, y-15);
		} else if(name.equalsIgnoreCase("“gun_inf")) {
			g2.drawImage(gun_inf, null, x-15, y-15);
		}
		
		if(isPointerOver){
			g2.setComposite(transcluentWhite);
			g2.setColor(Color.WHITE);
			g2.fillOval(x-radius-2, y-radius-2, radius*2+4, radius*2+4);
			g2.setComposite(opaque);
		}
	}*/
	
	public static void drawItemBullet(Graphics2D g2,int x,int y,int radius,boolean isPointerOver){

		g2.setColor(Color.BLACK);
		g2.fillOval(x-radius-2, y-radius-2, radius*2+4, radius*2+4);
		g2.setColor(Color.GRAY);
		g2.fillOval(x-radius, y-radius, radius*2, radius*2);
		
		g2.setColor(Color.BLACK);
		g2.fillRect(x-20, y-10, 20, 20);
		g2.fillOval(x-20, y-10, 40, 20);
		
		if(isPointerOver){
			g2.setComposite(transcluentWhite);
			g2.setColor(Color.WHITE);
			g2.fillOval(x-radius-2, y-radius-2, radius*2+4, radius*2+4);
			g2.setComposite(opaque);
		}
	}
	
/*	public static void drawIconGun(Graphics2D g2,int bulletQuantity,int maxBullet,boolean isInfiniteBullet){
		if(gun == null || (isInfiniteBullet && gun_inf == null)) return;
		g2.setFont(DrawingUtility.smallFont);
		if(isInfiniteBullet){
			g2.drawImage(gun_inf,null,ConfigurableOption.screenWidth/2-15,5);
		}else{
			g2.drawImage(gun,null,ConfigurableOption.screenWidth/2-15,5);
			g2.setColor(Color.WHITE);
			g2.drawString(bulletQuantity+"/"+maxBullet,ConfigurableOption.screenWidth/2,35);
		}
	}*/
	
	public static void drawStatusBar(Graphics2D g2, int currentEgg, boolean pause, 
										int timeSpent, GameAnimation timeLineAnimation){

		g2.setColor(Color.BLACK);
		g2.fillRect(20, 600, 80, 60);
		
		g2.setColor(Color.WHITE);
		g2.setFont(standardFont);
		g2.drawString( "" + currentEgg + " " + (timeSpent*PlayerStatus.TIME_CLOCK)/1000, 20, 650);
		
//		g2.drawString("SOCRE : "+score, ConfigurableOption.screenWidth/2 + 40, 35);
		
		
		if(pause) {
			g2.setFont(standardFont);
//			g2.drawString("PAUSE", ConfigurableOption.screenWidth/2-40, ConfigurableOption.screenHeight/2);
		}
//		start at 260 
//		end at 1000
//		one time line equals to 740 time spent
		timeLineAnimation.draw(g2, 260 + (int)((timeSpent%(180*100))*740.0/(180*100)), 19, false);
//		timeLineAnimation.draw(g2, 260 + (int)(((179*50)%(180*50))*740.0/(180*50)), 19, false);
	}
	public static GameAnimation createTimeLineAnimation() {
		GameAnimation anim = new GameAnimation(DrawingUtility.timeLineDragon,12,1,12,6);
		anim.play();
		return anim;
	}
	
	public static GameAnimation createAttackAnimation() {
		GameAnimation anim = new GameAnimation(DrawingUtility.dragon1,12,1,12,3);
		anim.play();
		return anim;
	}
	
	public static GameAnimation createDragon1Animation() {
		GameAnimation anim = new GameAnimation(DrawingUtility.dragon1,12,1,12,3);
		anim.play();
		return anim;
	}
	
	public static GameAnimation createDragon1AnimationLayingEgg() {
		GameAnimation anim = new GameAnimation(DrawingUtility.dragon1_egg,12,1,12,4);
		anim.play();
		return anim;
	}
/*	public static GameAnimation createShootingAnimationAt(int x,int y){
		GameAnimation anim = new GameAnimation(DrawingUtility.shootAnim,7,1);
		anim.centerAnimationAt(x,y);
		anim.play();
		return anim;
	}*/
}
