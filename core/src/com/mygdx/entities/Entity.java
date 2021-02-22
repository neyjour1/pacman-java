package com.mygdx.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.map.GameMap;

public abstract class Entity {

	protected float x, y;
	protected EntityType type;
	protected int direction;
	protected GameMap map;
	protected boolean isDestroyed = false;
	protected int color;
	protected int subtype;
	protected int mode;
	protected boolean justspawned = false;
	// Direcciones:
//	0 - Izquierda
//	1 - Derecha
//	2 - Arriba
//	3 - Abajo
	// *

	public Entity(float x, float y, EntityType type, GameMap map, int subtype) {
		this.x = x;
		this.y = y;
		this.type = type;
		this.map = map;
		this.subtype = subtype;
		this.direction = -1;
	}


	public int getMode() {
		return mode;
	}


	public Entity(float x, float y, EntityType type, GameMap map) {
		this(x,y,type,map, -1);
	}

	
	public boolean isDestroyed() {
		return isDestroyed;
	}

	public void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	public abstract void render(SpriteBatch batch);

	public void update(float delta) {

	}

	public float getX() {
		return this.y;
	}

	public float getY() {
		return this.y;
	}

	public int getWidth() {
		return this.type.getWidth();
	}

	public int getHeight() {
		return this.type.getHeight();
	}

	public EntityType getType() {
		return type;
	}

	public void setX(float x) {
		this.x = x * map.TILESIZE;
	}

	public void setY(float y) {
		this.y = y * map.TILESIZE;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	
	
	public void setJustspawned(boolean justspawned) {
		this.justspawned = justspawned;
	}


	public int getInt() {
		return 0;
	}

	public int getId() {
		return type.getId();
	}

	public int getSubtype() {
		return subtype;
	}
	
	public abstract int getPlayerId();
}
