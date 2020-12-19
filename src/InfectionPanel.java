import javax.swing.*;

import java.util.concurrent.ThreadLocalRandom;

import java.awt.*;

public class InfectionPanel extends JPanel{
	
	// Try not to change these values but you can if you would like
	
	private final int POPULATION = 450; // You can change this values if you would like but if you do, keep the population a multiple of the number of rows or else the program won't work
	private final int ROWS = 15; //  You can change this values if you would like but if you do, keep the rows a factor of the population or else the program won't work
	
	// Change these values so you can see how different factors cause different outcomes
	
	private int chance = 20; // As a percent, this is the chance that someone in the infection radius gets infected
	private int speed = 40; // This value is how fast you want the simulation to go, the closer this value is to 0 the faster the simulation will go (please note that this factor has not been perfected so when faster simulations are run you may end up with an inaccurate simulation)
	private int lowParam = -5; // Max negative movement (should equal the highParam if you don't want gravitation towards a side of the screen), represents how much a person moves
	private int highParam = 5; // Max positive movement (should equal the lowParam if you don't want gravitation towards a side of the screen), represents how much a person moves
	private boolean hub = false; // Whether or not people go to a central gathering place
	private int hubChance = 1; // As a tenth of a percent, this is the chance that a random person goes to the central gathering place
	private int hubDuration = 10; // In milliseconds (represents minutes IRL), this is how long a person spends in the central gathering place
	private double duration = 21; // In seconds (represents days IRL), this is how long the virus lasts before the person becomes removed (either recovered or dead)
	private int infectionRadius = 20; // In pixels (represents inches IRL), this is how close people have to be nearby to have a chance of contracting the virus
	private int symptomChance = 0; // As a percent, this is the chance that someone could have the virus but show no symptoms
	private int testChance = 0; // As a percent, this is the chance of testing people with the virus and then quarantining them
	
	// Don't change these values
	
	private int width = 1400 - 2 * infectionRadius;
	private int height = 830;
	
	private Person [] allPeople = new Person[POPULATION];
	
	public InfectionPanel() {
		
		int count = 0;
		
		if (testChance == 0) {
			
			width = 1440;
			
		}
		
		hubDuration = (int)(hubDuration / 40.0 / (1.0 / speed));
		duration = (duration / 40.0 / (1.0 / speed));
		
		for (int k = 0; k < ROWS; k ++) {
			
			for (int i = 0; i < POPULATION / ROWS; i ++) {
				
				int startX = width / (POPULATION / ROWS);
				int startY = height / ROWS;
				
				allPeople[count] = new Person(startX * i + startX / 4, startY * k + startY / 5, duration, hubDuration, symptomChance, testChance);
				
				count ++;
				
			}
			
		}
		
		allPeople[ThreadLocalRandom.current().nextInt(0, POPULATION)].setFirstInfection();;
		
	}
	
	public void paintComponent(Graphics g) {
		
		g.drawLine(width, 0, width, height);
		
		for (int i = 0; i < POPULATION; i ++) {
			
			g.drawImage(allPeople[i].getCurrentImage().getImage(), allPeople[i].getX(), allPeople[i].getY(), this);
			
		}
		
		testInfection();
		
	}
	
	private void testInfection() {
		
		for (int i = 0; i < POPULATION; i ++) {
			
			if (allPeople[i].isInfected()) {
				for (int k = 0; k < POPULATION; k ++) {
				
					if ((Math.abs(allPeople[i].getX() - allPeople[k].getX()) <= infectionRadius && Math.abs(allPeople[i].getY() - allPeople[k].getY()) <= infectionRadius) && ThreadLocalRandom.current().nextInt(0, 100) < chance) {
					
						allPeople[k].setInfection();
						
					}
				
				}
			}
			
		}
		
	}
	
	public void startMovement() {
		
		while (true) {
			
			for (int i = 0; i < POPULATION; i ++) {
				
				if (allPeople[i].getQuarantined()) {
					
					allPeople[i].setX(1400);
					
				}
				
				int xShift = ThreadLocalRandom.current().nextInt(lowParam, highParam + 1);
				int yShift = ThreadLocalRandom.current().nextInt(lowParam, highParam + 1);
				allPeople[i].shiftX(xShift);
				
				allPeople[i].shiftY(yShift);
				
				if (allPeople[i].getX() < 0) {
					
					xShift = highParam * 2;
					allPeople[i].shiftX(xShift);
					
				}
				
				if (allPeople[i].getX() + allPeople[i].getCurrentImage().getIconWidth() > width) {
					
					xShift = lowParam * 2;
					allPeople[i].shiftX(xShift);
					
				}
				
				if (allPeople[i].getY() < 0) {
					
					yShift = highParam * 2;
					allPeople[i].shiftY(yShift);
					
				}
				
				if (allPeople[i].getY() + allPeople[i].getCurrentImage().getIconHeight() * 2 > height) {
					
					yShift = lowParam * 2;
					allPeople[i].shiftY(yShift);
					
				}
				
				allPeople[i].travelToHub(hub, hubChance);
				
				
			}
			
			revalidate();
			repaint();
			
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
}
