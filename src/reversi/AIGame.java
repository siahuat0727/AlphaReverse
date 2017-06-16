package reversi;

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
	private int myColor;
	private String command;
	
	static Object lock = new Object();
	
	JButton undo = new JButton("");
	JButton restart = new JButton("");
	JButton black = new JButton("");
	JButton white = new JButton("");
	JLabel status = new JLabel("Please pick a colour to start");
	ImageIcon imgBlack = new ImageIcon("Resources/BtnBlack.png");
	ImageIcon imgWhite = new ImageIcon("Resources/BtnWhite.png");
	ImageIcon imgUndo = new ImageIcon("Resources/BtnUndo.png");
	ImageIcon imgRestart = new ImageIcon("Resources/BtnRestart.png");
	
	JButton boardc[][] = new JButton[WindowSingle.lol][WindowSingle.lol];
	JPanel gameboard = new JPanel();
	
	public AIGame(int size , int difficulty)
	{
		bsize = size;
		AI_level = difficulty;
		System.out.println("size is " + size + " diff is " + difficulty );
		
		// init window form
		setVisible(true);
		setSize(805, 600);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // set window form to center screen
		setResizable(false);
		setTitle("Single Player");
		
		//button included in this ui
		//undo , restart , black , white , pause
		
		ImageIcon icon = new ImageIcon("Resources/MultiGame.png");
		JLabel imgMain = new JLabel(icon);
		
		// init label (frame background)
		imgMain.setSize(800, 600);
		
		undo.setSize(250, 64);
		undo.setLocation(10,500);
		undo.setIcon(imgUndo);
		undo.setHorizontalTextPosition(0);
		undo.setActionCommand("UNDO");
		undo.addActionListener(this);
		undo.setEnabled(false);
		
		restart.setSize(250, 64);
		restart.setLocation(540,500);
		restart.setIcon(imgRestart);
		restart.setHorizontalTextPosition(0);
		restart.setActionCommand("RESTART");
		restart.addActionListener(this);
		restart.setEnabled(false);
		
		black.setSize(250, 64);
		black.setLocation(10, 10);
		black.setIcon(imgBlack);
		black.setHorizontalTextPosition(0);
		black.setActionCommand("Black");
		black.addActionListener(this);
		
		white.setSize(250, 64);
		white.setLocation(270,10);
		white.setIcon(imgWhite);
		white.setHorizontalTextPosition(0);
		white.setActionCommand("White");
		white.addActionListener(this);
		
		status.setSize(500,40);
		status.setForeground(Color.red);
		status.setFont(status.getFont().deriveFont(24.0f));
		status.setLocation(240, 81);
		
		add(undo);
		add(restart);
		add(black);
		add(white);
		add(status);
		
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
				boardc[i][j].setBackground(Color.green);
				gameboard.add(boardc[i][j]);
			}
		}
		
		//panel will auto resize and relocate
		gameboard.setSize(bsize*40, bsize*40);
		gameboard.setLocation((800 - bsize*40)/2 , 130);
	
		add(gameboard);
		add(imgMain);
	}
	
	
	public void startGame()
	{
		//board button 
		//using panel to fit the button inside
		//the button has no size thus 
		noMove = 0;
		Board.initialize(bsize);
		if( color == -1 ) 
		{
			status.setText("You are black , you go first");
		}
		else if( color == 1)
		{
			ReversiRule.canIgo(Board.BLACK);
			AI.go(Board.BLACK, AI_level);
			Board.printBoard();
			//status.setText("You are white , now is not your turn");
			status.setText("white " + Board.whiteCount + " vs black " + Board.blackCount);
		}
		else
		{
			status.setText("Please pick a colour to start");
			black.setVisible(true);
			white.setVisible(true);
			undo.setEnabled(false);
			restart.setEnabled(false);
		}
		
		for( int i = 0 ; i < bsize ; i++)
		{
			for( int j = 0 ; j < bsize ; j++ )
			{
				if( Board.board[i][j] == Board.BLACK ) boardc[i][j].setBackground( Color.BLACK);
				else if(Board.board[i][j] == Board.WHITE ) boardc[i][j].setBackground( Color.WHITE);
				else 
				{
					boardc[i][j].setBackground(Color.green);
					boardc[i][j].setEnabled(false);
				}
			}
		}
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
				restart.setEnabled(true);
				undo.setEnabled(true);
				
				if( noMove == 2)
				{
					status.setText("Game is over with white " + Board.whiteCount + " vs black " + Board.blackCount);
					break;
				}
				
				if( ReversiRule.canIgo( color ) == false)
				{
					noMove++;
				}
				else
				{
					UpdateWhereCanGo(myColor);
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
					
					noMove = 0;
					ReversiRule.goToThis();
					ReversiRule.go(x, y, color );
				}
				
				UpdateGameBoard();
				status.setText("white " + Board.whiteCount + " vs black " + Board.blackCount);
				
				restart.setEnabled(false);
				undo.setEnabled(false);
				
				try
				{
					Thread.sleep(1000);
				}
				catch(InterruptedException ex)
				{
					ex.printStackTrace();
				}
				
				Board.printBoard();
				
				if( noMove == 2)
				{
					status.setText("Game is over with white " + Board.whiteCount + " vs black " + Board.blackCount);
					break;
				}
				
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
	
	private void UpdateGameBoard(){
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
				else
				{
					boardc[i][j].setEnabled(false);
					boardc[i][j].setBackground(Color.green);
				}
				
			}
		}
	}
	
	private void UpdateWhereCanGo(int color){
		ReversiRule.checkWhereCanMove(color);
		for(Position pos : Board.possiblePos)
		{
			boardc[pos.getX()][pos.getY()].setEnabled(true);
			boardc[pos.getX()][pos.getY()].setBackground( Color.pink );
		}
	}
	
	public void actionPerformed( ActionEvent e )
	{
		command = e.getActionCommand();
		System.out.println("asd");
		
		if( command.equals("RESTART") )
		{
			System.out.println("Game restarted");
			color = 0;
			startGame();
		}
		else if( command.equals("UNDO") ) 
		{
			ReversiRule.undo();
			UpdateGameBoard();
			UpdateWhereCanGo(myColor);
			
		}else if( command.equals("Black") ) 
		{
			myColor = Board.BLACK;
			black.setVisible(false);
			white.setVisible(false);
			System.out.println(command);
			color = -1;
			startGame();
			Thr walau = new Thr();
			walau.start();
		}
		else if( command.equals("White") )
		{
			myColor = Board.WHITE;
			white.setVisible(false);
			black.setVisible(false);
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
