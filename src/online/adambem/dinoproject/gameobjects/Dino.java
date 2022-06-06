package online.adambem.dinoproject.gameobjects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Dino implements Collidable {

	private final int GROUND_LEVEL;

	// Initial jump speed
	private final int JUMP_STRENGTH = 7;

	// Maximum reachable falling speed
	private final int MAX_FALL_SPEED = 4;

	// Y cord. Above it, dino will start falling down when jumping
	private final int SPEED_LOSS_HEIGHT = 100;

	// Object used to store currently displayed texture
	private BufferedImage dinoTexture;

	// Textures used when walking
	private BufferedImage dinoWalk;
	private BufferedImage dinoWalkAlt;

	// Textures used when ducking
	private BufferedImage dinoDuck;
	private BufferedImage dinoDuckAlt;

	// Coordinates of left-bottom point of dino
	// Other can be calculated with quasi-getters below
	private int x1;
	private int y2;
	private int changeY;

	private int health;

	private int immunityTime;

	// Stores information about which texture should be used
	private boolean dinoAlt;

	// Is dino ducking?
	private boolean dinoIsDuck;

	// Can do second jump?
	private boolean canDoubleJump;

	public Dino(int y) {
		GROUND_LEVEL = y;
		x1 = 20;
		y2 = GROUND_LEVEL;
		changeY = 0;

		dinoAlt = false;
		dinoIsDuck = false;
		canDoubleJump = true;
		try {
			// Loads required textures from disk
			dinoWalk = ImageIO.read( new FileInputStream("./Images/DinoWalk1.png"));
			dinoWalkAlt = ImageIO.read( new FileInputStream("./Images/DinoWalk2.png"));

			dinoDuck = ImageIO.read( new FileInputStream("./Images/DinoDuck1.png"));
			dinoDuckAlt = ImageIO.read( new FileInputStream("./Images/DinoDuck2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		dinoTexture = dinoWalk;
	}

	public void damage() {
		health--;
	}

	public boolean hasImmunity() {
		return immunityTime > 0;
	}

	@Override
	public void move() {
		immunityTime--;

		// Move dino according to current vertical speed
		y2 += changeY;

		// If dino is high enough and his falling speed is lower than maximum,
		// make falling faster
		if (y2 < SPEED_LOSS_HEIGHT && changeY < MAX_FALL_SPEED) changeY++;

		// If dino is on ground level or lower, set it to ground level and stop vertical movement
		if (y2 >= GROUND_LEVEL) {
			changeY = 0;
			y2 = GROUND_LEVEL;
			canDoubleJump = true;
		}
	}

	public void render (Graphics g) {
		g.drawImage(dinoTexture, x1, getY1(), null);
	}


	public void jump(boolean isForced) {
		// If dino can jump (walks on the ground), start jump
		if (y2 == GROUND_LEVEL || isForced) {
			changeY = -JUMP_STRENGTH;
		// If dino can jump (didn't use his second jump), start jump
		} else if (canDoubleJump) {
			// Makes second jump 33% stronger than the first one
			changeY = -JUMP_STRENGTH - (JUMP_STRENGTH / 3);
			canDoubleJump = false;
		}
	}

	public void switchTexture() {


		// Switch to other texture
		dinoAlt = !dinoAlt;

		// Select texture according to current state of dino
		if (dinoIsDuck) {
			if (dinoAlt)
				dinoTexture = dinoDuckAlt;
			else
				dinoTexture = dinoDuck;
		}
		else {
			if (dinoAlt)
				dinoTexture = dinoWalkAlt;
			else
				dinoTexture = dinoWalk;
		}

	}


	//##############################
	// Getters and Setters
	//##############################

	public void setDinoDuck(boolean duck) {
		this.dinoIsDuck = duck;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setImmunityTime(int ticks) {
		this.immunityTime = ticks;
	}

	@Override
	public int getY1() {
		return y2 - dinoTexture.getHeight();
	}

	@Override
	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	@Override
	public int getX1() {
		return x1;
	}

	@Override
	public int getX2() {
		return x1 + dinoTexture.getWidth();
	}


}
