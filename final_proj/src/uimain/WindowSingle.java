package uimain;

import javax.swing.*;
import java.awt.event.*;

public class WindowSingle extends JFrame implements ActionListener
{
	public String bsize;
	
	private JTextField size;
	
	public WindowSingle()
	{
		setVisible(true);
		setSize(450,600);
		setLayout(null);
		
		JLabel diff = new JLabel(" Please choose AI difficulty ");
		diff.setSize(400 , 80 );
		diff.setLocation(0, 0);
		
		JButton easy = new JButton("EASY");
		easy.setSize(300 , 80);
		easy.setLocation(50, 200);
		
		JButton medium = new JButton("MEDIUM");
		medium.setSize(300 , 80);
		medium.setLocation(50, 300);
		
		JButton hard = new JButton("HARD");
		hard.setSize(300 , 80);
		hard.setLocation(50, 400);
		
		size = new JTextField("Enter board size , maximum 10 , minimum 4:" , 30);
		size.setLocation(50 , 100);
		size.setSize(size.getPreferredSize());
		
		easy.addActionListener(this);
		medium.addActionListener(this);
		hard.addActionListener(this);
		
		add(diff);
		add(easy);
		add(medium);
		add(hard);
		add(size);
		
		
	}
	
	public void actionPerformed( ActionEvent e )
	{
		bsize = size.getText();
		int lol = Integer.parseInt(bsize.substring(42));
		System.out.println(lol);
		
		
		String command = e.getActionCommand();
		
		if(command.equals("EASY"))
		{
			AIGame game = new AIGame( lol , 1 );
			game.startGame();
		}
		else if(command.equals("MEDIUM"))
		{
			AIGame game = new AIGame( lol , 2 );
			game.startGame();
		}
		else if(command.equals("HARD"))
		{
			AIGame game = new AIGame( lol , 3 );
			game.startGame();
		}

	}
	
	
}