package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.pacman.PacmanGame;
import com.mygdx.map.GameMap;

public class MainScreen implements Screen {
	
	private GameMap map;
	SpriteBatch batch;
	private PacmanGame game;
	private boolean tab = false;
	private ShapeRenderer shape;
	private final int ROWS = 22, COLUMNS = 23, TILESIZE = 32;
	private static final int RUNNING = 0;
	private static final int PAUSE = 1;
	private int instance = 0;
	
	public MainScreen(PacmanGame game) {
		batch = new SpriteBatch();
		map = new GameMap(batch, this);
		this.game = game;
		shape = new ShapeRenderer();
	}
	
	@Override
	public void show() {
		instance = RUNNING;
	}

	public void toggleGrid() {
		if(tab) {
		    shape.begin(ShapeType.Line);
		    shape.setColor(Color.GRAY);
		    for (int i = 0; i < ROWS; i++) {
		        shape.line(0, i*32, COLUMNS*TILESIZE, i*32);
		    }
		    for (int i = 0; i < COLUMNS; i++) {
		        shape.line(i*32, 0, i*32, ROWS*TILESIZE);
		    }
		    shape.end();
			}
	}
	
	@Override
	public void render(float delta) {
//		System.out.println("instancia: " + instance);
		if(instance == RUNNING) {
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			map.update(Gdx.graphics.getDeltaTime());
			batch.begin();
			map.render();
			batch.end();
		}
		if(Gdx.input.isKeyJustPressed(Keys.TAB)) {
			tab = !tab;
		}
		toggleGrid();
		if (Gdx.input.isKeyJustPressed(Keys.ALT_LEFT)) {
			game.setScreen(new MapEditorScreen(game));
		}
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			instance = PAUSE;
			game.setScreen(new PauseScreen(game));
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	public void setInstance(int instance) {
		this.instance = instance;
	}

	
}
