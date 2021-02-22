package com.mygdx.tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.*;

public enum TileType {
	AIR(0, false, "blocks/air.png", "Air"),
	WALL(1, true, "blocks/wall2.png", "Wall"),
	WALLBR(2, true, "blocks/walld_bot.png", "Wall Corner BR"),
	WALLBL(3, true, "blocks/walld_bot2.png", "Wall Corner BL"),
	WALLTR(4, true, "blocks/walld_top.png", "Wall Corner TR"),
	WALLTL(5, true, "blocks/walld_top2.png", "Wall Corner TL"),
	DOOR(6, false, "blocks/door.png", "Door"),
	FOOD(7, false, "blocks/food2.png", "Food"),
	BFOOD(8, false, "blocks/bfood2.png", "BFood"),
	PLAYERSPAWN(9, false, "entities/pacman.png", "Player Spawner");
	
	private int id;
	private boolean collidable;
	private Texture img;
	private String name;
	
	TileType(int id, boolean collidable, String path, String name) {
		this.id = id;
		this.img = new Texture(Gdx.files.internal(path));
		this.collidable = collidable;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public boolean isCollidable() {
		return collidable;
	}

	public Texture getImg() {
		return img;
	}

	public String getName() {
		return name;
	}
	
	
}
