/*
 * Title:           MoustacheApplet2.java
 * Description:     Description: MoustacheApplet2.java
 * Copyright:       Copyright (c) 2005
 * Company:         Comstock 
 * Author:          Campbell Wilson
 * Date:            March 2005
 *
 * $Revision: $
 * $Source:  $
 * 
 * Version History
 * $Log:  $
 */
package com.camartic.carcrash;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JPanel;

/**
 * @author Campbell Wilson
 * @version $Revision: $ $Date: $
 */
public class CarCrash extends JApplet implements Runnable{

	private static final String CVS_ID = "$Header:  $";
	
	AudioClip crashClip;
	AudioClip hornClip;
	
	Sprite [] sprites = new Sprite[6];
	ControllableSprite mainSprite;
	
	ImageIcon imageIcon;
	Image mainImage;
	static int width, height;
	Thread animationThread;
	Road [] roads;
	boolean winner;
	boolean loser;
	boolean displayScreen;
	
	Graphics osGraphics;
	Image offScreenImage;
	
	boolean game = true;
	
	public void init() {
		System.out.println("INIT");
		mainImage = getImage(super.getCodeBase(), "images/test_small.JPG");
		crashClip = super.getAudioClip(super.getCodeBase(), "audio/crash.wav");
		hornClip = super.getAudioClip(super.getCodeBase(), "audio/horn.wav");		
		System.out.println(super.getCodeBase());
		imageIcon = new ImageIcon(mainImage);
		setAppletStats();
		KeyboardEvents events = new KeyboardEvents();
		this.setFocusable(true);
		this.addKeyListener(events);
		sprites[0] = new Sprite(this, imageIcon, 0, 30, 1, 11, Color.blue);
		sprites[1] = new Sprite(this, imageIcon, width, 130, 2, 10, Color.cyan);
		sprites[2] = new Sprite(this, imageIcon, 0, 230, 1, 9, Color.orange);
		sprites[3] = new Sprite(this, imageIcon, width, 330, 2, 8, Color.green);
		sprites[4] = new Sprite(this, imageIcon, 0, 430, 1, 7, Color.magenta);
		sprites[5] = new Sprite(this, imageIcon, width, 530, 2, 6, Color.red);
		mainSprite = new ControllableSprite(this, imageIcon, this.getWidth()/2, this.getHeight() - 10);
		// create roads for sprites
		roads = new Road[sprites.length];
		for (int i = 0; i < sprites.length; i++){
			roads[i] = new Road(sprites[i]);
		}
	}
	
	public synchronized void paintGameScreen(Graphics g){
		if (displayScreen)
			return;
		displayScreen = true;
		osGraphics.setColor(Color.BLACK);
		osGraphics.fillRect(0, 0, width, height);
		osGraphics.setColor(Color.red);
		Font largeFont = new Font("Helvetica", Font.PLAIN, 24);
		osGraphics.setFont(largeFont);
		if (winner){
			osGraphics.drawString("WINNER - PURE DEAD BRILLIANT!", 0, 100);
			hornClip.play();
		}
		else{
			osGraphics.drawString("LOSER - YOU ARE A BAMPOT!", 0, 100);
			crashClip.play();
		}
		osGraphics.drawString("PRESS RETURN TO RESTART", 0, 200);
		g.drawImage(offScreenImage, 0, 0, this);
	}
	
	public void paint(Graphics g) {
		if (!game)
			paintGameScreen(g);
		else{
			render();
			g.drawImage(offScreenImage, 0, 0, this);
		}
	}
	
	/**
	 * Paint the offscreen graphics
	 *
	 */
	public void render() {
		// foreground
		if (offScreenImage == null){
			offScreenImage = super.createImage(width, height);
			osGraphics = offScreenImage.getGraphics();
		}
		osGraphics.clearRect(0,0,width, height);
		osGraphics.setColor(Color.white);
		osGraphics.fillRect(0, 0, width, height);
		for (int i = 0; i < roads.length; i++) {
			roads[i].paint(osGraphics);
		}
		
		mainSprite.paint(osGraphics);
		// paint each sprite and road
		for (int i = 0; i < sprites.length; i++) {
			sprites[i].paint(osGraphics);
		}
	}
	
	public void start(){
		animationThread = new Thread(this);
		animationThread.start();
	}
	
	public void setAppletStats(){
		System.out.println("width is "+this.getWidth());
		width = this.getWidth();
		System.out.println("height is "+this.getHeight());
		height = this.getHeight();
	}
	
	/**
	 * Used for the controllable sprite
	 */
	public class KeyboardEvents implements KeyListener{
		
		public void keyPressed(KeyEvent e) {
			int location = e.getKeyCode();
			//if (location == KeyEvent.VK_LEFT)
			if ( !game && location == KeyEvent.VK_ENTER){
				reset();
			}
			mainSprite.update(location);
		}

		public void keyTyped(KeyEvent e) {
		}
		
		public void keyReleased(KeyEvent e) {
		}
	}
	
	public void reset(){
		for (int i = 0; i < sprites.length; i++){
			sprites[i].resetPosition();
		}
		mainSprite.resetPosition();
		winner = false;
		loser = false;
		hornClip.stop();
		crashClip.stop();
		game = true;
		displayScreen = false;
	}
	
	/**
	 * The animation thread
	 */
	public void run() {
		while (true) {
			try {
				// update the sprite positions
				for (int i = 0; i < sprites.length; i++) {
					sprites[i].update();
				}
				Thread.sleep(10);
			}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			repaint();
		}
	}
}