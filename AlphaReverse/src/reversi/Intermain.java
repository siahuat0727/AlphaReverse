package reversi;

import javax.swing.JButton;
import javax.swing.JFrame;


public class Intermain {

	public static void main(String[] args) {
		JFrame frame= new JFrame();
		frame.setSize(800,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton single = new JButton("Single Player");
		JButton multi = new JButton("Multi Player");
		
		//MainEventListener mblistener = new MainEventListener();
		single.addActionListener(new MainEventListener());
		multi.addActionListener(new MainEventListener());
		
		
		single.setLocation(250,300);
		single.setSize(300,40);

		multi.setLocation(250,350);
		multi.setSize(300,40);

		frame.setLayout(null);

		frame.add(single);
		frame.add(multi);

		frame.setVisible(true);
		

	}

}
