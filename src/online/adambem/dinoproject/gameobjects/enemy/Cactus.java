package online.adambem.dinoproject.gameobjects.enemy;

import online.adambem.dinoproject.gameobjects.Collidable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Cactus implements Collidable {

	private int x1;
	private int y2;

	private BufferedImage image;

	protected Cactus (int x1, int y2) {
		this.x1 = x1;
		this.y2 = y2;
	}

	public Cactus(int x1, int y2, int quantity) {

		this(x1, y2);
		try {
			// Read image with correct number of cacti
			switch (quantity) {
				case 2:
					image = ImageIO.read( new FileInputStream("./Images/Little2.png"));
					break;
				case 3:
					image = ImageIO.read( new FileInputStream("./Images/Little3.png"));
					break;
				default:
					image = ImageIO.read( new FileInputStream("./Images/Little1.png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void move() {
		x1 -= 6;
	}

	// Empty method required by interface
	@Override
	public void switchTexture() {

	}

	@Override
	public void render (Graphics g) {
		g.drawImage(image, x1, getY1(), null);
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
		return x1 + image.getWidth();
	}

	@Override
	public int getY1() {
		return y2 - image.getHeight();
	}

	@Override
	public int getY2() {
		return y2;
	}

	protected void setImage(BufferedImage image) {
		this.image = image;
	}
}
