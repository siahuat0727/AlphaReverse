package reversi;

import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;

public class WindowSingle extends JFrame implements ActionListener
{
	public String bsize;
	private JTextField size;
	public static int lol ;
	
	public WindowSingle() 
	{
		setVisible(true);
		setSize(805, 600);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // set window form to center screen
		setResizable(false);
		setTitle("Single Player");
		
		ImageIcon icon = new ImageIcon("Resources/Single.png");
		ImageIcon imgEasy = new ImageIcon("Resources/BtnEasy.png");
		ImageIcon imgMedium = new ImageIcon("Resources/BtnMedium.png");
		ImageIcon imgHard = new ImageIcon("Resources/BtnHard.png");
		JLabel imgMain = new JLabel(icon);
		
		// init label (frame background)
		imgMain.setSize(800, 600);
		
		JButton easy = new JButton("");
		easy.setSize(300 , 64);
		easy.setLocation(250, 200);
		easy.setIcon(imgEasy);
		easy.setHorizontalTextPosition(0);
		easy.setActionCommand("EASY");
		
		JButton medium = new JButton("");
		medium.setSize(300 , 64);
		medium.setLocation(250, 276);
		medium.setIcon(imgMedium);
		medium.setHorizontalTextPosition(0);
		medium.setActionCommand("MEDIUM");
		
		JButton hard = new JButton("");
		hard.setSize(300 , 64);
		hard.setLocation(250, 350);
		hard.setIcon(imgHard);
		hard.setHorizontalTextPosition(0);
		hard.setActionCommand("HARD");
		
		size = new JTextField("8" , 30);
		size.setLocation(450 , 440);
		size.setSize(140, 40);
		size.setHorizontalAlignment(0);
		size.setFont(new Font("Arial Black", Font.BOLD, 18));
		size.setBackground(Color.black);
		size.setForeground(Color.green);
		
		easy.addActionListener(this);
		medium.addActionListener(this);
		hard.addActionListener(this);
		
		add(easy);
		add(medium);
		add(hard);
		add(size);
		add(imgMain);
		
	}
	
	public void actionPerformed( ActionEvent e )
	{
		bsize = size.getText();
		lol = Integer.parseInt(bsize);
		System.out.println(lol);
		
		
		String command = e.getActionCommand();
		
		if(command.equals("EASY"))
		{
			AIGame game = new AIGame( lol , 1 );
		}
		else if(command.equals("MEDIUM"))
		{
			AIGame game = new AIGame( lol , 2 );

		}
		else if(command.equals("HARD"))
		{
			AIGame game = new AIGame( lol , 3 );
		}

	}
	
	
}