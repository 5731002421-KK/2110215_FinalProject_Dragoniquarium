package render;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.TileObserver;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import ui.HighScoreUtility;
import main.Main;


public class GameTitle extends JPanel {
	
	private JPanel optionPanel;
	private JButton newGame;
	private JButton viewScore;
	
	public GameTitle() {
		
		this.setPreferredSize(new Dimension(1280, 700));
		this.setLayout(new BorderLayout());
		
		JLabel title = new JLabel("Shoot the bullet", SwingConstants.CENTER);
		this.add(title, BorderLayout.NORTH);
		title.setFont(new Font("Tahoma", Font.BOLD + Font.ITALIC, 30));
		title.setForeground(Color.BLACK);
		title.setBackground(Color.BLUE);
		title.setOpaque(true);
		
		
		JPanel buttonPanel = new JPanel();
		this.add(buttonPanel, BorderLayout.SOUTH);
		FlowLayout flayout = new FlowLayout();
		flayout.setHgap(1280/8);
		flayout.setVgap(5);
		buttonPanel.setLayout(flayout);
		buttonPanel.setBackground(Color.GREEN);
		
		// Button
		JButton newGame = new JButton("New Game");
		buttonPanel.add(newGame);
		
		newGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String txt = "New Game\n";
				txt += "objectCreationMinDelay=";
				txt += "objectCreationMaxDelay=" ;
				txt += "objectMinDuration=" ;
				txt += "objectMaxDuration=";
				txt += "timelimit=";
				Main.newGame();
//				JOptionPane.showMessageDialog(null, txt);
			}
		});
		
		
		JButton viewScore = new JButton("High Score");
		buttonPanel.add(viewScore);
		
		viewScore.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				HighScoreUtility.displayTop10();
//				JOptionPane.showMessageDialog(null, "High Score");
				
			}
		});
		
		// end Button
		
		
		JPanel optionPanel = new JPanel(new BorderLayout());
		JPanel rSet = new JPanel();
		JPanel oSet = new JPanel();
		
		optionPanel.add(rSet, BorderLayout.NORTH);
		optionPanel.add(oSet, BorderLayout.CENTER);
		this.add(optionPanel, BorderLayout.CENTER);
		
		///// rSet
		rSet.setLayout(new FlowLayout());
		
		JLabel wi = new JLabel("WIDTH");
		rSet.add(wi);
		
		JTextField textW = new JTextField(""+1280);
		textW.setPreferredSize(new Dimension(100, 20));
		rSet.add(textW);
		
		JLabel he = new JLabel("HEIGHT");
		rSet.add(he);
		
		JTextField textH = new JTextField(""+700);
		textH.setPreferredSize(new Dimension(100, 20));
		rSet.add(textH);
		
		JButton apply = new JButton("Apply");
		rSet.add(apply);
		
//		frame.setVisible(true);
	}
	
	

}
