import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		JFrame window = new JFrame("Virus Simulation");
	    window.setBounds(0, 0, 1440, 830);
	    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    InfectionPanel panel = new InfectionPanel();
	    panel.setBackground(Color.WHITE);
	    window.setBackground(Color.WHITE);
	    Container c = window.getContentPane();
	    c.add(panel);
	    window.setVisible(true);
	    
	    panel.startMovement();

	}

}
