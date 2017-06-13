package uimain;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import java.net.*;
import java.io.*;

public class WindowMultiServer extends JFrame implements ActionListener {
	
	private int bsize = 8;
	
	//button included in this ui
	//undo , restart , surrender , pause
					
	JButton btnUndo = new JButton("UNDO");
	JButton btnRestart = new JButton("RESTART");
	JButton btnSurrender = new JButton("SURRENDER");
	JButton btnBlack = new JButton("Choose Black");
	JButton btnWhite = new JButton("Choose White");

	JButton board[][] = new JButton[bsize][bsize];
	JPanel gameboard = new JPanel();
	
	public WindowMultiServer() {
		//System.out.println("size is " + size + " diff is " + difficulty );
		bsize = 8;
		
		setSize(800,600);
		setVisible(true);
		setLayout(null);
		
		btnUndo.setSize(200, 80);
		btnRestart.setSize(200, 80);
		btnSurrender.setSize(200, 80);
		btnBlack.setSize(200, 40);
		btnWhite.setSize(200, 40);
		
		btnUndo.setLocation(0,0);
		btnRestart.setLocation(200,0);
		btnSurrender.setLocation(400,0);
		btnBlack.setLocation(600,0);
		btnWhite.setLocation(600,40);
		
		add(btnUndo);
		add(btnRestart);
		add(btnSurrender);
		add(btnBlack);
		add(btnWhite);
		
		btnBlack.addActionListener(this);
		btnWhite.addActionListener(this);
		
		btnUndo.setEnabled(false);
		btnRestart.setEnabled(false);
		btnSurrender.setEnabled(false);
		
		btnUndo.setBackground( Color.red );
		btnRestart.setBackground( Color.red );
		btnSurrender.setBackground( Color.red );
		
		gameboard.setLayout(new GridLayout(bsize,bsize));
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
		
	}
	
	public void startServer( int col )
	{
		
		
		
		System.out.println("Creating and waiting for connection");
		
		if( col == 1 ) System.out.println(" Serverside is white ");
		else System.out.println(" Serverside is black ");
		
		
		
		
		//board button 
		//using panel to fit the button inside
		//the button has no size thus 
		
		
		
		
		
//		try
//		{
//			ServerSocket serverSock = new ServerSocket(8000);
//			Socket conSock = serverSock.accept();
//			
//			BufferedReader clientInput = new BufferedReader( new InputStreamReader(conSock.getInputStream()));
//			DataOutputStream clientOutput= new DataOutputStream(conSock.getOutputStream());
//			
//			String tunnel = clientInput.readLine();
//			if(tunnel.equals("connected")) clientOutput.writeBytes(Integer.toString(turn));
//			
//			for( i = 0 ; i < bsize ; i++ )
//			{
//				for( j = 0 ; j < bsize ; j++ )
//				{
//					board[i][j].addActionListener( new ActionListener() { 
//						public void actionPerformed(ActionEvent e )
//						{
//							try
//							{
//								clientOutput.writeBytes(e.getActionCommand());
//								turn = 1;
//							}
//							catch ( IOException ex )
//							{
//								System.out.println("board button error ");
//							}
//						}
//					});
//					
//				}
//			}
//			
//			//while(true){}
//			if( turn == 1 )
//			{
//				for( i = 0 ; i < bsize ; i++)
//				{
//					for( j = 0 ; j < bsize ; j++ )
//					{
//						board[i][j].setEnabled(false);
//						if( board[i][j].getBackground() != Color.black || board[i][j].getBackground() != Color.white ) board[i][j].setBackground( Color.red );
//					}
//				}
//				
//				String clientText = clientInput.readLine();
//				System.out.println(clientText);
//				
//				int x = Integer.parseInt( clientText.substring(1, 2));
//				int y = Integer.parseInt( clientText.substring(2));
//				
//				if( col == 1 ) board[x][y].setBackground( Color.black );
//				else board[x][y].setBackground( Color.white );
//				turn = 0 ;
//				//continue;
//			}
//			else if( turn == 0 )
//			{
//				for( i = 0 ; i < bsize ; i++)
//				{
//					for( j = 0 ; j < bsize ; j++ )
//					{
//						board[i][j].setEnabled(true);
//						if( board[i][j].getBackground() != Color.black || board[i][j].getBackground() != Color.white ) board[i][j].setBackground( Color.blue );
//					}
//				}
//				
//				
//			}
//		}
//		catch( IOException e)
//		{
//			System.out.println("lol" + e.getMessage());
//		}
		System.out.println("Done init board.");
		try {
			ServerSocket serverSock = new ServerSocket(8000);
			System.out.print("Server started...\n");
			while (true) {
				Socket cSock = serverSock.accept();
				Chat chat = new Chat(cSock);
				Thread chatthread = new Thread(chat);
				chatthread.start();
			}
		} catch (IOException e) {
			System.out.println("disconnected...");
		}
	}
	
	class Chat implements Runnable {
		private Socket socket;

		public Chat(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			try {
				BufferedReader clientInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				while (true) {
					String clientText = clientInput.readLine();
					ReceivedData(clientText);
					if (clientText.equals("bye"))
						break;
				}
				clientInput.close();
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public int turn = 0 ;
	
	private void ReceivedData(String data) {
		System.out.println("Received data from client. (" + data + ").");		
	}
	
	public void actionPerformed( ActionEvent e )
	{
		String command = e.getActionCommand();
		System.out.println( command );
		
		if( command.equals("Choose Black"))
		{
			turn = 0;
			int col = 0;
			
			
//			undo.setEnabled(true);
//			restart.setEnabled(true);
//			surrender.setEnabled(true);
//			undo.setBackground( Color.blue );
//			restart.setBackground( Color.blue );
//			surrender.setBackground( Color.blue );
//			
//			black.setEnabled(false);
//			white.setEnabled(false);
//			black.setBackground( Color.red );
//			white.setBackground( Color.red );
			System.out.println( turn + " " + col );
			startServer(col);
			
		}
		else if( command.equals("Choose White"))
		{
			turn = 1;
			int col = 1 ;
			
//			undo.setEnabled(true);
//			restart.setEnabled(true);
//			surrender.setEnabled(true);
//			undo.setBackground( Color.blue );
//			restart.setBackground( Color.blue );
//			surrender.setBackground( Color.blue );
//			
//			black.setEnabled(false);
//			white.setEnabled(false);
//			black.setBackground( Color.red );
//			white.setBackground( Color.red );
			System.out.println( turn + " " + col );
			startServer(col);
		}
		
	}
	
}
