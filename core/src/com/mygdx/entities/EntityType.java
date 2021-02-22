package com.mygdx.entities;

public enum EntityType {

	PLAYER(0, 32, 32, "Player"),
	GHOST(1, 32, 32, "Ghost");
	
	private int id, height, width;
	private String name;
	
	EntityType(int id, int width, int height, String name){
		this.id = id;
		this.width = width;
		this.height = height;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public String getName() {
		return name;
	}
	
	
	
}
