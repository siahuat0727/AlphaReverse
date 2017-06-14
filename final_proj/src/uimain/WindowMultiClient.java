package uimain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WindowMultiClient extends JFrame implements ActionListener {

	private int bsize;

	JButton btnUndo;
	JButton btnRestart;
	JButton btnSurrender;
	JButton btnStart;
	JButton board[][];
	JPanel gameboard;

	public WindowMultiClient() {
		// init data
		bsize = 8;

		// variables
		btnUndo = new JButton("Undo");
		btnRestart = new JButton("Restart");
		btnSurrender = new JButton("Surrender");
		btnStart = new JButton("Start Game");
		board = new JButton[bsize][bsize];
		gameboard = new JPanel();

		// add controls to listener
		btnUndo.addActionListener(this);
		btnRestart.addActionListener(this);
		btnSurrender.addActionListener(this);
		btnStart.addActionListener(this);

		// init main window
		setSize(800, 600);
		setVisible(true);
		setLayout(null);

		// init button undo
		btnUndo.setLocation(0, 0);
		btnUndo.setSize(200, 80);
		btnUndo.setEnabled(false);
		btnUndo.setBackground(Color.red);
		btnUndo.setActionCommand("UNDO");

		// init button restart
		btnRestart.setLocation(200, 0);
		btnRestart.setSize(200, 80);
		btnRestart.setEnabled(false);
		btnRestart.setBackground(Color.red);
		btnRestart.setActionCommand("RESTART");

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
		add(btnUndo);
		add(btnRestart);
		add(btnSurrender);
		add(btnStart);
	}

	public void startGame() {
		LAN.Write("CONNECTED", "localhost");
		String temp = LAN.Read();
		if (temp.equals("WHITE")) {
			temp = LAN.Read();
			SetBoardEnable(true);
			UpdateGameBoard(temp);
		}
		if (temp.equals("BLACK")) {
			SetBoardEnable(true);
			UpdateGameBoard(temp);
		}
	}

	private void UpdateGameBoard(String inp) {
		System.out.println("UPDATE: " + inp);
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
		if (command.substring(0, 5).equals("BOARD")) {
			((JButton) e.getSource()).setBackground(Color.white);
			LAN.Write(((JButton) e.getSource()).getActionCommand().substring(5), "localhost");
			SetBoardEnable(false);
			String temp = LAN.Read();
			UpdateGameBoard(temp);
			SetBoardEnable(true);
		}
		else if (command.equals("START")) {
			startGame();
		}
	}
}