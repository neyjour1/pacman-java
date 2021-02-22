package com.mygdx.utils;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.pacman.PacmanGame;
import com.mygdx.screens.PauseScreen;

public class SoundManager {
	public Sound[] effects = new Sound[5];
	public Music[] music = new Music[5];
	private float soundvolume = 0.5f;
	private boolean soundenabled = true;
	
	public SoundManager() {
		effects[0] = Gdx.audio.newSound(Gdx.files.internal("sounds/Pacman Eating Cherry.mp3"));
		effects[1] = Gdx.audio.newSound(Gdx.files.internal("sounds/Pacman Eating Ghost.mp3"));
		effects[2] = Gdx.audio.newSound(Gdx.files.internal("sounds/Pacman Dies.mp3"));
		effects[3] = Gdx.audio.newSound(Gdx.files.internal("sounds/Pacman Siren.mp3"));
		music[0] = Gdx.audio.newMusic(Gdx.files.internal("sounds/pacmanwaka.mp3"));
	}
	
	public void play(int id) {
		soundvolume = PauseScreen.getPREF_MUSIC_VOLUME();
		soundenabled = PauseScreen.isPREF_MUSIC_ENABLED();
		if(soundenabled) {
			effects[id].play(soundvolume);
		}
	}
	
	public void playMusic(int id) {
		if(music[id].isPlaying()) {
			music[id].stop();
			music[id].play();
		}
	}
	
}
