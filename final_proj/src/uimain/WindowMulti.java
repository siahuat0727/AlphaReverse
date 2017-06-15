package uimain;

import javax.swing.*;
import java.awt.event.*;

public class WindowMulti extends JFrame implements ActionListener {
	
	JButton btnHost = new JButton("I'm the host");
	JButton btnClient = new JButton("I want to join other");
	JTextField lblIp = new JTextField("Enter ip here:" , 30);
	
	public WindowMulti() {
		//variables
		
		
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
		add(lblIp);
	}
	
	public void actionPerformed( ActionEvent e ) {
		String command = e.getActionCommand(); //get button command
		String ip = lblIp.getText().substring(14);
		//String ip = lblIp.getText().substring(14);
		System.out.println(ip);
		if( command.equals("HOST")) {
			if( ip.equals("") )
			{
				WindowMultiServer game = new WindowMultiServer("127.0.0.1");
				game.setLocationRelativeTo(null);
			}
			else
			{
				WindowMultiServer game = new WindowMultiServer(ip);
				game.setLocationRelativeTo(null);
			}
		}
		else if( command.equals("CLIENT")) {
			if( ip.equals("") )
			{
				WindowMultiClient game = new WindowMultiClient("127.0.0.1");
				game.setLocationRelativeTo(null);
			}
			else
			{
				WindowMultiClient game = new WindowMultiClient(ip);
				game.setLocationRelativeTo(null);
			}
		}	
	}
}