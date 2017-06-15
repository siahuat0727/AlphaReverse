package uimain;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class WindowMultiServer extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int bsize;
	private String IpAddress, temp;

	JButton btnSurrender;
	JButton btnBlack;
	JButton btnWhite;
	JButton board[][];
	JPanel gameboard;
	Color myColor, yourColor;

	public WindowMultiServer(String inpIP) {
		// init data
		bsize = 8;
		IpAddress = inpIP;

		// variables
		btnSurrender = new JButton("Surrender");
		btnBlack = new JButton("Choose Black");
		btnWhite = new JButton("Choose White");
		board = new JButton[bsize][bsize];
		gameboard = new JPanel();

		// add controls to listener
		btnSurrender.addActionListener(this);
		btnBlack.addActionListener(this);
		btnWhite.addActionListener(this);

		// init main window
		setSize(800, 600);
		setVisible(true);
		setLayout(null);

		// init button surrender
		btnSurrender.setLocation(400, 0);
		btnSurrender.setSize(200, 80);
		btnSurrender.setEnabled(false);
		btnSurrender.setBackground(Color.red);
		btnSurrender.setActionCommand("SURRENDER");

		// init button black
		btnBlack.setLocation(600, 0);
		btnBlack.setSize(200, 40);
		btnBlack.setActionCommand("BLACK");

		// init button white
		btnWhite.setLocation(600, 40);
		btnWhite.setSize(200, 40);
		btnWhite.setActionCommand("WHITE");

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
		add(btnSurrender);
		add(btnBlack);
		add(btnWhite);
	}

	public void startGame(Color color) {
		System.out.println("Starting Game...");
		if (color == Color.black) {
			System.out.println("Host - Black");
			LAN.Read();
			LAN.Write("WHITE", IpAddress);
			SetBoardEnable(true);
		} else if (color == Color.white) {
			System.out.println("Host - White");
			LAN.Read();
			LAN.Write("BLACK", IpAddress);
			LAN.Read();
			SetBoardEnable(true);
		}
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("BLACK")) {
			btnBlack.setEnabled(false);
			btnWhite.setEnabled(false);
			myColor = Color.black;
			yourColor = Color.white;
			startGame(myColor);
		} else if (command.equals("WHITE")) {
			btnBlack.setEnabled(false);
			btnWhite.setEnabled(false);
			myColor = Color.white;
			yourColor = Color.black;
			startGame(myColor);
		} else if (command.substring(0, 5).equals("BOARD")) {
			((JButton) e.getSource()).setBackground(myColor);
			LAN.Write(((JButton) e.getSource()).getActionCommand().substring(5), IpAddress);
			SetBoardEnable(false);
			temp = LAN.Read();
			UpdateGameBoard(temp);
			SetBoardEnable(true);
		} else if (command.equals("SURRENDER")) {

		}
	}

	private void UpdateGameBoard(String inp) {
		System.out.println("SERVER UPDATE: " + inp);
		int x = Integer.parseInt(inp.substring(1, 2));
		int y = Integer.parseInt(inp.substring(2));

		board[x][y].setBackground(yourColor);
	}

	private void SetBoardEnable(Boolean inp) {
		for (int i = 0; i < bsize; i++) {
			for (int j = 0; j < bsize; j++) {
				board[i][j].setEnabled(inp);
			}
		}
	}
}