package online.adambem.dinoproject.mechanics;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

	private final int FRAME_WIDTH = 848;
	private final int FRAME_HEIGHT = 200;

	GamePanel panel;

	public GameWindow() throws HeadlessException {
		super("Dino the Java Archeologist");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(FRAME_WIDTH,FRAME_HEIGHT);
		this.setResizable(false);
		this.setVisible(true);

		panel = new GamePanel(FRAME_WIDTH, FRAME_HEIGHT);
		this.add(panel);
		this.addKeyListener(new GameKeyListener(panel));
	}

}
