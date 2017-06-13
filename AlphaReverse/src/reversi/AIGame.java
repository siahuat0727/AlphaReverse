package testsingleplayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AIGame extends JFrame implements ActionListener
{
	private int bsize;
	private int AI_level;
	private int color;
	private int noMove = 0;
	static boolean pressed = false ;
	private int turn;
	private int count = 0;
	private int x , y;
	private String command;
	
	static Object lock = new Object();
	
	
	JButton undo = new JButton("UNDO");
	JButton restart = new JButton("RESTART");
	JButton black = new JButton("Black");
	JButton white = new JButton("White");
	JLabel status = new JLabel("Please pick a colour to start");
	
	JButton boardc[][] = new JButton[SingleWindow.lol][SingleWindow.lol];
	JPanel gameboard = new JPanel();
	
	public AIGame(int size , int difficulty)
	{
		bsize = size;
		AI_level = difficulty;
		System.out.println("size is " + size + " diff is " + difficulty );
		
		setSize(800,600);
		setVisible(true);
		setLayout(null);
		
		//button included in this ui
		//undo , restart , black , white , pause
		
		
		undo.setSize(200, 80);
		restart.setSize(200, 80);
		black.setSize(200, 40);
		white.setSize(200, 40);
		status.setSize(500,40);
		
		undo.setLocation(0,0);
		restart.setLocation(200,0);
		black.setLocation(400,0);
		white.setLocation(400,40);
		status.setLocation(0, 81);
		
		add(undo);
		add(restart);
		add(black);
		add(white);
		add(status);
		
		black.addActionListener(this);
		white.addActionListener(this);
		undo.addActionListener(this);
		
		gameboard.setLayout(new GridLayout(bsize,bsize));
		int i = 0 , j = 0 ;
		for( i = 0 ; i < bsize ; i++)
		{
			for( j = 0 ; j < bsize ; j++ )
			{
				//for each button their command is B'i''j'
				String buttonname = "B" + i + j;

				boardc[i][j] = new JButton(buttonname);
				boardc[i][j].addActionListener(this);
				boardc[i][j].setEnabled(false);
				gameboard.add(boardc[i][j]);
			}
		}
		
		//panel will auto resize and relocate
		gameboard.setSize(bsize*40, bsize*40);
		gameboard.setLocation((800 - bsize*40)/2 , 130);
		add(gameboard);
	}
	
	
	public void startGame()
	{
		//board button 
		//using panel to fit the button inside
		//the button has no size thus 
		
		Board.initialize(bsize);
		if( color == -1 ) 
		{
			status.setText("You are black , you go first");
		}
		else
		{
			ReversiRule.canIgo(Board.BLACK);
			AI.go(Board.BLACK, AI_level);
			Board.printBoard();
			status.setText("You are white , now is not your turn");
		}
		
		for( int i = 0 ; i < bsize ; i++)
		{
			for( int j = 0 ; j < bsize ; j++ )
			{
				if( Board.board[i][j] == Board.BLACK ) boardc[i][j].setBackground( Color.BLACK);
				else if(Board.board[i][j] == Board.WHITE ) boardc[i][j].setBackground( Color.WHITE);
				else boardc[i][j].setEnabled(true);
			}
		}

		System.out.println("asxdvgdfd");

	}
	
	public class Thr extends Thread
	{
		public void run()
		{
			synchronized(lock)
			{
				pressed = false;
			}
			
			while(true)
			{
				if( pressed == false )
				{
					while(true) 
					{
						count ++;
						synchronized( lock )
						{
							if( pressed == true )
							{
								break;
							}
						}
					}
				}
				
				x = Integer.parseInt(command.substring(1, 2));
				y = Integer.parseInt(command.substring(2));
				
				System.out.println("hi " + x + " " + y);
				
				if( ReversiRule.canIgo( color ) == false)
				{
					noMove++;
				}
				else
				{
					ReversiRule.goToThis();
					ReversiRule.go(x, y, color );
				}
				
				for( int i = 0 ; i < bsize ; i++)
				{
					for( int j = 0 ; j < bsize ; j++ )
					{
						if( Board.board[i][j] == -1 )
						{
							boardc[i][j].setEnabled(false);
							boardc[i][j].setBackground( Color.BLACK);
						} 
						else if(Board.board[i][j] == 1 )
						{
							boardc[i][j].setEnabled(false);
							boardc[i][j].setBackground( Color.WHITE);
						}
					}
				}
				status.setText("white " + Board.whiteCount + " vs black " + Board.blackCount);
				

				try
				{
					Thread. sleep(1000);
				}
				catch(InterruptedException ex)
				{
					ex.printStackTrace();
				}
				
				Board.printBoard();
				
				if( ReversiRule.canIgo(-color) == false)
				{
					noMove++;
				}
				else
				{
					noMove = 0;
					AI.go(-color, AI_level);
					Board.printBoard();
				}
					
				for( int i = 0 ; i < bsize ; i++)
				{
					for( int j = 0 ; j < bsize ; j++ )
					{
						if( Board.board[i][j] == -1 )
						{
							boardc[i][j].setEnabled(false);
							boardc[i][j].setBackground( Color.BLACK);
						} 
						else if(Board.board[i][j] == 1 )
						{
							boardc[i][j].setEnabled(false);
							boardc[i][j].setBackground( Color.WHITE);
						}
					}
				}
				status.setText("white " + Board.whiteCount + " vs black " + Board.blackCount);
				
				synchronized(lock)
				{
					pressed = false;
				}
			}
		}
	}
	
	public void actionPerformed( ActionEvent e )
	{
		command = e.getActionCommand();
		System.out.println("asd");
		
		if( command.equals("UNDO") )
		{
			
		}
		else if( command.equals("Black") ) 
		{
			System.out.println(command);
			color = -1;
			startGame();
			Thr walau = new Thr();
			walau.start();
		}
		else if( command.equals("White") )
		{
			System.out.println(command);
			color = 1;
			startGame();
			Thr walau = new Thr();
			walau.start();
		}
		/////////////////////////////////////////////////// chess action
		else
		{
			synchronized(lock)
            {
                pressed = true;
            }
		}
		////////////////////////////////////////// chess action
	}
	
}
