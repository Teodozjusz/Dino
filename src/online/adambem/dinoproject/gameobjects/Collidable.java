package online.adambem.dinoproject.gameobjects;

public interface Collidable extends Renderable {

	int getX1();
	int getX2();
	int getY1();
	int getY2();

	default boolean collides(Collidable object) {
		return getX2() >= object.getX1() &&
				getX1() <= object.getX2() &&
				getY1() <= object.getY2() &&
				getY2() >= object.getY1();
	}

}
