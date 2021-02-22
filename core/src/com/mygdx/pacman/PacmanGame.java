package com.mygdx.pacman;

import com.badlogic.gdx.Game;
import com.mygdx.screens.MainScreen;
import com.mygdx.screens.PauseScreen;

public class PacmanGame extends Game {
	public MainScreen gamescreen;
	public PauseScreen pausescreen;
	
	public void create() {
		gamescreen = new MainScreen(this);
		pausescreen = new PauseScreen(this);
		this.setScreen(gamescreen);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
	}
}
