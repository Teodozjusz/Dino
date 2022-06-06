package online.adambem.dinoproject.mechanics;

import online.adambem.dinoproject.gameobjects.Collidable;
import online.adambem.dinoproject.gameobjects.Dino;

import java.util.List;

public class GameLoop extends Thread {

	private final int FRAMERATE = 60;
	private final int FRAME_TIME = 1000 / FRAMERATE;

	GamePanel panel;

	// Is game still running?
	private boolean gameRuns;

	// Player character
	private Dino dino;

	// List of obstacles
	private List<Collidable> obstacles;

	public GameLoop(GamePanel panel) {
		this.panel = panel;

		this.gameRuns = true;

		this.dino = this.panel.getDino();
		this.obstacles = this.panel.getObstacles();
	}

	@Override
	public void run() {

		while (gameRuns) {
			addPoint();

			// Animations for player and obstacles (birds)
			if (panel.getPoints() % 8 == 0) {
				panel.getDino().switchTexture();
			}

			if (panel.getPoints() % 16 == 0) {
				for (Collidable obstacle : obstacles) {
					obstacle.switchTexture();
				}
			}

			// Player movement
			dino.move();

			// Obstacle movement and collision detection
			for (Collidable obstacle : obstacles) {
				obstacle.move();

				// if players hits an obstacle the game ends
				if (dino.collides(obstacle) && !dino.hasImmunity()) {
					dino.damage();
					panel.refreshHealthBar();
					dino.setImmunityTime(60);
					dino.jump(true);
					if (dino.getHealth() == 0) {
						gameRuns = false;
						panel.saveHighScore();
						break;
					}
				}
			}

			// When obstacle is out of screen, it gets deleted
			for (int i = 0; i < obstacles.size(); i++) {
				Collidable current = obstacles.get(i);

				if (current.getX1() < -50) {
					obstacles.remove(i);
					obstacles.add(panel.generateObstacle());
				}
			}
			panel.repaint();

			try {
				Thread.sleep(FRAME_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private void addPoint() {
		int points = panel.getPoints();
		panel.setPoints(points + 1);
	}

	//##############################
	// Getters and Setters
	//##############################

	public boolean isGameRuns() {
		return gameRuns;
	}
}
