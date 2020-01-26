// Daniel Byun (byun-sungwoo)

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

public class HexagonAnimation extends JPanel implements MouseListener, KeyListener, ActionListener {
	// Global Variables
	public final int FRAME_WIDTH;
	public final int FRAME_HEIGHT;
	private final int HEXAGON_SIZE;
	private ArrayList<Hexagon> hexArray = new ArrayList<Hexagon>();
	private Timer time = new Timer(25, this);	// Every 40 refreshes is 1 sec since 25*40=1000
	private Boolean active;				// Boolean that keeps track whether or not inactivity is greater than or equal to 15
	private double inactivity = 0;			// Keeps track of how long user has been inactive in seconds (so if inactivity = 3 that means 3 seconds)

	// [Purpose] Constructor for HexagonAnimation class.
	// Constructor takes (int) width of frame, (int) width of height, and (int) width of hexagon and initializes FRAME_WIDTH, FRAME_HEIGHT, and HEXAGON_SIZE respectively.
	// Constructor adds initial Hexagon to the ArrayList<Hexagon> hexArray by using the HEXAGON_SIZE, FRAME_WIDTH, and FRAME_HEIGHT.
	// Constructor also starts timer.
	public HexagonAnimation(int FRAME_WIDTH, int FRAME_HEIGHT, int HEXAGON_SIZE) {
		super();
		
		this.FRAME_WIDTH = FRAME_WIDTH;
		this.FRAME_HEIGHT = FRAME_HEIGHT;
		this.HEXAGON_SIZE = HEXAGON_SIZE;
		
		setBackground(Color.darkGray);
		
		hexArray.add(new Hexagon(randomColor(), this.FRAME_WIDTH/2-(HEXAGON_SIZE/2), (int)(this.FRAME_HEIGHT/2-((Math.sqrt(3)/2)*HEXAGON_SIZE/2)), randomSpeed(), randomSpeed(), HEXAGON_SIZE));
		
		addMouseListener(this);
		addKeyListener(this);
		setFocusable(true);
		
		time.start();
	}

	// [Purpose] randomColor takes no input and returns one of 6 colors using Math.random().
	// Red, Yellow, Green, Blue, White, or Magenta
	public Color randomColor() {
		switch((int)(Math.random()*6)) {
		case 0: return Color.red;
		case 1: return Color.yellow;
		case 2: return Color.green;
		case 3: return Color.blue;
		case 4: return Color.white;
		default: return Color.magenta;
		}
	}

	// [Purpose] randomSpeed takes no input and returns
	// a random integer from -4 to 4 excluding zero.
	public static int randomSpeed() {
		int topSpeed = 4;	// Max speed that the hexagon's x or y speed can travel negative or positive
		int value = (int)(Math.random()*(topSpeed*2));
		if(value >= topSpeed)
			return value-(topSpeed-1);
		return value-topSpeed;
	}

	public void addNotify() {
		super.addNotify();
		requestFocus();
	}

	// [Purpose] paintComponent takes a Graphics and has a return type void.
	// paintComponent runs a enhanced for loop on hexArray and applies .draw(g) to each Hexagon.
	// paintComponent also displays instructions for the controls of the Class along with the time the user has been inactive.
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for(Hexagon a : hexArray)
			a.draw(g);
		
		g.setColor(Color.green);
		g.setFont(new Font("TimesRoman", Font.BOLD, 15));
		g.drawString("[Press ESC to reset the window] [Click a hexagon to split into 4 smaller hexagons]", 7, 20);
		g.drawString("[Press MINUS to subtract 5 seconds] [Press EQUALS to add 5 seconds]", 7, 20 + 20);
		g.drawString("[Once user has been inactive for 15 seconds, all hexagons will begin to move]", 7, 20 + 20 + 20);
		
		if(inactivity >= 15)			// Inactive for 15 sec, red warning
			g.setColor(Color.red);
		else if(inactivity >= 10)		// Inactive for 5 sec, orange warning
			g.setColor(Color.orange);
		else if(inactivity >= 5)		// Inactive for 5 sec, yellow warning
			g.setColor(Color.yellow);
		
