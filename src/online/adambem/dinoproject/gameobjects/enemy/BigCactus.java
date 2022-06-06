package online.adambem.dinoproject.gameobjects.enemy;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;

public class BigCactus extends Cactus{

	public BigCactus(int x1, int y2, int quantity) {
		super(x1, y2);
		try {
			switch (quantity) {
				case 2:
					super.setImage(ImageIO.read( new FileInputStream("./Images/Big2.png")));
					break;
				case 4:
					super.setImage(ImageIO.read( new FileInputStream("./Images/Big4.png")));
					break;
				default:
					super.setImage(ImageIO.read( new FileInputStream("./Images/Big1.png")));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
