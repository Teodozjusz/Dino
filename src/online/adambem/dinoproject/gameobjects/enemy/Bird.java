package online.adambem.dinoproject.gameobjects.enemy;

import online.adambem.dinoproject.gameobjects.Collidable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Bird implements Collidable {

	// Object used to store currently displayed texture
	private BufferedImage birdTexture;

	// Textures used when flying
	private BufferedImage birdFlying;
	private BufferedImage birdFlyingAlt;

	private boolean birdAlt;

	// The left-bottom point of object
	// Other points are calculated on-demand
	// using quasi-getters and width/height of the image
	private int x1;
	private int y2;

	public Bird(int x, int y) {
		x1 = x;
		y2 = y;

		try {
			birdFlying = ImageIO.read( new FileInputStream("./Images/Bird1.png"));
			birdFlyingAlt = ImageIO.read( new FileInputStream("./Images/Bird2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		birdTexture = birdFlying;

	}

	@Override
	public void move() {
		x1 -= 6;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(birdTexture, getX1(), getY1(), null);
	}

	public void switchTexture() {
		// Do flying animation
		birdAlt = !birdAlt;

		// Select texture according to current state of dino
		if(birdAlt) {
			birdTexture = birdFlyingAlt;
		}
		else birdTexture = birdFlying;
	}



	//##############################
	// Getters and Setters
	//##############################

	@Override
	public int getX1() {
		return x1;
	}

	@Override
	public int getX2() {
		return x1 + birdTexture.getWidth();
	}

	@Override
	public int getY1() {
		return y2 - birdTexture.getHeight();
	}

	@Override
	public int getY2() {
		return y2;
	}
}
