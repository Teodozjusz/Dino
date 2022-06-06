package online.adambem.dinoproject;

import online.adambem.dinoproject.mechanics.GameWindow;

import javax.swing.*;

public class Main {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> new GameWindow());

	}

}
