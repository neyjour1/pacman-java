package com.mygdx.tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Tile {

	private Texture[] img;
	public Tile(TileType type, String texture) {
		this.img[0] = new Texture(Gdx.files.internal(texture));
	}

	public Texture[] getImg() {
		return img;
	}
	
	
	
}
