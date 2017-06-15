package reversi;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class WindowMultiGame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int bsize;
	private String IpAddress, temp;

	JButton btnSurrender = new JButton("");
	JButton btnBlack = new JButton("");
	JButton btnWhite = new JButton("");
	JButton btnStart = new JButton("");
	JButton board[][];
	JPanel gameboard;
	ImageIcon imgBlack = new ImageIcon("Resources/BtnBlack.png");
	ImageIcon imgWhite = new ImageIcon("Resources/BtnWhite.png");
	ImageIcon imgStart = new ImageIcon("Resources/BtnStart.png");
	ImageIcon imgSurrender = new ImageIcon("Resources/BtnSurrender.png");
	

	private int myColor, yourColor;
	private int boardc[][]; 
	private final int enableColor = 2;

	public WindowMultiGame(String inpIP, String usrType) {

		// init data
		bsize = 8;
		IpAddress = inpIP;

		// variables
		board = new JButton[bsize][bsize];
		gameboard = new JPanel();
		ImageIcon icon = new ImageIcon("Resources/MultiGame.png");
		JLabel imgMain = new JLabel(icon);

		// init window form
		setVisible(true);
		setSize(805, 600);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // set window form to center screen
		setResizable(false);
		setTitle("Host");

		// init button surrender
		btnSurrender.setLocation(530, 496);
		btnSurrender.setSize(260, 64);
		btnSurrender.setIcon(imgSurrender);
		btnSurrender.setHorizontalTextPosition(0);
		btnSurrender.setActionCommand("SURRENDER");
		btnSurrender.addActionListener(this);
		
		// init label (frame background)
		imgMain.setSize(800, 600);

		// init gameboard (command = B'i''j')
		gameboard.setLayout(new GridLayout(bsize, bsize));
		for (int i = 0; i < bsize; i++) {
			for (int j = 0; j < bsize; j++) {
				String buttonname = "B" + i + j;
				board[i][j] = new JButton(buttonname);
				board[i][j].setEnabled(false);
				board[i][j].addActionListener(this);
				board[i][j].setBackground(Color.green);
				board[i][j].setActionCommand("BOARD" + buttonname);
				gameboard.add(board[i][j]);
			}
		}
		gameboard.setSize(bsize * 40, bsize * 40);
		gameboard.setLocation((800 - bsize * 40) / 2, 100);
		add(gameboard);

		// add controls to window
		UserType(usrType);
		add(btnSurrender);
		add(imgMain);
		initialize();
	}
	
	public void UserType(String inp) {
		if (inp.equals("HOST")) {
			// init button black
			btnBlack.setLocation(10, 496);
			btnBlack.setSize(250, 64);
			btnBlack.setIcon(imgBlack);
			btnBlack.setHorizontalTextPosition(0);
			btnBlack.setActionCommand("BLACK");
			btnBlack.addActionListener(this);
			add(btnBlack);

			// init button white
			btnWhite.setLocation(270, 496);
			btnWhite.setSize(250, 64);
			btnWhite.setIcon(imgWhite);
			btnWhite.setHorizontalTextPosition(0);
			btnWhite.setActionCommand("WHITE");
			btnWhite.addActionListener(this);
			add(btnWhite);
		}
		else if (inp.equals("CLIENT")) {
			// init button start
			btnStart.setLocation(10, 496);
			btnStart.setSize(500, 64);
			btnStart.setIcon(imgStart);
			btnStart.setHorizontalTextPosition(0);
			btnStart.setActionCommand("START");
			btnStart.addActionListener(this);			
			add(btnStart);
		}
	}

	private void initialize(){
		Board.initialize();
		Board.history.add(new Position(-1, -1)); //err... to avoid crash when fisrt go // history.size() cannot be 0
		boardc = Board.board.clone();
		UpdateBoardBackground();
	}
	
	public void startGame(Color colorr) {
		int color = colorr == Color.black ? Board.BLACK : Board.WHITE;
		System.out.println("Starting Game...");
		if (color == Board.BLACK) {
			System.out.println("Host - Black");
			LAN.Read();
			LAN.Write("WHITE", IpAddress);
			SetBoardEnable(true);
			UpdateWhereCanGo();
		} else if (color == Board.WHITE) {
			System.out.println("Host - White");
			LAN.Read();
			LAN.Write("BLACK", IpAddress);
			//Read Set************************
			Thread ReadData = new Thread() {
				@Override
				public void run() {
					temp = LAN.Read();
					UpdateGameBoard(temp);
				}
			};
			ReadData.start();
			SetBoardEnable(true);
		}
	}
	
	public void startGame() {
		LAN.Write("CONNECTED", IpAddress);
		String temp = LAN.Read();
		if (temp.equals("WHITE")) {
			temp = LAN.Read();
			SetBoardEnable(true);
			myColor = Board.WHITE;
			yourColor = Board.BLACK;
			UpdateGameBoard(temp);
		}
		if (temp.equals("BLACK")) {
			SetBoardEnable(true);
			myColor = Board.BLACK;
			yourColor = Board.WHITE;
			UpdateWhereCanGo();
		}
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("BLACK")) {
			btnBlack.setVisible(false);
			btnWhite.setVisible(false);
			myColor = Board.BLACK;
			yourColor = Board.WHITE;
			startGame(parseColor(myColor));
		} 
		else if (command.equals("WHITE")) {
			btnBlack.setVisible(false);
			btnWhite.setVisible(false);
			myColor = Board.WHITE;
			yourColor = Board.BLACK;
			startGame(parseColor(myColor));
		} 
		else if (command.substring(0, 5).equals("BOARD")) {
			UpdateGameBoard(((JButton) e.getSource()).getActionCommand().substring(5));
			LAN.Write(((JButton) e.getSource()).getActionCommand().substring(5), IpAddress);
			//Read Set************************
			Thread ReadData = new Thread() {
				@Override
				public void run() {
					temp = LAN.Read();
					UpdateGameBoard(temp);
					UpdateWhereCanGo();
					
				}
			};
			ReadData.start();
			//**********************************
		} 
		else if (command.equals("START")) {
			btnStart.setVisible(false);
			startGame();
		}
		else if (command.equals("SURRENDER")) {

		}
	}

	private void UpdateGameBoard(String inp) {
		System.out.println("SERVER UPDATE: " + inp);
		int x = Integer.parseInt(inp.substring(1, 2));
		int y = Integer.parseInt(inp.substring(2));
		
		ReversiRule.go(x, y, myColor);
		Board.printBoard();
		boardc = Board.board.clone();
		UpdateBoardBackground();
	}

	private void UpdateBoardBackground(){
		for(int i = 0; i < bsize; ++i)
			for(int j = 0; j < bsize; ++j)
				board[i][j].setBackground(parseColor(boardc[i][j]));
	}
	
	private void UpdateWhereCanGo(){
		ReversiRule.canIgo(myColor);
		for(Position pos : Board.possiblePos)
			boardc[pos.getX()][pos.getY()] = enableColor;
		UpdateBoardBackground();
	}

	private void SetBoardEnable(Boolean inp) {
		for (int i = 0; i < bsize; i++) {
			for (int j = 0; j < bsize; j++) {
				board[i][j].setEnabled(inp);
			}
		}
	}

	Color parseColor(int color){
		if(color == Board.BLACK)
			return Color.black;
		else if(color == Board.WHITE)
			return Color.white;
		else if(color == enableColor)
			return Color.pink;
		else
			return Color.green;
	}
	
	
}