/*
 * Title:           Road.java
 * Description:     Description: Road.java
 * Company:         Comstock 
 * Author:          Campbell Wilson
 *
 * $Revision: $
 * $Source:  $
 * 
 * Version History
 * $Log:  $
 */
package com.camartic.carcrash;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * @author Campbell Wilson
 * @version $Revision: $ $Date: $
 */
public class Road {

	private static final String CVS_ID = "$Header:  $";
	
	int y;
	int width;
	int imageHeight;
	int roadHeight;
	int topOfRoad;
	int bottomOfRoad;
	int middleOfRoad;
	
	/**
	 * The same logic as the instructor below, but creates the roads from the sprites
	 * @param s
	 */
	public Road (Sprite s){
		this.y = s.y;
		this.width = s.applet.width;
		this.imageHeight = s.height;
		roadHeight = imageHeight + 40;
		topOfRoad = y - 20;
		bottomOfRoad = topOfRoad + roadHeight;
		middleOfRoad = topOfRoad + (roadHeight/2);
	}
	
	/**
	 * The height of the road is 20 higher and lower than the image
	 * x is always 0
	 * @param x
	 * @param y
	 * @param width
	 * @param imageHeight
	 */
	public Road (int y, int width, int imageHeight){
		this.y = y;
		this.width = width;
		this.imageHeight = imageHeight;
	}
	
	public void paint(Graphics g){
		g.setColor(Color.darkGray);
		g.fillRect(0, topOfRoad, width, roadHeight);
		// paint lines on road, 2 pixels above and below the middle
		g.setColor(Color.white);
		for (int i = 0; i < width; i += 20){
			g.fillRect(i, middleOfRoad - 2, 10, 4);
		}
	}
}