package reversi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MultiWindow extends JFrame implements ActionListener
{
	public MultiWindow()
	{
		setVisible(true);
		setSize(450,450);
		setLayout(null);
		
		JLabel diff = new JLabel(" Be the host or enter ip ? ");
		diff.setSize(400 , 80 );
		diff.setLocation(0, 0);
		
		JButton host = new JButton("I'm the host");
		host.setSize(300 , 80);
		host.setLocation(50, 100);
		
		JButton client = new JButton("I want to join other");
		client.setSize(300 , 80);
		client.setLocation(50, 200);
		
		/*JTextField name = new JTextField("Enter ip :" , 30);
		name.setLocation(50 , 300);
		name.setVisible(false);*/
		
		host.addActionListener(this);
		client.addActionListener(this);
		client.addActionListener(this);
		
		add(host);
		add(diff);
		add(client);
		//add(name);
		
		
	}
	
	public void actionPerformed( ActionEvent e )
	{
		String command = e.getActionCommand();
		
		if( command.equals("I'm the host"))
		{
			System.out.println("Host server creating");
			ServerSide game = new ServerSide();
		}
		
		else if( command.equals("I want to join other"))
		{
			JTextField name = new JTextField("Enter ip here:" , 30);
			name.setLocation(50 , 340);
			name.setSize(name.getPreferredSize());
			
			this.add(name);
			System.out.println("sohai");
			
			//String ip;
			ClientSide game = new ClientSide();
			game.startClient();
		}
		
		
	}
	
	
}