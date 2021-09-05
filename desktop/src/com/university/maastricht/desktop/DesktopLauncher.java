package com.university.maastricht.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.university.maastricht.ui.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Bamboo";
		config.width = 1280;
		config.height = 720;
		new LwjglApplication(new Game(), config);
	}
}
