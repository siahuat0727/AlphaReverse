package reversi;

import javax.swing.*;

import java.awt.Color;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.util.Scanner;

public class ScoreBoard extends JFrame implements ActionListener {

//	public static void main(String args[]) {
//		Board.initialize();
//		new ScoreBoard(Board.BLACK);
//	}

	public ScoreBoard(int color) {

		JFrame frame = new JFrame();
		JButton btnRestart = new JButton("");
		JButton btnExit = new JButton("");
		JLabel lblPlayer = new JLabel("Player");
		JLabel lblAI = new JLabel("AI");
		
		ImageIcon icon = new ImageIcon("Resources/MultiGame.png");

		ImageIcon imgRestart = new ImageIcon("Resources/BtnRestart.png");
		ImageIcon imgExit = new ImageIcon("Resources/BtnExit.png");
		
		JLabel imgMain = new JLabel(icon);

		// int playerCount = color;
		int playerCount = 0;
		int aiCount = 0;
		int player = color;
		int ai;

		String player_result = "";
		String ai_result = "";

		if (player == Board.WHITE) {
			playerCount = Board.whiteCount;
			aiCount = Board.blackCount;
		}

		else if (player == Board.BLACK) {
			playerCount = Board.blackCount;
			aiCount = Board.whiteCount;
		}

		try {
			PrintWriter writer = new PrintWriter(new FileOutputStream(
					"result_player.txt", true));
			writer.println(Integer.toString(playerCount));

			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Scanner scanner = new Scanner(new FileInputStream(
					"result_player.txt"));
			while (scanner.hasNextLine()) {
				player_result += scanner.nextLine();
				player_result = player_result + "\n";
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			PrintWriter writer = new PrintWriter(new FileOutputStream(
					"result_ai.txt", true));
			writer.println(Integer.toString(aiCount));

			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Scanner scanner = new Scanner(new FileInputStream("result_ai.txt"));
			while (scanner.hasNextLine()) {
				ai_result += scanner.nextLine();
				ai_result = ai_result + "\n";
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		JTextArea player_field = new JTextArea(20, 25);
		player_field.setLocation(61, 100);
		player_field.setSize(player_field.getPreferredSize());
		player_field.setText(player_result);
//		player_field.setForeground(Color.green);
//		player_field.setBackground(Color.black);

		JTextArea ai_field = new JTextArea(20, 25);
		ai_field.setLocation(464, 100);
		ai_field.setSize(ai_field.getPreferredSize());
		ai_field.setText(ai_result);
//		ai_field.setForeground(Color.green);
//		ai_field.setBackground(Color.black);

		frame.setSize(805, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null); // set window form to center screen
		frame.setResizable(false);
		frame.setTitle("Result");

		btnRestart.setLocation(76, 490);
		btnRestart.setSize(250, 64);
		btnRestart.setHorizontalTextPosition(0);
		btnRestart.setActionCommand("Restart");
		btnRestart.addActionListener(this);
		btnRestart.setIcon(imgRestart);

		btnExit.setLocation(479, 490);
		btnExit.setSize(250, 64);
		btnExit.setHorizontalTextPosition(0);
		btnExit.setActionCommand("Exit");
		btnExit.addActionListener(this);
		btnExit.setIcon(imgExit);

		lblPlayer.setLocation(175, 50);
		lblPlayer.setSize(200, 40);
		lblPlayer.setHorizontalTextPosition(0);
		lblPlayer.setForeground(Color.green);

		lblAI.setLocation(603, 50);
		lblAI.setSize(200, 40);
		lblAI.setHorizontalTextPosition(0);
		lblAI.setForeground(Color.green);

		// btnRefresh.addActionListener(this);
		// btnExit.addActionListener(this);

		// init label (frame background)
		imgMain.setSize(800, 600);
		
		frame.setLayout(null);
		frame.add(btnRestart);
		frame.add(btnExit);
		frame.add(lblPlayer);
		frame.add(lblAI);
		frame.add(player_field);
		frame.add(ai_field);
		frame.setVisible(true);
		frame.add(imgMain);
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if (command.equals("Restart")) {
			setVisible(false);
			//WindowMain frm = new WindowMain();
			WindowMain.main(new String[1]);
		}
	

		else if (command.equals("Exit")) {
			System.exit(0);
		}
	}
}
