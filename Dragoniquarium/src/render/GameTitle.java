package render;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import ui.HighScoreUtility;
import main.Main;


public class GameTitle extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public static class TitleButton extends JPanel {
		
		private static final long serialVersionUID = 1L;
		private BufferedImage image;
		private Color color = Color.RED;
		
		public TitleButton(String name, int x, int y) {
			image = DrawingUtility.getButton(name);
			this.setBounds(x, y, image.getWidth(), image.getHeight());
		}
		
		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D)g;
			
			
			g2d.setBackground(color);
			g2d.clearRect(0, 0, getWidth(), getHeight());
			g2d.drawImage(image, null, 0, 0);
			
//			g2d.fillRect(x, y, width, height);
		}
		
		
	}
	
	public GameTitle() {
		
		this.setPreferredSize(new Dimension(1280, 700));
		this.setLayout(null);
		
		TitleButton startButton = new TitleButton("start", 400, 200);
		startButton.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				Main.newGame();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				startButton.color = Color.RED;
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				startButton.color = Color.YELLOW;
			}
		});
		this.add(startButton);
		
		
		TitleButton highScoreButton = new TitleButton("high score", 400, 500);
		highScoreButton.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				HighScoreUtility.displayTop10();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				highScoreButton.color = Color.RED;
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				highScoreButton.color = Color.YELLOW;
			}
		});
		this.add(highScoreButton);
		
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setBackground(Color.WHITE);
		g2d.clearRect(0, 0, 1280, 700);
		
		// draw background
		g2d.drawImage(DrawingUtility.titleBackGround, null, 0, 0);
	
//		System.out.println("-----");
		
	}
	
}
