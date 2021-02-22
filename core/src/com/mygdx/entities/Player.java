package com.mygdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.map.GameMap;

public class Player extends Entity {

	private int SPEED = 4;
	private final int CLIVES = 3;
	private int userSpeed = 1 * SPEED;
	private int speedx = 0, speedy = 0;
	private Texture skin;
	private Sprite sprite;
	private TextureRegion[] animationFrames = new TextureRegion[3];
	private TextureRegion[] energizedFrames = new TextureRegion[3];
	private TextureRegion[] deathFrames = new TextureRegion[6];
	private TextureRegion frameActual;
	private Animation<TextureRegion> animation;
	private int score = 0;
	private float atime = 0f;
	private int trydir = -1;
	private float timer = 0;
	private int mode = -1;
	private int id;
	private float mytimer = 0;
	
	public Player(int x, int y, GameMap map, int id) {
		super(x * map.TILESIZE, y * map.TILESIZE, EntityType.PLAYER, map);
		skin = new Texture(Gdx.files.internal("entities/pacman.png"));
		animationFrames[0] = new TextureRegion(new Texture(Gdx.files.internal("entities/pacman-sheet2.png")), 0, 0, 32,
				32);
		animationFrames[1] = new TextureRegion(new Texture(Gdx.files.internal("entities/pacman-sheet2.png")), 32, 0, 32,
				32);
		animationFrames[2] = new TextureRegion(new Texture(Gdx.files.internal("entities/pacman-sheet2.png")), 64, 0, 32,
				32);
		energizedFrames[0] = new TextureRegion(new Texture(Gdx.files.internal("entities/energized-sheet.png")), 0, 0, 32,
				32);
		energizedFrames[1] = new TextureRegion(new Texture(Gdx.files.internal("entities/energized-sheet.png")), 32, 0, 32,
				32);
		energizedFrames[2] = new TextureRegion(new Texture(Gdx.files.internal("entities/energized-sheet.png")), 64, 0, 32,
				32);
		deathFrames[0] = new TextureRegion(new Texture(Gdx.files.internal("entities/death-sheet.png")), 0, 0, 32,
				32);
		deathFrames[1] = new TextureRegion(new Texture(Gdx.files.internal("entities/death-sheet.png")), 32, 0, 32,
				32);
		deathFrames[2] = new TextureRegion(new Texture(Gdx.files.internal("entities/death-sheet.png")), 64, 0, 32,
				32);
		deathFrames[3] = new TextureRegion(new Texture(Gdx.files.internal("entities/death-sheet.png")), 96, 0, 32,
				32);
		deathFrames[4] = new TextureRegion(new Texture(Gdx.files.internal("entities/death-sheet.png")), 128, 0, 32,
				32);
		deathFrames[5] = new TextureRegion(new Texture(Gdx.files.internal("entities/death-sheet.png")), 160, 0, 32,
				32);
		animation = new Animation<TextureRegion>(0.05f, animationFrames);
		this.id = id;
		sprite = new Sprite(animationFrames[0]);
		sprite.setPosition(this.x, this.y);
	}

	@Override
	public void update(float delta) {
		if(map.ingame) {
			mode = 1;
		}
		if(mode != -1) {
			if(map.getMode() == 1) {
				if(this.mode == 0) {
					this.mode = 1;
				}
			} else {
				if(this.mode == 1) {
					this.mode = 0;
				}
			}
			if(map.playerlives == 0) {
				map.setGameover(false);
			}
			if(map.checkCollisionWith(this.x, this.y, getWidth(), getHeight(), id)) {
				if(mode == 0 || !map.eatable) {
					map.restart();
					mode = 2;
					atime = 0;
					map.mode = 2;
					if(map.playerlives > 0) {
						map.playerlives -= 1;
					}
					System.out.println("Vidas restantes: " + map.playerlives);
				} else if(mode == 1){
					mode = 3;
					map.mode = 3;
					hide();
				}
			}
			
			
			atime += Gdx.graphics.getDeltaTime();
			if(mode == 2) {
				frameActual = animation.getKeyFrame(atime, false);
			} else if(mode != 3){
				frameActual = animation.getKeyFrame(atime, true);
			}
			
			if(mode == 3) {
				mytimer += Gdx.graphics.getDeltaTime();
				System.out.println("timer: " + mytimer);
				if(mytimer  >= 1) {
					show();
					if(map.getEnergytimer() != 0) {
						map.mode = 1;
					}
					map.ingame = true;
					mytimer = 0;
				}
			}
			
			sprite.setRegion(frameActual);
			if(mode == 0) {
				animation = new Animation<TextureRegion>(0.05f, animationFrames);
				if(this.x % map.TILESIZE == 0 && this.y % map.TILESIZE == 0) {
					SPEED = 2;	
				}
			} else if (mode == 1){
				animation = new Animation<TextureRegion>(0.05f, energizedFrames);
				if(this.x % map.TILESIZE == 0 && this.y % map.TILESIZE == 0) {
					SPEED = 4;	
				}
			} else if (mode == 2) {
				animation = new Animation<TextureRegion>(0.2f, deathFrames);
				if(atime >= 1.5f) {
					if(map.playerlives == 0) {
						map.setGameover(true);
						map.ingame = false;
					} else {
						map.killPlayer();
					}
				}
				
			}
	//		Resetear la direccion buffereada
			if(trydir != -1) {
				timer += delta;
				if(timer >= 0.5) {
					trydir = -1;
					timer = 0;
				}
			} else {
				timer = 0;
			}
			sprite.setPosition(this.x, this.y);
			
			if(this.mode < 2) {
				map.checkTile(this.x, this.y, this.getWidth(), this.getHeight(), id);
				this.checkdir();
				this.updateSkin();		
				this.moveX(speedx);
				this.moveY(speedy);
				this.handleInput();
			}
		}
	}

	
	public void hide() {
		sprite.setSize(0, 0);
	}
	
