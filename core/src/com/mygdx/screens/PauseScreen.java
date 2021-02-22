package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.mygdx.pacman.PacmanGame;
import com.mygdx.utils.TextManager;

public class PauseScreen implements Screen {
	private TextManager text = new TextManager();
	private static float PREF_MUSIC_VOLUME = 0.1f;
	private static boolean PREF_MUSIC_ENABLED = true;
	private Stage stage;
	private PacmanGame game;
	private Skin skin;
	private SpriteBatch batch = new SpriteBatch();
	
	public PauseScreen(PacmanGame game) {
		this.game = game;
		this.stage = new Stage();
		skin = new Skin(Gdx.files.internal("quantum-horizon/skin/quantum-horizon-ui.json"));
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void show() {
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		TextButton resume = new TextButton("Resume", skin);
		final Slider volumeSlider = new Slider(0f, 1f, 0.1f, false, skin);
		final CheckBox volumeEnabled = new CheckBox("Sound", skin);
		volumeEnabled.setChecked(PREF_MUSIC_ENABLED);
		volumeSlider.setValue(PREF_MUSIC_VOLUME);
		table.add(resume).fillX().uniformX();
		resume.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(game.gamescreen);
			}
		});
		volumeSlider.addListener( new EventListener() {
	  		@Override
			public boolean handle(Event event) {
	  			PREF_MUSIC_VOLUME = volumeSlider.getValue();
                return false;
	  		}
		});
		
		table.row();
		table.add(volumeSlider);
		table.row();
		table.add(volumeEnabled);
		
		volumeEnabled.addListener( new ChangeListener() {
			@Override
	  		public void changed(ChangeEvent event, Actor actor) {
	  			boolean enabled = volumeEnabled.isChecked();
	  			System.out.println("Volume enabled: " + volumeEnabled.isChecked());
	  			PREF_MUSIC_ENABLED = enabled;
	  		}
		});
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
		stage.draw();
		batch.begin();
		text.font.setColor(Color.YELLOW);
		text.font.getData().setScale(2);
		text.font.draw(batch, "PACMAN", Gdx.graphics.getWidth()/2-170, Gdx.graphics.getHeight()-20);
		batch.end();
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
		stage.dispose();
		skin.dispose();
	}

	public static float getPREF_MUSIC_VOLUME() {
		return PREF_MUSIC_VOLUME;
	}

	public static boolean isPREF_MUSIC_ENABLED() {
		return PREF_MUSIC_ENABLED;
	}
	
	
	
}
