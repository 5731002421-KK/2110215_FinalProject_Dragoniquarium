package render;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import logic.PlayerStatus;

public class PauseButton extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private BufferedImage pauseImage;
	private BufferedImage playImage;
	private boolean isPointerOver = false;
	public Color color = Color.RED;
	
	public PauseButton(int x, int y) {
		pauseImage = DrawingUtility.getButton("pause");
		this.setBounds(x, y, pauseImage.getWidth(), pauseImage.getHeight());
		
		playImage =  DrawingUtility.getButton("play");
		
		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				color = Color.RED;
				if(PlayerStatus.instance.isPause()) {
					PlayerStatus.instance.setPause(false);
					synchronized (PlayerStatus.instance) {
						PlayerStatus.instance.notifyAll();
					}
				} else {
					PlayerStatus.instance.setPause(true);
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				color = Color.RED;
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				color = Color.YELLOW;
			}
		});
		
		
		
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setBackground(color);
		g2d.clearRect(0, 0, getWidth(), getHeight());
		
		if(isPointerOver) {
			
		}
		
		if(PlayerStatus.instance.isPause()) {
			g2d.drawImage(playImage, null, 0, 0);
		} else {
			g2d.drawImage(pauseImage, null, 0, 0);
		}
		
		
//		g2d.fillRect(x, y, width, height);
	}
	
	
}