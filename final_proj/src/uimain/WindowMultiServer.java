package uimain;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import java.net.*;
import java.io.*;

public class WindowMultiServer extends JFrame implements ActionListener {

	private int bsize;

	JButton btnUndo;
	JButton btnRestart;
	JButton btnSurrender;
	JButton btnBlack;
	JButton btnWhite;
	JButton board[][];
	JPanel gameboard;

	public WindowMultiServer() {
		// init data
		bsize = 8;

		// variables
		btnUndo = new JButton("Undo");
		btnRestart = new JButton("Restart");
		btnSurrender = new JButton("Surrender");
		btnBlack = new JButton("Choose Black");
		btnWhite = new JButton("Choose White");
		board = new JButton[bsize][bsize];
		gameboard = new JPanel();

		// add controls to listener
		btnUndo.addActionListener(this);
		btnRestart.addActionListener(this);
		btnSurrender.addActionListener(this);
		btnBlack.addActionListener(this);
		btnWhite.addActionListener(this);

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
		add(btnUndo);
		add(btnRestart);
		add(btnSurrender);
		add(btnBlack);
		add(btnWhite);
	}

	public void startGame(int col) {
		System.out.println("Starting Game...");
		if (col == 0) {
			System.out.println("Host - Black");
			LAN.Read();
			LAN.Write("WHITE");
			SetBoardEnable(true);
		} else if (col == 1) {
			System.out.println("Host = White");
			LAN.Read();
			LAN.Write("BLACK");
			LAN.Read();
			SetBoardEnable(true);
		}
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("BLACK")) {
			startGame(0);
		} else if (command.equals("WHITE")) {
			startGame(1);
		} else if (command.substring(0, 5).equals("BOARD")) {
			((JButton) e.getSource()).setBackground(Color.white);
			LAN.Write(((JButton) e.getSource()).getActionCommand().substring(5));
			SetBoardEnable(false);
			String temp = LAN.Read();
			UpdateGameBoard(temp);
			SetBoardEnable(true);
		}
	}
	
	private void UpdateGameBoard(String inp) {
		System.out.println("UPDATE: " + inp);
	}
	
	private void SetBoardEnable(Boolean inp) {
		for (int i = 0; i < bsize; i++) {
			for (int j = 0; j < bsize; j++ ) {		
				board[i][j].setEnabled(inp);
			}
		}
	}
	

}