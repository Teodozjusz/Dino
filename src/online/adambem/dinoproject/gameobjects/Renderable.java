package online.adambem.dinoproject.gameobjects;

import java.awt.*;

public interface Renderable {

	void switchTexture();
	void move();
	void render(Graphics g);
}
