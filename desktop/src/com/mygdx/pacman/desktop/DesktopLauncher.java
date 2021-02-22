package com.mygdx.pacman.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.pacman.PacmanGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new PacmanGame(), config);
		config.title = "Pacman LibGDX";
		config.foregroundFPS = 60;
		config.backgroundFPS = 60;
		config.width = 736;
		config.height = 744;
		config.resizable = false;
		config.addIcon("icon.png", FileType.Internal);
		config.addIcon("icon128.png", FileType.Internal);
	}
}