		if(((int)inactivity) >= 3600)		// [Hour : Minute : Second] display
			g.drawString("User has been inactive for " + ((int)inactivity)/3600 + " hr " + (((int)inactivity)/60-(((int)inactivity)/3600)*60) + " min " + ((int)inactivity)%60 + " sec.", 7, 20 + 20 + 20 + 20);
		else if(((int)inactivity) >= 60)	// [Minute : Second] display
			g.drawString("User has been inactive for " + ((int)inactivity)/60 + " min " + ((int)inactivity)%60 + " sec.", 7, 20 + 20 + 20 + 20);
		else					// [Second] display
			g.drawString("User has been inactive for " + (int)inactivity + " sec.", 7, 20 + 20 + 20 + 20);
	}

	// [Purpose] mouseReleased takes a MouseEvent and has a return type of void.
	// mouseReleased takes the coordinate of the mouse's location and checks if the location of the mouse is within any of the Hexagons in hexArray.
	// If the location of the mouse is within a Hexagon in hexArray, that hexagon will be removed from hexArray and replaced with 4 hexagons with half the original's size.
	// If the mouse has been released inactivity will be reset to 0.
	public void mouseReleased(MouseEvent e) {
		for(int a = 0; a < hexArray.size(); a++) {
			// Remove and split into 4
			if(hexArray.get(a).withinHexagon(new Point(e.getX(), e.getY())) == true) {
				int oldSize = hexArray.get(a).getSize();
				int oldX = hexArray.get(a).getXPos();
				int oldY = hexArray.get(a).getYPos();
				
				hexArray.remove(hexArray.get(a));
				hexArray.add(new Hexagon(randomColor(), oldX, oldY, randomSpeed(), randomSpeed(), oldSize/2));
				hexArray.add(new Hexagon(randomColor(), oldX+oldSize/2, oldY, randomSpeed(), randomSpeed(), oldSize/2));
				hexArray.add(new Hexagon(randomColor(), oldX, oldY+oldSize/2, randomSpeed(), randomSpeed(), oldSize/2));
				hexArray.add(new Hexagon(randomColor(), oldX+oldSize/2, oldY+oldSize/2, randomSpeed(), randomSpeed(), oldSize/2));
				
				break;	// If a Hexagon has been split into 4, break for loop.
			}
		}
		
		inactivity = 0;	// Reset countdown
	}

	// [Purpose] keyPressed takes a KeyEvent and has a return type of void.
	// If ESC has been pressed, keyPressed will reset inactivity to 0, clear hexArray, and add the initial Hexagon to the 
	// ArrayList<Hexagon> hexArray by using the HEXAGON_SIZE, FRAME_WIDTH, and FRAME_HEIGHT.
	// If MINUS is pressed, keyPressed will subtract 5 from inactivity. If subtracting 5 from inactivity results in a value below zero, inactivity will be set equal to 0.
	// If EQUALS is pressed, keyPressed will add 5 to inactivity.
	public void keyPressed(KeyEvent e) {
		// If ESC is pressed, clear screen
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			hexArray = new ArrayList<Hexagon>();
			hexArray.add(new Hexagon(randomColor(), this.FRAME_WIDTH/2-(HEXAGON_SIZE/2), (int)(this.FRAME_HEIGHT/2-((Math.sqrt(3)/2)*HEXAGON_SIZE/2)), randomSpeed(), randomSpeed(), HEXAGON_SIZE));
			
			inactivity = 0;	// Reset countdown
		}
		// If MINUS is pressed, subtract 5 seconds from inactivity
		if(e.getKeyCode() == KeyEvent.VK_MINUS) {
			if(inactivity - 5 <= 0)	// Can't go below 0
				inactivity = 0;
			else
				inactivity -= 5;
		}
		// If EQUALS is pressed, add 5 seconds to inactivity
		if(e.getKeyCode() == KeyEvent.VK_EQUALS)
			inactivity += 5;
	}

	// [Purpose] actionPerformed takes an ActionEvent and has a return type of void.
	// If inactivity is less than 15, then active will be set equal to true.
	// If inactivity is greater than or equal to 15, then active will be set equal to false.
	// If active is false all Hexagons in hexArray will be moved according to their xVel and yVel values.
	// - If moving the Hexagon means being greater than the FRAME_WIDTH or FRAME_HEIGHT or less than 0, then 
	//	 the respective velocities will be multiplied by -1 to keep the Hexagon in frame view.
	// - If moving the Hexagon means coming into contact with another Hexagon in the hexArray, multiply both Hexagon's x and y velocities by -1.
	// actionPerformed adds 0.025 to inactivity per tick.
	// actionPerformed calls method repaint() per tick.
	public void actionPerformed(ActionEvent e) {
		if(inactivity < 15)	// If inactive for under 15 seconds
			active = true;	// Set active to false
		if(inactivity >= 15)	// If inactive for 15 seconds
			active = false;	// Set active to false
		if(!active) {		// If not active then move hexagons
			for(int a = 0; a < hexArray.size(); a++) {	// If shape hits wall
				if(hexArray.get(a).getXPos() <= 0) {						// Left wall
					hexArray.get(a).setXVel(hexArray.get(a).getXVel()*-1);
					hexArray.get(a).setXPos(hexArray.get(a).getXPos() + 1);	// Prevents hexagon from clipping into wall
				}
				if(hexArray.get(a).getXPos() >= FRAME_WIDTH-hexArray.get(a).getSize()) {	// Right wall
					hexArray.get(a).setXVel(hexArray.get(a).getXVel()*-1);
					hexArray.get(a).setXPos(hexArray.get(a).getXPos() - 1);	// Prevents hexagon from clipping into wall
				}
				if(hexArray.get(a).getYPos() <= 0) {						// Ceiling
					hexArray.get(a).setYVel(hexArray.get(a).getYVel()*-1);
					hexArray.get(a).setYPos(hexArray.get(a).getYPos() + 1);	// Prevents hexagon from clipping into wall
				}
				if(hexArray.get(a).getYPos() >= FRAME_HEIGHT-hexArray.get(a).getSize()) {	// Floor
					hexArray.get(a).setYVel(hexArray.get(a).getYVel()*-1);
					hexArray.get(a).setYPos(hexArray.get(a).getYPos() - 1);	// Prevents hexagon from clipping into wall
				}
			}
			
			for(int a = 0; a < hexArray.size(); a++) {	// Compares every Hexagon with every other Hexagon in hexArray.
				for(int b = a+1; b < hexArray.size(); b++) {
					Hexagon aHex = hexArray.get(a);
					Point[] bHexPnt = hexArray.get(b).getHexPoints();
					// If any of hexArray.get(b)'s points are within hexArray.get(a)
					if(	aHex.withinHexagon(bHexPnt[0]) || aHex.withinHexagon(bHexPnt[1]) ||
						aHex.withinHexagon(bHexPnt[2]) || aHex.withinHexagon(bHexPnt[3]) ||
						aHex.withinHexagon(bHexPnt[4]) || aHex.withinHexagon(bHexPnt[5])) {
						// If A's x pos is less than B then move A left 1 and B right 1
						if(hexArray.get(a).getXPos() < hexArray.get(b).getXPos()) {
							hexArray.set(a, hexArray.get(a).moveLocation(-1, 0));
							hexArray.set(b, hexArray.get(b).moveLocation(1, 0));
						} else {// Else move A right 1 and B left 1
							// This prevents hexagons from being stuck inside one another
							hexArray.set(a, hexArray.get(a).moveLocation(1, 0));
							hexArray.set(b, hexArray.get(b).moveLocation(-1, 0));
						}
						
						hexArray.get(a).setXVel(hexArray.get(a).getXVel()*-1);
						hexArray.get(a).setYVel(hexArray.get(a).getYVel()*-1);
						hexArray.get(b).setXVel(hexArray.get(b).getXVel()*-1);
						hexArray.get(b).setYVel(hexArray.get(b).getYVel()*-1);
					}
				}
			}
			for(int a = 0; a < hexArray.size(); a++)	// Move all hexagons according to their velocity
				hexArray.set(a, hexArray.get(a).moveLocation(hexArray.get(a).getXVel(), hexArray.get(a).getYVel()));
		}
		inactivity += .025;	// Every 40 refreshes is 1 second
		repaint();
	}

	// Unused Functions
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}
