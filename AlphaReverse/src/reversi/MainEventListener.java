package reversi;

import java.awt.event.*;

public class MainEventListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand(); //get button command
		if( command.equals("Single Player")) {
			WindowSingle check = new WindowSingle();
			check.setLocationRelativeTo(null);
		}
		else if( command.equals("Multi Player")) {
			WindowMulti check = new WindowMulti();
			check.setLocationRelativeTo(null);
		}	
	}	
}