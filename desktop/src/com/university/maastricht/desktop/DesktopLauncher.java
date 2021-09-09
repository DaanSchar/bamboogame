package com.university.maastricht.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.university.maastricht.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
//		config = "Bamboo";
//		config.width = Game.WINDOW_WIDTH;
//		config.height = Game.WINDOW_HEIGHT;
		new Lwjgl3Application(new Game(), config);
	}
}
