package com.mygdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.map.GameMap;

public class GhostB extends Ghost {

//	private static Texture img = new Texture(Gdx.files.internal("entities/ghost2.png"));
	private static TextureRegion[] skinframe = new TextureRegion[4];
	static {
		skinframe[0] = new TextureRegion(new Texture(Gdx.files.internal("entities/orange.png")), 0,0,32,32);
		skinframe[1] = new TextureRegion(new Texture(Gdx.files.internal("entities/orange.png")), 32,0,32,32);
		skinframe[2] = new TextureRegion(new Texture(Gdx.files.internal("entities/orange.png")), 64,0,32,32);
		skinframe[3] = new TextureRegion(new Texture(Gdx.files.internal("entities/orange.png")), 96,0,32,32);
	}
	
	public GhostB(int x, int y, GameMap map) {
		super(x, y, map, skinframe,1);
	}

	@Override
	public void chaseMode() {

	}

	@Override
	public void frightenedMode() {

	}

}
