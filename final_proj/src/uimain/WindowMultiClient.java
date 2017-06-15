package uimain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WindowMultiClient extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int bsize;
	private String IpAddress, temp;

	JButton btnSurrender;
	JButton btnStart;
	JButton board[][];
	JPanel gameboard;
	Color myColor, yourColor;

	public WindowMultiClient(String inpIp) {
		// init data
		bsize = 8;
		IpAddress = inpIp;

		// variables
		btnSurrender = new JButton("Surrender");
		btnStart = new JButton("Start Game");
		board = new JButton[bsize][bsize];
		gameboard = new JPanel();

		// add controls to listener
		btnSurrender.addActionListener(this);
		btnStart.addActionListener(this);

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
		btnStart.setLocation(600, 0);
		btnStart.setSize(200, 40);
		btnStart.setActionCommand("START");

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
		add(btnStart);
	}

	public void startGame() {
		LAN.Write("CONNECTED", IpAddress);
		String temp = LAN.Read();
		if (temp.equals("WHITE")) {
			temp = LAN.Read();
			myColor = Color.white;
			yourColor = Color.black;
			SetBoardEnable(true);
			UpdateGameBoard(temp);
		}
		if (temp.equals("BLACK")) {
			SetBoardEnable(true);
			myColor = Color.black;
			yourColor = Color.white;
			UpdateGameBoard(temp);
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

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("START")) {
			startGame();
		} else if (command.substring(0, 5).equals("BOARD")) {
			((JButton) e.getSource()).setBackground(myColor);
			LAN.Write(((JButton) e.getSource()).getActionCommand().substring(5), IpAddress);
			//Read Set************************
			Thread ReadData = new Thread() {
				@Override
				public void run() {
					temp = LAN.Read();
					UpdateGameBoard(temp);
				}
			};
			ReadData.start();
			//**********************************
		} else if (command.equals("SURRENDER")) {

		}
	}
}