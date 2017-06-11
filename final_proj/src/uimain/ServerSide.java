package uimain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.net.*;
import java.io.*;

public class ServerSide extends JFrame implements ActionListener
{
	private int bsize;
	
	//button included in this ui
	//undo , restart , surrender , pause
					
	JButton undo = new JButton("UNDO");
	JButton restart = new JButton("RESTART");
	JButton surrender = new JButton("SURRENDER");
	JButton black = new JButton("Choose Black");
	JButton white = new JButton("Choose White");
	
	public ServerSide()
	{
		//System.out.println("size is " + size + " diff is " + difficulty );
		bsize = 8;
		
		setSize(800,600);
		setVisible(true);
		setLayout(null);
		
		undo.setSize(200, 80);
		restart.setSize(200, 80);
		surrender.setSize(200, 80);
		black.setSize(200, 40);
		white.setSize(200, 40);
		
		undo.setLocation(0,0);
		restart.setLocation(200,0);
		surrender.setLocation(400,0);
		black.setLocation(600,0);
		white.setLocation(600,40);
		
		add(undo);
		add(restart);
		add(surrender);
		add(black);
		add(white);
		
		black.addActionListener(this);
		white.addActionListener(this);
		
		undo.setEnabled(false);
		restart.setEnabled(false);
		surrender.setEnabled(false);
		
		undo.setBackground( Color.red );
		restart.setBackground( Color.red );
		surrender.setBackground( Color.red );
	}
	
	public void startServer( int col )
	{
		System.out.println("Creating and waiting for connection");
		JButton board[][] = new JButton[bsize][bsize];
		JPanel gameboard = new JPanel();
		
		if( col == 1 ) System.out.println(" Serverside is white ");
		else System.out.println(" Serverside is black ");
		
		gameboard.setLayout(new GridLayout(bsize,bsize));
		
		//board button 
		//using panel to fit the button inside
		//the button has no size thus 
		
		
		
		int i = 0 , j = 0 ;
		for( i = 0 ; i < bsize ; i++)
		{
			for( j = 0 ; j < bsize ; j++ )
			{
				//for each button their command is B'i''j'
				String buttonname = "B" + i + j;

				board[i][j] = new JButton(buttonname);
				

				gameboard.add(board[i][j]);
			}
		}
		
		//panel will auto resize and relocate
		gameboard.setSize(bsize*40, bsize*40);
		gameboard.setLocation((800 - bsize*40)/2 , 100);
		add(gameboard);
		
		try
		{
			ServerSocket serverSock = new ServerSocket(8000);
			Socket conSock = serverSock.accept();
			
			BufferedReader clientInput = new BufferedReader( new InputStreamReader(conSock.getInputStream()));
			DataOutputStream clientOutput= new DataOutputStream(conSock.getOutputStream());
			
			String tunnel = clientInput.readLine();
			if(tunnel.equals("connected")) clientOutput.writeBytes(Integer.toString(turn));
			
			for( i = 0 ; i < bsize ; i++ )
			{
				for( j = 0 ; j < bsize ; j++ )
				{
					board[i][j].addActionListener( new ActionListener() { 
						public void actionPerformed(ActionEvent e )
						{
							try
							{
								clientOutput.writeBytes(e.getActionCommand());
								turn = 1;
							}
							catch ( IOException ex )
							{
								System.out.println("board button error ");
							}
						}
					});
					
				}
			}
			
			//while(true){}
			if( turn == 1 )
			{
				for( i = 0 ; i < bsize ; i++)
				{
					for( j = 0 ; j < bsize ; j++ )
					{
						board[i][j].setEnabled(false);
						if( board[i][j].getBackground() != Color.black || board[i][j].getBackground() != Color.white ) board[i][j].setBackground( Color.red );
					}
				}
				
				String clientText = clientInput.readLine();
				System.out.println(clientText);
				
				int x = Integer.parseInt( clientText.substring(1, 2));
				int y = Integer.parseInt( clientText.substring(2));
				
				if( col == 1 ) board[x][y].setBackground( Color.black );
				else board[x][y].setBackground( Color.white );
				turn = 0 ;
				//continue;
			}
			else if( turn == 0 )
			{
				for( i = 0 ; i < bsize ; i++)
				{
					for( j = 0 ; j < bsize ; j++ )
					{
						board[i][j].setEnabled(true);
						if( board[i][j].getBackground() != Color.black || board[i][j].getBackground() != Color.white ) board[i][j].setBackground( Color.blue );
					}
				}
				
				
			}
		}
		catch( IOException e)
		{
			System.out.println("lol" + e.getMessage());
		}
	}
	
	public int turn = 0 ;
	
	public void actionPerformed( ActionEvent e )
	{
		String command = e.getActionCommand();
		System.out.println( command );
		
		if( command.equals("Choose Black"))
		{
			turn = 0;
			int col = 0;
			
			
			undo.setEnabled(true);
			restart.setEnabled(true);
			surrender.setEnabled(true);
			undo.setBackground( Color.blue );
			restart.setBackground( Color.blue );
			surrender.setBackground( Color.blue );
			
			black.setEnabled(false);
			white.setEnabled(false);
			black.setBackground( Color.red );
			white.setBackground( Color.red );
			System.out.println( turn + " " + col );
			startServer( col );
			
		}
		else if( command.equals("Choose White"))
		{
			turn = 1;
			int col = 1 ;
			
			undo.setEnabled(true);
			restart.setEnabled(true);
			surrender.setEnabled(true);
			undo.setBackground( Color.blue );
			restart.setBackground( Color.blue );
			surrender.setBackground( Color.blue );
			
			black.setEnabled(false);
			white.setEnabled(false);
			black.setBackground( Color.red );
			white.setBackground( Color.red );
			System.out.println( turn + " " + col );
			startServer( col );
		}
		
	}
	
}
