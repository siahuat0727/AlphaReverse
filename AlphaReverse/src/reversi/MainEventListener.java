package reversi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainEventListener implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
		System.out.println(command);
		
		if( command.equals("Single Player"))
		{
			SingleWindow check = new SingleWindow();
		}
		else if( command.equals("Multi Player"))
		{
			MultiWindow check = new MultiWindow();
		}
		
		
	}
	
	
}
