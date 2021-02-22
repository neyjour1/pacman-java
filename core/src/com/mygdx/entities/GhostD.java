package com.mygdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.map.GameMap;

public class GhostD extends Ghost {

	private static TextureRegion[] skinframe = new TextureRegion[4];
	static {
		skinframe[0] = new TextureRegion(new Texture(Gdx.files.internal("entities/pink.png")), 0,0,32,32);
		skinframe[1] = new TextureRegion(new Texture(Gdx.files.internal("entities/pink.png")), 32,0,32,32);
		skinframe[2] = new TextureRegion(new Texture(Gdx.files.internal("entities/pink.png")), 64,0,32,32);
		skinframe[3] = new TextureRegion(new Texture(Gdx.files.internal("entities/pink.png")), 96,0,32,32);
	}
	
	
	public GhostD(int x, int y, GameMap map) {
		super(x, y, map, skinframe, 3);
	}

	@Override
	public void chaseMode() {

	}

	@Override
	public void frightenedMode() {

	}

}
