package com.university.maastricht.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.university.maastricht.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Bamboo";
		config.width = 1000;
		config.height = 1000;
		new LwjglApplication(new Game(), config);
	}
}
