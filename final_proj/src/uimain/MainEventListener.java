package uimain;

import java.awt.event.*;

public class MainEventListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand(); //get button command
		if( command.equals("Single Player")) {
			SingleWindow check = new SingleWindow();
			check.setLocationRelativeTo(null);
		}
		else if( command.equals("Multi Player")) {
			MultiWindow check = new MultiWindow();
			check.setLocationRelativeTo(null);
		}	
	}	
}