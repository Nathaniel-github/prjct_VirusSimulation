import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.concurrent.ThreadLocalRandom;

import javax.swing.*;

public class Person implements ActionListener{
	
	private boolean infected;
	private boolean removed;
	private boolean symptomatic;
	private boolean inHub = false;
	private boolean quarantined = false;
	
	private Timer infectionTimer;
	private Timer hubTimer;
	
	private int x;
	private int y;
	private int homeX;
	private int homeY;
	private int originX;
	private int originY;
	private int symptomChance;
	private int testChance;
	
	private double scaleFactor;
	
	private ImageIcon displayImage;
	
	public Person(int xCord, int yCord, double duration, int hubDuration, int sympChance, int testingChance) {
		
		infected = false;
		removed = false;
		symptomatic = true;
		
		x = xCord;
		y = yCord;
		scaleFactor = 40;
		symptomChance = sympChance;
		testChance = testingChance;
		
		infectionTimer = new Timer((int)(duration * 1000), this);
		hubTimer = new Timer(hubDuration, this);
		
		displayImage = new ImageIcon("SusceptibleImage.png");
		displayImage = new ImageIcon(displayImage.getImage().getScaledInstance((int)(displayImage.getIconWidth() / scaleFactor), (int)(displayImage.getIconHeight() / scaleFactor), Image.SCALE_DEFAULT));
	}
	
	public void setInfection() {
		
		if (!removed && !infected) {
			infected = true;
			
			displayImage = new ImageIcon("InfectedImage.png");
			displayImage = new ImageIcon(displayImage.getImage().getScaledInstance((int)(displayImage.getIconWidth() / scaleFactor), (int)(displayImage.getIconHeight() / scaleFactor), Image.SCALE_DEFAULT));
			
			if (ThreadLocalRandom.current().nextInt(0, 100) < symptomChance) {
				
				symptomatic = false;
				
				displayImage = new ImageIcon("SymptomaticImage.jpg");
				displayImage = new ImageIcon(displayImage.getImage().getScaledInstance((int)(displayImage.getIconWidth() / scaleFactor), (int)(displayImage.getIconHeight() / scaleFactor), Image.SCALE_DEFAULT));
				
			}
			
			if (symptomatic && ThreadLocalRandom.current().nextInt(0, 100) < testChance) {
				
				quarantined = true;
				
				originX = x;
				originY = y;
				
			}
			
			infectionTimer.start();
		}
		
	}
	
	public void setFirstInfection() {
		
		if (!removed && !infected) {
			infected = true;
			
			displayImage = new ImageIcon("InfectedImage.png");
			displayImage = new ImageIcon(displayImage.getImage().getScaledInstance((int)(displayImage.getIconWidth() / scaleFactor), (int)(displayImage.getIconHeight() / scaleFactor), Image.SCALE_DEFAULT));
			
			if (ThreadLocalRandom.current().nextInt(0, 100) < symptomChance) {
	
				symptomatic = false;
				
				displayImage = new ImageIcon("SymptomaticImage.jpg");
				displayImage = new ImageIcon(displayImage.getImage().getScaledInstance((int)(displayImage.getIconWidth() / scaleFactor), (int)(displayImage.getIconHeight() / scaleFactor), Image.SCALE_DEFAULT));
				
			}
			
			infectionTimer.start();
		}
		
	}
	
	public boolean isInfected() {
		
		return infected;
		
	}
	
	public void shiftX(int shift) {
		
		x += shift;
		
	}
	
	public void setX(int xCord) {
		
		x = xCord;
		
	}
	
	public void shiftY(int shift) {
		
		y += shift;
		
	}
	
	public void setY(int yCord) {
		
		y = yCord;
		
	}
	
	public boolean getSymptomatic() {
		
		return symptomatic;
		
	}
	
	public ImageIcon getCurrentImage() {
		
		return displayImage;
		
	}
	
	public boolean getInfected() {
		
		return infected;
		
	}
	
	public boolean getQuarantined() {
		
		return quarantined;
		
	}
	
	public boolean getRemoved() {
		
		return removed;
		
	}
	
	public int getX() {
		
		return x;
		
	}
	
	public int getY() {
		
		return y;
		
	}
	
	public void travelToHub(boolean hub, double hubChance) {
		
		if (hub && !quarantined) {
			
			if (ThreadLocalRandom.current().nextInt(0, 1000) < hubChance && !inHub) {
				
				homeX = x;
				homeY = y;
				
				setX(720);
				setY(415);
				
				inHub = true;
				
				hubTimer.start();
			
			}
			
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Timer theClock = (Timer)e.getSource();
		
		if (theClock.equals(infectionTimer)) {
			
			infectionTimer.stop();
			
			infected = false;
			removed = true;
			
			displayImage = new ImageIcon("RemovedImage.png");
			displayImage = new ImageIcon(displayImage.getImage().getScaledInstance((int)(displayImage.getIconWidth() / scaleFactor), (int)(displayImage.getIconHeight() / scaleFactor), Image.SCALE_DEFAULT));
			
			if (quarantined) {
				setX(originX);
				setY(originY);
			}
			
		} else if (theClock.equals(hubTimer)) {
			
			setX(homeX);
			setY(homeY);
			
			hubTimer.stop();
			
			inHub = false;
			
		}
		
	}
	
}
