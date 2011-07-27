/*
 * Title:           Sprite.java
 * Description:     This class is used to provide the animated sprites
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
import java.awt.Image;
import java.awt.image.BufferStrategy;

import javax.swing.ImageIcon;

/**
 * @author Campbell Wilson
 * @version $Revision: $ $Date: $
 */
public class Sprite {

	private static final String CVS_ID = "$Header:  $";
	
	ImageIcon image;
	Image rawImage;
	int x;
	int y;
	int direction;
	int height = 20;
	int width = 40;

	int xDefault;
	int yDefault;
	
	int distance;
	
	Color color;
	
	CarCrash applet;
	
	public void paintToBuffer(){
	}
	
	/**
	 * 
	 * @param image
	 * @param x
	 * @param y
	 * @param direction 1 for up, 2 for down
	 */
	public Sprite(CarCrash applet, ImageIcon image, int x, int y, int direction, int distance, Color color) {
		this.image = image;
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.applet = applet;
		this.distance = distance;
		this.xDefault = x;
		this.yDefault = y;
		this.color = color;
	}

	public Sprite(CarCrash applet, int x, int y, int direction, int distance) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.applet = applet;
		this.distance = distance;
		this.xDefault = x;
		this.yDefault = y;
		rawImage = image.getImage();
	}
	
	public void paint (Graphics g){
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}
	
	/**
	 * Update the position of the image
	 * 1 is right
	 * 2 is left
	 *
	 */
	public synchronized void update() {
		
		if (direction == 1) {
			x += distance;
		}
		else {
			x -= distance;
		}
		if ( (x > applet.width) || (x+width < 0))
			x = this.xDefault;
		if (checkForCollision()){
			applet.loser = true;
			applet.game = false;
		}
	}
	
	public synchronized void resetPosition(){
		x = xDefault;
		y = yDefault;
	}
	
	public boolean checkForCollision() {
		ControllableSprite s = applet.mainSprite;
		if ((x + width) >= s.x && (s.x + s.width) >= x && (y + height) >= s.y
				&& (s.y + s.height) >= y)
			return true;
		else
			return false;
	}
}
