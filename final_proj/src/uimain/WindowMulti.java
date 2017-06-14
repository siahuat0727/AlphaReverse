package uimain;

import javax.swing.*;
import java.awt.event.*;

public class WindowMulti extends JFrame implements ActionListener {
	public WindowMulti() {
		//variables
		JButton btnHost = new JButton("I'm the host");
		JButton btnClient = new JButton("I want to join other");
		JTextField lblIp = new JTextField("Enter ip here:" , 30);
		
		//add controls to listener
		btnHost.addActionListener(this);
		btnClient.addActionListener(this);
		
		//init window form
		setVisible(true);
		setSize(450,450);
		setLayout(null);
		
		//init button host
		btnHost.setSize(300 , 80);
		btnHost.setLocation(50, 100);
		btnHost.setActionCommand("HOST");
		
		//init button client
		btnClient.setSize(300 , 80);
		btnClient.setLocation(50, 200);
		btnClient.setActionCommand("CLIENT");
		
		//init label ip address
		lblIp.setLocation(50 , 340);
		lblIp.setSize(lblIp.getPreferredSize());
		
		//add controls to form
		add(btnHost);
		add(btnClient);		
		this.add(lblIp);
	}
	
	public void actionPerformed( ActionEvent e ) {
		String command = e.getActionCommand(); //get button command
		if( command.equals("HOST")) {
			WindowMultiServer game = new WindowMultiServer();
			game.setLocationRelativeTo(null);
		}
		else if( command.equals("CLIENT")) {
			WindowMultiClient game = new WindowMultiClient();
			game.setLocationRelativeTo(null);
		}	
	}
}