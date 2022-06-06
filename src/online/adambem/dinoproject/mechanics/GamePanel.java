package online.adambem.dinoproject.mechanics;

import online.adambem.dinoproject.gameobjects.Collidable;
import online.adambem.dinoproject.gameobjects.enemy.BigCactus;
import online.adambem.dinoproject.gameobjects.enemy.Bird;
import online.adambem.dinoproject.gameobjects.enemy.Cactus;
import online.adambem.dinoproject.gameobjects.Dino;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class GamePanel extends JPanel {

	// Dimensions of the game's window
	private final int FRAME_WIDTH;
	private final int FRAME_HEIGHT;

	// Level of the ground
	private final int GROUND_LEVEL;

	// Player character
	private Dino dino;
	// List of obstacles
	private List<Collidable> obstacles;

	// Score counter
	private int points;

	// High score counter
	private int highScore;

	// Player lives count indicator
	private String livesBar;

	GameLoop loop;

	public GamePanel(int FRAME_WIDTH, int FRAME_HEIGHT) throws HeadlessException {
		super();

		this.FRAME_WIDTH = FRAME_WIDTH;
		this.FRAME_HEIGHT = FRAME_HEIGHT;

		this.GROUND_LEVEL = FRAME_HEIGHT - 50;

		points = 0;
		readHighScore();

		this.setSize(FRAME_WIDTH,FRAME_HEIGHT);
		this.setVisible(true);

		dino = new Dino(GROUND_LEVEL);

		setup(false);
	}

	// Method used to start or restart the game after lose
	public void setup(boolean startGame) {
		if (loop == null || !loop.isAlive()) {
			// Move dino to the ground level
			dino.setY2(GROUND_LEVEL);

			// Reset player's lives
			dino.setHealth(3);
			refreshHealthBar();

			// Reset list of obstacles and number of points
			obstacles = generateFirstObstacles();
			points = 0;

			// Create new game loop and start it if needed
			loop = new GameLoop(this);
			if (startGame)
				loop.start();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);

		// Draw background and interface
		drawBackground(g);
		drawScore(g);
		// Write instructions if game isn't active
		if (!loop.isAlive() || !loop.isGameRuns()) {
			drawInstructions(g);
		}

		// Rendering objects on panel
		for (Collidable Collidable: obstacles) {
			Collidable.render(g);
		}
		dino.render(g);
	}

	private void drawBackground(Graphics g) {
		// Draw background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

		// Draw ground
		g.setColor(Color.BLACK);
		g.drawLine(0, GROUND_LEVEL, FRAME_WIDTH, GROUND_LEVEL);
	}
	private void drawInstructions(Graphics g) {
		g.drawString("Press Arrow Up to jump. You can double jump.", FRAME_WIDTH - 650, 60);
		g.drawString("Press Arrow Down to duck.", FRAME_WIDTH - 550, 80);
	}
	private void drawScore(Graphics g) {
		// Write score and high score
		g.setColor(Color.GRAY);
		g.setFont(new Font("Courier New", Font.BOLD, 16));
		g.drawString("Score:" + points, FRAME_WIDTH - 140, 20);
		g.drawString("Highscore:" + highScore, FRAME_WIDTH - 300, 20);

		// Write lives
		g.drawString(livesBar, 10, 20);
	}

	public void refreshHealthBar() {
		StringBuilder result = new StringBuilder("Health: ");
		for (int i = 0; i < dino.getHealth(); i++) {
			result.append("|");
		}
		livesBar = result.toString();
	}

	// Spawns three single small cacti in the beginning of each game
	public List<Collidable> generateFirstObstacles() {
		List<Collidable> result = new ArrayList<>();
		result.add(new Cactus(FRAME_WIDTH, GROUND_LEVEL, 1));
		result.add(new Cactus(FRAME_WIDTH + 300, GROUND_LEVEL, 1));
		result.add(new Cactus(FRAME_WIDTH + 600, GROUND_LEVEL, 1));

		return result;
	}

	public Collidable generateObstacle() {
		// Get random number from 0 to 99
		int random = new Random().nextInt(0, 99);
		Collidable result;

		// Determine type of obstacle
		if (random < 25 ) // 0 - 24: Spawn single small cactus
			result = new Cactus(FRAME_WIDTH, GROUND_LEVEL, 1);

		else if (random < 40 ) // 25 - 39: Spawn two small cacti
			result = new Cactus(FRAME_WIDTH, GROUND_LEVEL, 2);

		else if (random < 50 ) // 40 - 49: Spawn three small cacti
			result = new Cactus(FRAME_WIDTH, GROUND_LEVEL, 3);

		else if (random < 70 ) // 50 - 69 (nice): Spawn single big cactus
			result = new BigCactus(FRAME_WIDTH, GROUND_LEVEL, 1);

		else if (random < 80 ) // 70 - 79: Spawn two big cacti
			result = new BigCactus(FRAME_WIDTH, GROUND_LEVEL, 2);

		else if (random < 85 ) // 80 - 84: Spawn four big cacti
			result = new BigCactus(FRAME_WIDTH, GROUND_LEVEL, 4);

		else if (random < 95 ) // 85 - 94: Spawn high-flying bird
			result = new Bird(FRAME_WIDTH, GROUND_LEVEL - 30);

		else                   // 95 - 99: Spawn low-flying bird
			result = new Bird(FRAME_WIDTH, GROUND_LEVEL);

		return result;

	}

	private void readHighScore() {
		try {
			Scanner read = new Scanner(new File("highscore"));
			highScore = Integer.parseInt(read.next());
			System.out.println("Read high score: " + highScore);
			read.close();
		} catch (FileNotFoundException e) {
			System.out.println("Didn't found high score file");
			highScore = 0;
		}
	}

	public void saveHighScore() {
		if (points > highScore) {
			highScore = points;
			try {
				FileWriter write = new FileWriter("highscore");
				write.write(Integer.toString(highScore));
				write.close();
			} catch (IOException e) {
				System.out.println("Couldn't access the highscore file");
			}
		}

	}

	//##############################
	// Getters and Setters
	//##############################

	public Dino getDino() {
		return dino;
	}

	public List<Collidable> getObstacles() {
		return obstacles;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
}
