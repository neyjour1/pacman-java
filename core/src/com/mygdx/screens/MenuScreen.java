package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.pacman.PacmanGame;

public class MenuScreen implements Screen{

	private PacmanGame game;
	private Stage stage;
	
	public MenuScreen(PacmanGame game) {
		this.game = game;
		this.stage = new Stage();
		Gdx.input.setInputProcessor(this.stage);
	}

	@Override
	public void show() {
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		Skin skin = new Skin(Gdx.files.internal("skin/asd.json"));
		TextButton sgame = new TextButton("Start Game", skin);
		TextButton edit = new TextButton("Make your own MAP", skin);
		TextButton quit = new TextButton("Exit", skin);
		table.add(sgame).fillX().uniformX();
		table.row().pad(10, 0, 10, 0);
		table.add(edit).fillX().uniformX();
		table.row();
		table.add(quit).fillX().uniformX();
		sgame.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(new MainScreen(game));
			}
		});
	}

	@Override
	public void render(float delta) {
		stage.draw();
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
		
	}
	
}
