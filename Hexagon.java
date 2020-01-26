// Daniel Byun (byun-sungwoo)

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Hexagon {
	// Global Variables
	private int xPos;
	private int yPos;
	private int xVel;
	private int yVel;
	private int size;
	private int sideLength;
	private Color fillColor;

	private int[] xArray = new int[6];
	private int[] yArray = new int[6];
	private Point[] hexPoints = new Point[6];

	// [Purpose] Constructor for Hexagon class
	// Takes a (Color) color to fill the hexagon, (int) x position of hexagon, (int) y position of hexagon, 
	// (int) x velocity of hexagon, (int) y velocity of hexagon, and (int) total width of the hexagon.
	// Constructor initializes two arrays of integers, xArray and yArray, which represent the x coordinates and y coordinates of the hexagon's 6 points.
	// Constructor initializes an array of Points, hexPoints that represent the coordinates of all the points of the hexagon.
	public Hexagon(Color fillColor, int xPos, int yPos, int xVel, int yVel, int size) {
		this.fillColor = fillColor;
		this.xPos = xPos;
		this.yPos = yPos;
		this.xVel = xVel;
		this.yVel = yVel;
		this.size = size;
		this.sideLength = size/2;
		
		double hypotenuse = sideLength;
		double width = sideLength/2;
		double height = (Math.sqrt(3)/2)*sideLength;
		
		xArray[0] = (int)(-width);
		xArray[1] = (int)width;
		xArray[2] = (int)hypotenuse;
		xArray[3] = (int)width;
		xArray[4] = (int)(-width);
		xArray[5] = (int)(-hypotenuse);
		
		yArray[0] = (int)height;
		yArray[1] = (int)height;
		yArray[2] = (int)0;
		yArray[3] = (int)(-height);
		yArray[4] = (int)(-height);
		yArray[5] = (int)0;
		
		for(int a = 0; a < xArray.length; a++) {
			xArray[a] += this.xPos;
			xArray[a] += hypotenuse;
		}
		for(int a = 0; a < yArray.length; a++) {
			yArray[a] += this.yPos;
			yArray[a] += height;
		}
		for(int a = 0; a < 6; a++)
			hexPoints[a] = new Point(xArray[a], yArray[a]);
	}

	// [Purpose] moveLocation takes two integers that represent the shift in x and shift in y respectively and has a return type of Hexagon.
	// moveLocation takes the dx and dy and returns a new Hexagon with all the same parameters of the current Hexagon.
	// but adds dx to xPos and dy to yPos.
	public Hexagon moveLocation(int dx, int dy) {
		return new Hexagon(fillColor, xPos+dx, yPos+dy, xVel, yVel, size);
	}

	// [Purpose] withinHexagon takes a Point and has a return type of Boolean.
	// withinHexagon checks if the given Point lies within the area created by the current Hexagon's 
	// hexPoints which is the set of points in an x-y plane that represents the Hexagon.
	// If the point lies within return true, if not false.
	public Boolean withinHexagon(Point p) {
		double width = sideLength/2;
		double height = (Math.sqrt(3)/2)*sideLength;
		
		if(p.getX() >= hexPoints[5].getX() && p.getX() <= hexPoints[5].getX()+width && p.getY() >= -(height/width)*(p.getX()-hexPoints[5].getX())+height+yPos && p.getY() <= (height/width)*(p.getX()-hexPoints[5].getX())+height+yPos)
			return true;
		else if(p.getX() >= hexPoints[0].getX() && p.getX() <= hexPoints[1].getX() && p.getY() <= hexPoints[0].getY() && p.getY() >= hexPoints[3].getY())
			return true;
		else if(p.getX() >= hexPoints[1].getX() && p.getX() <= hexPoints[2].getX()+width && p.getY() >= -(height/width)*(hexPoints[2].getX()-p.getX())+height+yPos && p.getY() <= (height/width)*(hexPoints[2].getX()-p.getX())+height+yPos)
			return true;
		return false;
	}

	// [Purpose] draw takes a Graphics and has a return type of void.
	// draw utilizes fillPolygon and plugs in xArray and yArray to draw a hexagon.
	// draw draws two Polygons, one colored in Polygon by using fillPolygon and colors it using the 
	// Hexagon's fillColor the other is an outline by using drawPolygon with black.
	public void draw(Graphics g) {
		g.setColor(this.fillColor);
		g.fillPolygon(xArray, yArray, 6);
		g.setColor(Color.BLACK);
		g.drawPolygon(xArray, yArray, 6);
	}

	// Getter/Setter
	// [Purpose] Getter method for xPos.
	public int getXPos() {
		return xPos;
	}

	// [Purpose] Setter method for xPos.
	public void setXPos(int xPos) {
		this.xPos = xPos;
	}

	// [Purpose] Getter method for yPos.
	public int getYPos() {
		return yPos;
	}

	// [Purpose] Setter method for yPos.
	public void setYPos(int yPos) {
		this.yPos = yPos;
	}

	// [Purpose] Getter method for xVel.
	public int getXVel() {
		return xVel;
	}

	// [Purpose] Setter method for xVel.
	public void setXVel(int xVel) {
		this.xVel = xVel;
	}

	// [Purpose] Getter method for yVel.
	public int getYVel() {
		return yVel;
	}

	// [Purpose] Setter method for yVel.
	public void setYVel(int yVel) {
		this.yVel = yVel;
	}

	// [Purpose] Getter method for size.
	public int getSize() {
		return size;
	}

	// [Purpose] Setter method for size.
	public void setSize(int size) {
		this.size = size;
	}

	// [Purpose] Getter method for fillColor.
	public Color getFillColor() {
		return fillColor;
	}

	// [Purpose] Setter method for fillColor.
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	// [Purpose] Getter method for hexPoints.
	public Point[] getHexPoints() {
		return hexPoints;
	}
}
