/*
 * Title:           ControllableSprite.java
 * Description:     This class uses a sprite that can be contolled with the keyboard
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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

/**
 * @author Campbell Wilson
 * @version $Revision: $ $Date: $
 */
public class ControllableSprite {

	private static final String CVS_ID = "$Header:  $";
	
	ImageIcon image;
	Image rawImage;
	BufferedImage spriteImage;
	int x;
	int y;
	public final static int height = 10;
	public final static int width = 10;
	CarCrash applet;
	int xDefault;
	int yDefault;
	
	/**
	 * Use own image
	 *
	 */
	public ControllableSprite(){
		
	}
	
	public ControllableSprite(CarCrash applet, ImageIcon image, int x, int y){
		this.applet = applet;
		this.image = image;
		this.x = x;
		this.y = y;
		this.xDefault = x;
		this.yDefault = y;
		rawImage = image.getImage();
	}
	
	public void resetPosition(){
		x = xDefault;
		y = yDefault;
	}
	
	/**
	 * 
	 * if any of the sprites hit any of these points then a collision has occurred
	 * if controllable sprite top1
	 *
	 */
	public boolean checkForCollision() {
		for (int i = 0; i < applet.sprites.length; i++) {
			Sprite s = applet.sprites[i];
			if ((x + width) >= s.x && (s.x + s.width) >= x
					&& (y + height) >= s.y && (s.y + s.height) >= y)
				return true;
		}
		return false;
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, width, height);
	}
	
	public void update(int location){
		
		if (location == KeyEvent.VK_LEFT){
			if ( x > 0){
				x -= 10;
			}
		}
		else if (location == KeyEvent.VK_RIGHT){
			if ( (x+width) < applet.width){
				x += 10;
			}
		}
		else if (location == KeyEvent.VK_DOWN){
			if ( (y+height) < applet.height){
				y += 10;
			}
		}
		else if (location == KeyEvent.VK_UP){
			if (y > 0){
				y -= 10;
				if (y <= 0){
					applet.winner = true;
					applet.game = false;
				}
			}
		}
		if (checkForCollision()){
			applet.loser = true;
			applet.game = false;
		}
	}
}

