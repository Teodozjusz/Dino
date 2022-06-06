package online.adambem.dinoproject.mechanics;

import online.adambem.dinoproject.gameobjects.Dino;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameKeyListener extends KeyAdapter {

	private Dino dino;
	private GamePanel panel;

	public GameKeyListener(GamePanel panel) {
		this.dino = panel.getDino();
		this.panel = panel;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			// Arrow up - jump
			case KeyEvent.VK_UP:
				dino.jump(false);
				break;
			// Arrow down - start ducking
			case KeyEvent.VK_DOWN:
				dino.setDinoDuck(true);
				break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			// Arrow down - stop ducking
			case KeyEvent.VK_DOWN:
				dino.setDinoDuck(false);
				break;
			// Restart game
			default:
				panel.setup(true);
				break;
		}
	}
}
