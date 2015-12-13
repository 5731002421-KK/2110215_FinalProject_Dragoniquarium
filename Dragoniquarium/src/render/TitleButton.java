package render;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class TitleButton extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	public Color color = Color.RED;
	
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
		
//		g2d.fillRect(x, y, width, height);
	}
	
	
}