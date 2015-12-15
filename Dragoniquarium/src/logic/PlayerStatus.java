package logic;

import java.awt.Color;
import java.awt.Graphics2D;

import render.DrawingUtility;
import render.GameAnimation;
import render.IRenderable;
import render.Resource;

public class PlayerStatus implements IRenderable{
	
	public static final int TIME_CLOCK = 10;
	public static PlayerStatus instance;
	
	private int timeSpent = 0;
	private int egg;
	private boolean pause = false;
	private GameAnimation timeLineAnimation;
	
	private TimeRunnable time;
	
	public PlayerStatus() {
		super();
		this.egg = 300;
		timeLineAnimation = DrawingUtility.createTimeLineAnimation();
		
		instance = this;
		
		time = new TimeRunnable(this);
		new Thread(time).start();
	}
	
	public boolean isPause() {
		return pause;
	}
	
	public void setPause(boolean pause) {
		this.pause = pause;
	}
	
	public boolean isDisplayingArea(int x,int y){
		return x < 50;
	}
	
	public int getTimeSpent() {
		return timeSpent;
	}
	
	public synchronized void increaseTimeSpent(int time) {
		this.timeSpent += time;
	}
	
	public void addEgg(int a) {
		egg += a;
	}
	
	public void subtractEgg(int a) {
		egg = Math.max(egg - a, 0);
	}

	public int getEgg() {
		return egg;
	}

	@Override
	public void draw(Graphics2D g2d) {
		
		if(!pause) {
			timeLineAnimation.updateAnimation();
		}
		DrawingUtility.drawStatusBar(g2d, egg, pause, timeSpent, timeLineAnimation);
		/*g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 420, 640, 60);
		g2d.setColor(Color.white);
		
		String scoreText = "EGG: " + this.egg;
		g2d.setFont(Resource.standardFont);
		g2d.drawString(scoreText, 10, 455);*/
		
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	@Override
	public int getZ() {
		return 2147483647;
	}
	
	
}
