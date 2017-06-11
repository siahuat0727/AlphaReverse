package uimain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AIGame extends JFrame implements ActionListener
{
	private int bsize;
	
	public AIGame(int size , int difficulty)
	{
		bsize = size;
		System.out.println("size is " + size + " diff is " + difficulty );
		
		setSize(800,600);
		setVisible(true);
		setLayout(null);
		
		//button included in this ui
		//undo , restart , surrender , pause
		JButton undo = new JButton("UNDO");
		JButton restart = new JButton("RESTART");
		JButton surrender = new JButton("SURRENDER");
		
		undo.setSize(200, 80);
		restart.setSize(200, 80);
		surrender.setSize(200, 80);
		
		undo.setLocation(0,0);
		restart.setLocation(200,0);
		surrender.setLocation(400,0);
		
		add(undo);
		add(restart);
		add(surrender);		
	}
	
	
	
	public void startGame()
	{
		//board button 
		//using panel to fit the button inside
		//the button has no size thus 
		
		JButton board[][] = new JButton[bsize][bsize];
		JPanel gameboard = new JPanel();
		gameboard.setLayout(new GridLayout(bsize,bsize));
		int i = 0 , j = 0 ;
		for( i = 0 ; i < bsize ; i++)
		{
			for( j = 0 ; j < bsize ; j++ )
			{
				//for each button their command is B'i''j'
				String buttonname = "B" + i + j;
				//System.out.println(buttonname);
				board[i][j] = new JButton(buttonname);
				board[i][j].addActionListener(this);
				//board[i][j].setSize(30, 30);
				//board[i][j].setLocation( 200 + i*30, 200 + j*30);
				gameboard.add(board[i][j]);
			}
		}
		
		//panel will auto resize and relocate
		gameboard.setSize(bsize*40, bsize*40);
		gameboard.setLocation((800 - bsize*40)/2 , 100);
		add(gameboard);
	}
	
	public void actionPerformed( ActionEvent e )
	{
		
		System.out.println( e.getActionCommand() );
	}
	
}
