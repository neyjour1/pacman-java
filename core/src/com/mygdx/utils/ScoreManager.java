package com.mygdx.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ScoreManager {

	private TextureRegion[] texture = new TextureRegion[6];
	private boolean hidden = true;
	private Sprite sprite;
	public ScoreManager() {
		texture[0] = new TextureRegion(new Texture(Gdx.files.internal("score/200.png")));
		texture[1] = new TextureRegion(new Texture(Gdx.files.internal("score/400.png")));
		texture[2] = new TextureRegion(new Texture(Gdx.files.internal("score/600.png")));
		texture[3] = new TextureRegion(new Texture(Gdx.files.internal("score/800.png")));
		texture[4] = new TextureRegion(new Texture(Gdx.files.internal("score/100w.png")));
		texture[5] = new TextureRegion(new Texture(Gdx.files.internal("score/300w.png")));
		sprite = new Sprite(texture[0]);
		sprite.setScale(2);
		sprite.setSize(0, 0);
	}
	
	public TextureRegion[] getTexture() {
		return texture;
	}

	public void setTexture(int index) {
		sprite.setSize(32, 32);
		sprite.setRegion(texture[index]);
		hidden = false;
	}
	
	public void hide() {
		sprite.setSize(0, 0);
		hidden = true;
	}
	
	public void setX(float x) {
		sprite.setX(x);
	}
	
	public void setY(float y) {
		sprite.setY(y);
	}
	
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}

	public boolean isHidden() {
		return hidden;
	}
	
	
}
