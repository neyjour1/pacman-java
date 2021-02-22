package com.mygdx.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.map.GameMap;
import com.mygdx.tiles.TileType;

public abstract class Ghost extends Entity {

	protected int SPEED = 2;
	protected int speedx = 0, speedy = 0;
	protected TextureRegion[] img;
	protected Texture img2 = new Texture(Gdx.files.internal("entities/frightened2.png"));
	private TextureRegion[] frightenedFrames = new TextureRegion[2];
	protected int direction = -1;
	protected ArrayList<Integer> directions = new ArrayList<Integer>();
	private Animation<TextureRegion> animation;
	private int currentimg = 0;
	private float atimer = 0;
	private TextureRegion frameactual;
	private ShapeRenderer sr;

	public Ghost(int x, int y, GameMap map, TextureRegion[] img, int subtype) {
		super(x * map.TILESIZE, y * map.TILESIZE, EntityType.GHOST, map, subtype);
		this.img = img;
		directions = checkAvailableDirections();
		direction = directions.get((int) (Math.random() * directions.size()));
		frightenedFrames[0] = new TextureRegion(new Texture(Gdx.files.internal("entities/frightened-sheet.png")), 0, 0,
				32, 32);
		frightenedFrames[1] = new TextureRegion(new Texture(Gdx.files.internal("entities/frightened-sheet.png")), 32, 0,
				32, 32);
		animation = new Animation<TextureRegion>(0.3f, frightenedFrames);
		if (map.getEnergytimer() != 0) {
			justspawned = true;
		}
		mode = 2;
		sr = new ShapeRenderer();
	}

	@Override
	public void render(SpriteBatch batch) {
		sr.begin(ShapeType.Filled);
		sr.end();
		if (mode != 3) {
			batch.draw(img[currentimg], this.x, this.y, getWidth(), getHeight());
		} else {
			if (map.getEnergytimer() >= 4) {
				atimer += Gdx.graphics.getDeltaTime();
				frameactual = animation.getKeyFrame(atimer, true);
				batch.draw(frameactual, this.x, this.y, getWidth(), getHeight());
			} else {
				batch.draw(img2, this.x, this.y, getWidth(), getHeight());
			}
		}
	}

	@Override
	public int getMode() {
		return this.mode;
	}

	@Override
	public void update(float delta) {
		if (map.getMode() == 1 || map.getMode() == 3) {
			if (this.mode != 3 && !justspawned) {
				this.mode = 3;
				SPEED = 1;
			}
		} else {
			this.mode = 2;
			SPEED = 2;
		}
		if (map.getEnergytimer() == 0) {
			justspawned = false;
		}
		directions = checkAvailableDirections();
		if (mode != 2) {
			if (!directions.contains(direction) && directions.size() != 0) {
				int random = (int) (Math.random() * directions.size());
				System.out.println("Directions: " + (directions));
				System.out.println("Random: " + random);
				direction = directions.get(random);
			}
		} else {
			if(this.x % 2 == 1) this.x += 1;
			if(this.y % 2 == 1) this.y += 1;
			scatterMode();
		}
		
		handleDirection();
		moveX(speedx);
		moveY(speedy);
	}

	public void handleDirection() {
		switch (direction) {
		case -1:
			speedx = 0;
			speedy = 0;
			break;
		case 0:
			speedx = -SPEED;
			speedy = 0;
			this.currentimg = 1;
			break;
		case 1:
			speedx = SPEED;
			speedy = 0;
			this.currentimg = 0;
			break;
		case 2:
			speedx = 0;
			speedy = SPEED;
			this.currentimg = 2;
			break;
		case 3:
			speedx = 0;
			speedy = -SPEED;
			this.currentimg = 3;
			break;
		}
	}

	public abstract void chaseMode();

	public void scatterMode() {
		
//		System.out.println(directions);
		
		if (directions.size() == 1) {
			direction = directions.get(0);
		} else if (directions.size() == 2) {
			for (int temp : directions) {
				if (direction == 0 || direction == 1) {
					if (temp > 1) {
						direction = temp;
						break;
					}
				}
				if (direction == 2 || direction == 3) {
					if (temp < 2) {
						direction = temp;
						break;
					}
				}

			}
		} else if (directions.size() == 3) {
			int[] storedirection = new int[directions.size() - 1];
			int cont = 0;
			for (int temp : directions) {
				if (cont < storedirection.length) {
					if (direction == 0 || direction == 1) {
						if (temp == direction || temp > 1) {
							storedirection[cont] = temp;
							cont += 1;
						}
					} else if (direction == 2 || direction == 3) {
						if (temp == direction || temp < 2) {
							storedirection[cont] = temp;
							cont += 1;
						}
					}
				}
			}
			int random = (int) (Math.random() * (storedirection.length));
			direction = storedirection[random];
		} else if (directions.size() == 4) {
			int[] storedirection = new int[directions.size() - 1];
			int cont = 0;
			for (int temp : directions) {
				if (cont < storedirection.length) {
					if (Math.abs(temp - direction) != 1) {
						storedirection[cont] = temp;
						cont += 1;
					}
				}
			}
			int random = (int) (Math.random() * (storedirection.length));
			direction = storedirection[random];
		}
	}

	public abstract void frightenedMode();

	public int getManhattanDistance(int x, int y) {
		int col = (int) this.x / map.TILESIZE;
		int row = (int) this.y / map.TILESIZE;
		int col2 = (int) x / map.TILESIZE;
		int row2 = (int) y / map.TILESIZE;
		int distance = Math.abs(col2 - col) + Math.abs(row2 - row);
		return distance;
	}

	public void moveX(float f) {
		float newX = this.x + f;
		if (newX + getWidth() < map.getMapwidth() * map.TILESIZE && newX > 0) {
			this.x = newX;
		} else {
			if (direction == 0) {
				direction = 1;
			} else if (direction == 1) {
				direction = 0;
			}
		}
	}

	public void moveY(float f) {
		float newY = this.y + f;
		if (newY + getHeight() < map.getMapheight() * map.TILESIZE && newY > 0) {
			this.y = newY;
		} else {
			if (direction == 2) {
				direction = 3;
			} else if (direction == 3) {
				direction = 2;
			}
		}

	}

	public ArrayList<Integer> checkAvailableDirections() {
		
		ArrayList<Integer> directions = new ArrayList<Integer>();
		int col = (int) (this.x + getWidth()/2) / map.TILESIZE;
		int row = (int) (this.y + getHeight()/2) / map.TILESIZE;
		System.out.println("X: " + x + " y: " + y);
		if ((int)y % map.TILESIZE == 0 && (int)x % map.TILESIZE == 0) {
			System.out.println("Entre al mod :D");
			if (!map.getTileAt(col - 1, row).isCollidable()) {
				
				directions.add(0);
			}
			if (!map.getTileAt(col + 1, row).isCollidable()) {
				directions.add(1);
			}
			if (!map.getTileAt(col, row + 1).isCollidable()) {
				directions.add(2);
			}
			if (!map.getTileAt(col, row - 1).isCollidable()) {
				directions.add(3);
			}
		}
		System.out.println("Devolviendo: " + directions);
		return directions;
	}

	@Override
	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}

	@Override
	public int getPlayerId() {
		return 0;
	}

}
