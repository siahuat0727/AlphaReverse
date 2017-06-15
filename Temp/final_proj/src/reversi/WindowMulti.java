package reversi;

import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.net.InetAddress;

public class WindowMulti extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JButton btnHost = new JButton("");
	JButton btnClient = new JButton("");
	JLabel lblMyIp = new JLabel("My IP Address:");
	JLabel lblYourIp = new JLabel("Opponent's IP Address:");
	JTextField txtMyIp = new JTextField("");
	JTextField txtYourIp = new JTextField("");

	public WindowMulti() {
		// variables
		ImageIcon icon = new ImageIcon("Resources/Multi.png");
		JLabel imgMain = new JLabel(icon);
		ImageIcon imgHost = new ImageIcon("Resources/BtnHost.png");
		ImageIcon imgPlayer = new ImageIcon("Resources/BtnPlayer.png");

		// init window form
		setVisible(true);
		setSize(805, 600);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // set window form to center screen
		setResizable(false);
		setTitle("Multi Player");

		// init button host
		btnHost.setSize(400, 64);
		btnHost.setLocation(202, 277);
		btnHost.setIcon(imgHost);
		btnHost.setHorizontalTextPosition(0);
		btnHost.setActionCommand("HOST");
		btnHost.addActionListener(this);

		// init button client
		btnClient.setSize(400, 64);
		btnClient.setLocation(202, 354);
		btnClient.setIcon(imgPlayer);
		btnClient.setHorizontalTextPosition(0);
		btnClient.setActionCommand("CLIENT");
		btnClient.addActionListener(this);

		// init label my ip address
		lblMyIp.setLocation(202, 450);
		lblMyIp.setForeground(Color.green);
		lblMyIp.setFont(lblMyIp.getFont().deriveFont(24.0f));
		lblMyIp.setSize(300, 50);

		// init label your ip address
		lblYourIp.setLocation(103, 480);
		lblYourIp.setForeground(Color.green);
		lblYourIp.setFont(lblYourIp.getFont().deriveFont(24.0f));
		lblYourIp.setSize(300, 50);

		// init textbox my ip address
		txtMyIp.setLocation(380, 456);
		txtMyIp.setSize(220, 30);
		txtMyIp.setFont(new Font("Arial Black", Font.BOLD, 18));
		txtMyIp.setEditable(false);
		txtMyIp.setHorizontalAlignment(0);
		txtMyIp.setBackground(Color.black);
		txtMyIp.setForeground(Color.green);
		try {
			txtMyIp.setText(InetAddress.getLocalHost().getHostAddress());
		} 
		catch (Exception e) {
			System.out.println("GET IP ERROR:" + e);
		}

		// init textbox your ip address
		txtYourIp.setLocation(380, 490);
		txtYourIp.setSize(220, 30);
		txtYourIp.setFont(new Font("Arial Black", Font.BOLD, 18));
		txtYourIp.setHorizontalAlignment(0);
		txtYourIp.setBackground(Color.black);
		txtYourIp.setForeground(Color.green);

		// init label (frame background)
		imgMain.setSize(800, 600);

		// add controls to form
		add(btnHost);
		add(btnClient);
		add(lblMyIp);
		add(lblYourIp);
		add(txtMyIp);
		add(txtYourIp);
		add(imgMain);
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand(); // get button command
		String ip = txtYourIp.getText();
		System.out.println(ip);
		if (command.equals("HOST")) {
			if (ip.equals("")) {
				WindowMultiServer game = new WindowMultiServer("localhost");
				game.setLocationRelativeTo(null);
			} 
			else {
				WindowMultiServer game = new WindowMultiServer(ip);
				game.setLocationRelativeTo(null);
			}
		} 
		else if (command.equals("CLIENT")) {
			if (ip.equals("")) {
				WindowMultiClient game = new WindowMultiClient("localhost");
				game.setLocationRelativeTo(null);
			} 
			else {
				WindowMultiClient game = new WindowMultiClient(ip);
				game.setLocationRelativeTo(null);
			}
		}
	}
}