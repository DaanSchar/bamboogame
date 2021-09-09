package com.university.maastricht.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.university.maastricht.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Bamboo";
		config.width = Game.WINDOW_WIDTH;
		config.height = Game.WINDOW_HEIGHT;
		new LwjglApplication(new Game(), config);
	}
}