	public void show() {
		sprite.setSize(EntityType.PLAYER.getWidth(), EntityType.PLAYER.getHeight());
	}
	
	private void handleInput() {
		if (Gdx.input.isKeyJustPressed(Keys.A)) {
			trydir = 0;
		}
		if (Gdx.input.isKeyJustPressed(Keys.D)) {
			trydir = 1;
		}
		if (Gdx.input.isKeyJustPressed(Keys.W)) {
			trydir = 2;
		}
		if (Gdx.input.isKeyJustPressed(Keys.S)) {
			trydir = 3;
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.TAB)) {
			score = map.getScore(0);
			if (!map.isDebugger()) {
				map.setDebugger(true);
			}
		}
	}
	
	protected void moveX(float f) {
		float newX = this.x + f;
		if (!map.checkcolision(newX, this.y, getWidth(), getHeight(), direction)) {
			this.x = newX;
		}
	}

	protected void moveY(float f) {
		float newY = this.y + f;
		if (!map.checkcolision(this.x, newY, getWidth(), getHeight(), direction)) {
			this.y = newY;
		}
	}

	private void updateSkin() {
		switch (direction) {
		case -1:
			sprite.setRotation(0);
			sprite.setFlip(false, false);
			speedx = 0;
			speedy = 0;
			break;
		case 0:
			sprite.setRotation(0);
			sprite.setFlip(true, false);
			speedx = -SPEED;
			speedy = 0;
			break;
		case 1:
			sprite.setRotation(0);
			sprite.setFlip(false, false);
			speedx = SPEED;
			speedy = 0;
			break;
		case 2:
			sprite.setFlip(false, false);
			sprite.setRotation(90);
			speedx = 0;
			speedy = SPEED;
			break;
		case 3:
			sprite.setFlip(false, false);
			sprite.setRotation(270);
			speedx = 0;
			speedy = -SPEED;
			break;
		}
	}

	private void checkdir() {
		switch (trydir) {
		case 0:
			if (this.y % map.TILESIZE == 0) {
				if (!map.checkcolision(this.x - getWidth(), this.y, getWidth(), getHeight(), trydir)) {
					direction = trydir;
					trydir = -1;
				}
			}
			break;
		case 1:
			if (this.y % map.TILESIZE == 0) {
				if (!map.checkcolision(this.x + getWidth(), this.y, getWidth(), getHeight(), trydir)) {
					direction = trydir;
					trydir = -1;
				}
			}
			break;
		case 2:
			if (this.x % map.TILESIZE == 0) {
				if (!map.checkcolision(this.x, this.y + getHeight(), getWidth(), getHeight(), trydir)) {
					direction = trydir;
					trydir = -1;
				}
			}
			break;
		case 3:
			if (this.x % map.TILESIZE == 0) {
				if (!map.checkcolision(this.x, this.y - getHeight(), getWidth(), getHeight(), trydir)) {
					direction = trydir;
					trydir = -1;
				}
			}
			break;
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		sprite.draw(batch);
		
	}

	public int getScore() {
		return score;
	}

	public int getInt() {
		return getScore();
	}

	public int getPlayerId() {
		return this.id;
	}

	@Override
	public float getX() {
		return this.x;
	}

	@Override
	public float getY() {
		return this.y;
	}

	
	
	
}
