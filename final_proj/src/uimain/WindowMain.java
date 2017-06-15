package uimain;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class WindowMain {

	public static void main(String[] args) {
		//variables
		JFrame frame= new JFrame();
		JButton btnSin = new JButton("");
		JButton btnMul = new JButton("");
		ImageIcon icon = new ImageIcon("Resources/Main.png");
		ImageIcon imgSin = new ImageIcon("Resources/BtnSingle.png");
		ImageIcon imgMul = new ImageIcon("Resources/BtnMulti.png");
		JLabel imgMain = new JLabel(icon);
		
		//init frame
		frame.setSize(805,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null); //set window form to center screen
		frame.setResizable(false);
		frame.setTitle("Alpha Reverse");
		
		//init button (single player)		
		btnSin.setLocation(40,440);
		btnSin.setSize(250,40);
		btnSin.setIcon(imgSin);
		btnSin.setHorizontalTextPosition(0);
		btnSin.setActionCommand("Single Player");
		btnSin.addActionListener(new MainEventListener());
		
		//init button (multi player)
		btnMul.setLocation(40,490);
		btnMul.setSize(250,40);
		btnMul.setIcon(imgMul);
		btnSin.setHorizontalTextPosition(0);
		btnMul.setActionCommand("Multi Player");
		btnMul.addActionListener(new MainEventListener());
		
		//init label (frame background)
		imgMain.setSize(800, 600);
		
		//add controls to frame and show
		frame.setLayout(null);
		frame.add(btnSin);
		frame.add(btnMul);
		frame.add(imgMain);
		frame.setVisible(true);
	}
}
