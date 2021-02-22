package com.mygdx.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


public class TextManager {
	public BitmapFont font;
	
	public TextManager() {
		font = new BitmapFont(Gdx.files.internal("font/emulogic.fnt"),
                Gdx.files.internal("font/emulogic.png"), false);
	}
}
