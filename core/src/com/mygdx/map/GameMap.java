package com.mygdx.map;
import java.io.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.entities.Entity;
import com.mygdx.entities.EntityType;
import com.mygdx.entities.GhostA;
import com.mygdx.entities.GhostB;
import com.mygdx.entities.GhostC;
import com.mygdx.entities.GhostD;
import com.mygdx.entities.Player;
import com.mygdx.screens.MainScreen;
import com.mygdx.tiles.Tile;
import com.mygdx.tiles.TileType;
import com.mygdx.utils.ScoreManager;
import com.mygdx.utils.SoundManager;
import com.mygdx.utils.TextManager;

public class GameMap{

	public final int TILESIZE = 32;
	@SuppressWarnings("unused")
	private static final int ROW = 22, COLUMNS = 23;
	private static final int TIME = 3;
	private static final int GHOSTS = 4;
	private static String[] mapdata = new String[ROW];
	private SpriteBatch batch;
	protected ArrayList<Entity> entities;
	private ArrayList<Integer> score;
	private boolean debugger = false;
	private int mapwidth, mapheight;
	private boolean starting = true;
	@SuppressWarnings("unused")
	private MainScreen screen;
	private SoundManager smanager = new SoundManager();
	private int playerX = 0, playerY = 0;
	private int[] ghostX = new int[GHOSTS], ghostY = new int[GHOSTS];
	public boolean ingame = false;
	private boolean gameover = false;
	private int initialfood = 0;
	private int food = 0;
	private HashMap<String,TileType> tilemap;
	public int mode = 0;
	private float timer = 0;
	private float energytimer = 0;
	private boolean[] ghost = new boolean[4];
	public int playerlives = 3;
	private float ghostADead = 0, ghostBDead = 0, ghostCDead = 0, ghostDDead = 0;
	private float myTimer = 0;
	private int[] scorepoint = new int[6];
	private ScoreManager myScore = new ScoreManager();
	private float startTimer;
	private int contadorfantasmas = 0;
	private float scoretimer;
	private TextManager textmanager = new TextManager();
	public boolean eatable;

	public GameMap(SpriteBatch batch, MainScreen screen) {
		this.screen = screen;
		mapdata = getDefaultmap();
//		mapdata = readMap();
		if(mapdata == null) {
			mapdata = getDefaultmap();
		}
		mapwidth = mapdata[0].toCharArray().length;
		mapheight = mapdata.length;
		
		for(int x = 0; x < 4; x++) {
			ghostX[x] = -1;
			ghostY[x] = -1;
		}
		for(int x = 0; x < 4; x++) {
			ghost[x] = false;
		}
		for(int x = 0; x < 6; x++) {
			scorepoint[x] = -1;
		}
		
		playerX = getXPosition('P');
		playerY = getYPosition('P');
		
		ghostX[0] = getXPosition('R');
		ghostY[0] = getYPosition('R');
		
		ghostX[1] = getXPosition('B');
		ghostY[1] = getYPosition('B');
		
		ghostX[2] = getXPosition('G');
		ghostY[2] = getYPosition('G');
		
		ghostX[3] = getXPosition('Q');
		ghostY[3] = getYPosition('Q');
		
		fillMap();
		this.batch = batch;
		entities = new ArrayList<Entity>();
		score = new ArrayList<Integer>();
		
		if(playerX != -1 && playerY != -1) {
			entities.add(new Player(playerX,playerY,this,0));
			score.add(0);
		}
		
		addGhosts();
		initialfood = getFood();
		wait(3);
		
	}
	
	private void addGhosts() {
		if(ghostX[0] != -1 && ghostY[0] != -1) {
			entities.add(new GhostA(ghostX[0], ghostY[0], this));
			ghost[0] = true;
		}
		if(ghostX[1] != -1 && ghostY[1] != -1) {
			entities.add(new GhostB(ghostX[1], ghostY[1], this));
			ghost[1] = true;
		}
		if(ghostX[2] != -1 && ghostY[2] != -1) {
			entities.add(new GhostC(ghostX[2], ghostY[2], this));
			ghost[2] = true;
		}
		if(ghostX[3] != -1 && ghostY[3] != -1) {
			entities.add(new GhostD(ghostX[3], ghostY[3], this));
			ghost[3] = true;
		}
	}
	
	private void wait(int seconds) {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				ingame = true;
				mode = 0;
			}
		};
		timer.schedule(task, seconds*1000);
	}
	
	private String[] readMap(){
	  String mapFile = System.getProperty("user.dir") + "/savemap.txt";
	  String line = null;
	  String temp[] = new String[ROW];
	  int y = 0;
	  try{
		  FileReader f = new FileReader(mapFile);  
		  BufferedReader br = new BufferedReader(f);
		  while ((line = br.readLine()) != null){
			  temp[y] = line; 
			  y += 1;
		  }
		  br.close();
		  f.close();
		  System.out.println("Exito!");
		  return temp;
	  } catch(Exception e) {
		  System.out.println(e);
		  return null;
	  }
	}

	private int getFood() {
		int foodn = 0;
		for(int i = 0; i < mapheight; i++) {
			char[] c = mapdata[i].toCharArray();
			for(int j = 0; j<c.length;j++) {
				if(c[j] == 'C' || c[j] == 'D') {
					foodn += 1;
				}
			}
		}
		return foodn;
	}
	
	  private String[] getDefaultmap(){
	      String[] temp = {
	        "00000000000000000000000",
	        "0CCCCCCCCCC0CCCCCCCCCC0",
	        "0D00C00000C0C0000C000D0",
	        "0CCCCCCCCCCCCCCCCCCCCC0",
	        "0C00C0C0000000000C000C0",
	        "0CCCC0CCCCCCCCCCCCCCCC0",
	        "0000C000C00000C000C0000",	
	        "1110C011111R111110C0111",
	        "0000C0101001001010C0000",
	        "2111C11010GBQ01010C1112",
	        "0000C0101000001010C0000",
	        "1110C0111111111110C0111",
	        "0100C00100CCC0C000C0000",
	        "0000CCCCC000C0CCC0CCCC0",
	        "0CCCC000CCCCC0C0C0CC0C0",
	        "0C00CCC0C000C0C0CCC00C0",
	        "0DC0C0C0CCPCC0C0C0C0CD0",
	        "00C0C0C00000C0C0C0C0C00",
	        "0CCCC0CCC0CCC0CCCCCCCC0",
	        "0C000000C0C0000000000C0",
	        "0CCCCCCCCCCCCCCCCCCCCC0",
	        "00000000000000000000000",
	      };
	      return temp;
	  }

	  public int getMapwidth() {
		return mapwidth;
	}

	public int getMapheight() {
		return mapheight;
	}

	  private int getXPosition(char block) {
		  for(int y = 0; y < mapheight; y++) {
			  for(int x = 0; x < mapwidth; x++) {
				  char c = mapdata[y].charAt(x);
				  if (c == block) {
					  return x;
				  }
			  }  
		  }
		  return -1;
	  }
	  
	  private int getYPosition(char block) {
		  for(int y = 0; y < mapheight; y++) {
			  for(int x = 0; x < mapwidth; x++) {
				  char c = mapdata[y].charAt(x);
				  if (c == block) {
					  return Math.abs(y-mapheight+1);
				  }
			  }  
		  }
		  return -1;
	  }
	  
	  private void fillMap() {
		  String temp = new String(); 
		  TileType value;
		  temp = null;
		  tilemap = new HashMap<String,TileType>();
		  for(int m=0;m<mapheight;m++) {
			  char[] c = mapdata[m].toCharArray();
			  for(int n=0;n<c.length;n++) {
				  value = null;
				  temp = String.valueOf(n) + "," + String.valueOf(Math.abs(m-mapheight+1));
				  if(c[n] == '0') {
					  value = TileType.WALL;
				  }
				  if(c[n] == 'C') {
					  value = TileType.FOOD;
				  }
				  if(c[n] == 'D') {
					  value = TileType.BFOOD;
				  }
				  if(c[n] == '1') {
					  value = TileType.AIR;
				  }
				  if(c[n] == '2') {
					  value = TileType.DOOR;
				  }
				  if(c[n] == 'P') {
					  value = TileType.AIR;
				  }
				  if(c[n] == 'G') {
					  value = TileType.AIR;
				  }
				  if(c[n] == 'R') {
					  value = TileType.AIR;
				  }
				  if(c[n] == 'B') {
					  value = TileType.AIR;
				  }
				  if(c[n] == 'Q') {
					  value = TileType.AIR;
				  }
				  tilemap.put(temp, value);
			  }
		  }
	  }
	   
	  public void render() {
		  String guilives = "Lives: " + playerlives;
		  if(!myScore.isHidden()) {
			  scoretimer += Gdx.graphics.getDeltaTime();
		  } else {
			  scoretimer = 0;
		  }
		  generateMap();
		  if(starting) {
			  textmanager.font.setColor(Color.RED);
			  textmanager.font.getData().setScale(0.5f);
			  textmanager.font.draw(batch, "Player1", (COLUMNS-8)*TILESIZE+16, (ROW+1)*TILESIZE-16);
			  textmanager.font.draw(batch, "Ready!", (COLUMNS-3)*TILESIZE-8, (ROW+1)*TILESIZE-16);
		  } else {
			  textmanager.font.setColor(Color.WHITE);
			  textmanager.font.getData().setScale(0.5f);
			  textmanager.font.draw(batch, guilives, (COLUMNS-5)*TILESIZE+16, (ROW+1)*TILESIZE-16);
		  }
		  for(Entity entity : entities) {
			  entity.render(batch);
		  }
		  myScore.draw(batch);
		  if(scoretimer >= 2f) {
			  myScore.hide();
			  scoretimer = 0;
		  }
		  String guiscore = "Score: " + score.get(0);
		  textmanager.font.getData().setScale(1);
		  textmanager.font.setColor(Color.YELLOW);
		  textmanager.font.draw(batch, "pacman", COLUMNS/2*TILESIZE-90, (ROW+1)*TILESIZE);
		  textmanager.font.setColor(Color.WHITE);
		  textmanager.font.getData().setScale(0.5f);
		  textmanager.font.draw(batch, guiscore, 10, (ROW+1)*TILESIZE-16);
	  }
	  
	  public void playerEnergized(int seconds) {
		if(mode == 1) {
			if(ingame) {
				timer += Gdx.graphics.getDeltaTime();
				energytimer = timer;
			}
			System.out.println("Timer: " + timer);
			if(energytimer >= seconds) {
				mode = 0;
				energytimer = 0;
				contadorfantasmas = 0;
				System.out.println("Exito! Mode: " + mode);
			}
		}
		
	  }
	  
	  public float getEnergytimer() {
		return energytimer;
	}

	public void restart() {
		  ingame = false;
		  myTimer = 0;
		  startTimer = 0;
		  mode = 0;
		  int index = 0;
		  for(Entity entity : entities) {
			  if(entity.getType() == EntityType.GHOST) {
				  entity.setDestroyed(true);
				  ghost[entity.getSubtype()] = false;
			  }
		  }
		wait(3);
//		startTimer += Gdx.graphics.getDeltaTime();
//		  if(startTimer >= 1.5f && !ingame && !ghost[0] && !ghost[1] && !ghost[2]) {
//			  addGhosts();
//			  startTimer = 0;
//		  }
	  }
	  
	  
	  public void update(float delta) {
		  if(!gameover) {
			  startTimer += delta;
			  if(startTimer >= 1.5f && !ingame && !ghost[0] && !ghost[1] && !ghost[2] && !ghost[3] && mode == 2) {
				  addGhosts();
				  startTimer = 0;
			  }
			  if(ingame) {
				  starting = false;
				  myTimer += delta;
				  for(Entity entity : entities) {
					  entity.update(delta);
				  }
				  this.food = getFood();
				  if(this.initialfood != 0 && this.food == 0) {
					  gameover = true;
				  }
				  this.debug();
				  if(mode == 1) {
					  playerEnergized(7);
				  }
				  if(!ghost[0] && ((int)myTimer - (int) ghostADead) == 7) {
//					  spawnGhost(0);
					  entities.add(new GhostA(ghostX[0], ghostY[0], this));
					  ghost[0] = true;
					  ghostADead = 0;
					  System.out.println("cero");
				  } else if(!ghost[1] && ((int) myTimer - (int)ghostBDead) == 7) {
//					  spawnGhost(1);
					  entities.add(new GhostB(ghostX[1], ghostY[1], this));
					  ghost[1] = true;
					  ghostBDead = 0;
					  System.out.println("uno");
				  } else if(!ghost[2] && ((int) myTimer - (int) ghostCDead) == 7) {
//					  spawnGhost(2);
					  entities.add(new GhostC(ghostX[2], ghostY[2], this));
					  ghost[2] = true;
					  ghostCDead = 0;
					  System.out.println("dos");
				  } else if(!ghost[3] && ((int) myTimer - (int) ghostDDead) == 7) {
//					  spawnGhost(2);
					  entities.add(new GhostD(ghostX[3], ghostY[3], this));
					  ghost[3] = true;
					  ghostDDead = 0;
					  System.out.println("tres");
				  }
				  if(!ghost[0] && !ghost[1] && !ghost[2] && !ghost[3]) {
					  contadorfantasmas = 0;
				  }
			  } else {
				  if(mode >= 2) {
					  for(Entity entity : entities) {
						  if(entity.getType() == EntityType.PLAYER) {
							  entity.update(delta);
						  }
					  }
				  }
			  }
			  
			  
			  if(Gdx.input.justTouched()) {
				  int mousex = (int) (Gdx.input.getX() / TILESIZE);
				  int mousey = (int) (Gdx.input.getY() / TILESIZE);
				  mousey = Math.abs(mousey - mapheight + 1);
				  System.out.println("X: " + mousex + " Y: " + mousey + " Tile: " + getTileAt(mousex, mousey) + " / Collidable: " + getTileAt(mousex, mousey).isCollidable());
			  }
			  
			  Iterator<Entity> it = entities.iterator();
			  while(it.hasNext()) {
				  Entity entity = it.next();
				  if(entity.isDestroyed()) {
					  it.remove();
				  }
			  }
			 
			  boolean playerok = false;
			  for(Entity entity : entities) {
				  if (entity.getType() == EntityType.PLAYER) {
					  playerok = true;
					  break;
				  }
			  }
			  if(!playerok) {
				  entities.add(new Player(playerX, playerY, this, 0));
			  }
		  }
	  }	  
	 
	public void setGameover(boolean gameover) {
		this.gameover = gameover;
	}

	public void resetLevel() {
		 if (!ingame && !ghost[0] && !ghost[1] && !ghost[2] && !ghost[3]){
			  addGhosts();
		  }
	}
	
	public void killPlayer() {
		for(Entity entity : entities) {
		  if(entity.getType() == EntityType.PLAYER) {
			  entity.setDestroyed(true);
		  }
		}
	}
	
	public boolean checkcolision(float x, float y, int width, int height, int direction) {
		  int col = (int) x/TILESIZE;
		  int row = (int) y/TILESIZE;
		  
		  if(direction == 1) {
			  col = (int) Math.ceil(x/TILESIZE);
		  } else if (direction == 2) {
			  row = (int) Math.ceil(y/TILESIZE);
		  }
		  
		  if (x <= 0 || x+width >= (mapwidth*TILESIZE)-1 || y <= 0 || y+height >= mapheight*TILESIZE ) {
			  if(getTileAt(col, row) != TileType.DOOR) {
				  return true;  
			  }
			  
		  }
		  
		  if (getTileAt(col, row).isCollidable()){
			  return true;
		  }  
  
		return false; 
	  }
	  
	  public void checkTile(float x, float y, int width, int height, int id) {
		  int tempx = (int)((x+width/2)/TILESIZE);
		  int tempy = (int)((y+height/2)/TILESIZE);
		  int col = (int) x/TILESIZE;
		  int row = (int) y/TILESIZE;
		  int newX = -1;
		  int newY = -1;
		  Entity ent = getEntityById(id);
		  int dir = ent.getDirection();
		  if (getTileAt(tempx, tempy) == TileType.FOOD || (getTileAt(tempx, tempy) == TileType.BFOOD)){
			  int temp = -1;
			  try {
				  temp = score.get(id);
			  } catch (Exception e) {
				  System.out.println(e);
			  }
			  char[] c = mapdata[Math.abs(tempy-mapheight+1)].toCharArray();
			  if (getTileAt(tempx, tempy) == TileType.FOOD) {
				  swapFood(tempy, tempx);
				  if(temp != -1) {
					  score.set(id, temp + 10);  
				  }
			  }
			  if (getTileAt(tempx, tempy) == TileType.BFOOD) {
				  swapFood(tempy, tempx);
				  if(temp != -1) {
					  score.set(id, temp + 50);
					  this.mode = 1;
					  timer = 0;
					  for(Entity entity:entities) {
						  if(entity.getType() == EntityType.GHOST) {
							  entity.setJustspawned(false);
						  }
					  }
					  smanager.play(0);
				  }
			  }
		  }
		  
		  if(dir == 0 || dir == 1) {
			  if(dir == 0) {
				  if(getTileAt((int)((x+width)/TILESIZE), row) == TileType.DOOR) {
					  for(int j = 0; j < mapdata[0].length(); j++) {
						  if(j != tempx) {
							  if(getTileAt(j, row) == TileType.DOOR) {
								  newX = j;
							  }
						  }
					  }
				  }
			  } else if (dir == 1) {
				  if(getTileAt(col, row) == TileType.DOOR) {
					  for(int j = 0; j < mapdata[0].length(); j++) {
						  if(j != tempx) {
							  if(getTileAt(j, row) == TileType.DOOR) {
								  newX = j;
							  }
						  }
					  }
				  }
			  }
			  if(newX != -1) {
				  if (dir == 0) {
					  ent.setX((newX-1));
				  } else if (dir == 1) {
					  ent.setX((newX+1));
				  }
			  } 
		  }
			
		  if(dir == 2 || dir == 3) {
			  if(dir == 2) {
				  if(getTileAt(col, row) == TileType.DOOR) {
					  for(int k = 0; k < mapdata.length; k++) {
						  if(k != tempy) {
							  if(getTileAt(col, k) == TileType.DOOR) {
								  newY = k;
							  }
						  }
					  }
				  }
			  } else if(dir == 3) {
				  if(getTileAt(col, row+1) == TileType.DOOR) {
					  for(int k = 0; k < mapdata.length; k++) {
						  if(k != tempy) {
							  if(getTileAt(col, k) == TileType.DOOR) {
								  newY = k;
							  }
						  }
					  }
				  }
			  }
			  if(newY != -1) {
				  if (dir == 2) {
					  ent.setY((newY+1)*TILESIZE);  
				  } else if (dir == 3) {
					  ent.setY((newY-1)*TILESIZE);  
				  }
			  }
		  }
		  
				  
				 
				  
	  }
		  
	  private Entity getEntityById(int id) {
		  for (Entity entity : entities) {
			  if (entity.getPlayerId() == id) {
				  return entity;
			  }
		  }
		  return null;
	  }

	  private void generateMap() {
			for(int m=0;m<=mapdata.length-1;m++) {
				  char[] c = mapdata[m].toCharArray();
				  for(int n=0;n<c.length;n++) {
					 if(c[n] == '0') {
						 batch.draw(TileType.WALL.getImg(), n*TILESIZE, Math.abs(m-mapdata.length+1)*TILESIZE);
					 }
					 if(c[n] == 'C') {
						 batch.draw(TileType.FOOD.getImg(), n*TILESIZE, Math.abs(m-mapdata.length+1)*TILESIZE);
					 }
					 if(c[n] == 'D') {
						 batch.draw(TileType.BFOOD.getImg(), n*TILESIZE, Math.abs(m-mapdata.length+1)*TILESIZE);
					 }
					 if(c[n] == '2') {
						 batch.draw(TileType.AIR.getImg(), n*TILESIZE, Math.abs(m-mapdata.length+1)*TILESIZE);
					 }
					 if(c[n] == '1') {
						 batch.draw(TileType.AIR.getImg(), n*TILESIZE, Math.abs(m-mapdata.length+1)*TILESIZE);
					 }
				  }
			  }
	  }
	  
	  
	  private void swapFood(int y, int x) {  
		  char[] c = mapdata[Math.abs(y-mapheight+1)].toCharArray();
		  c[x] = '1';
		  String temp = "";
		  for(int z = 0; z < c.length; z++) {
			  temp += c[z];
		  }
		  mapdata[Math.abs(y-mapheight+1)] = temp;
		  fillMap();
		  smanager.play(0);
	  }
	  
	  
	private void debug() {
		if(debugger) {
			for (Entity entity : entities) {
				if (entity.getType() == EntityType.PLAYER) {
					System.out.println("Player X: " + entity.getX() + " Y: " + entity.getY());
					System.out.println("Player DIR: " + entity.getDirection());
					System.out.println("SCORE: " + entity.getInt());
				}
			}
		}
		debugger = false;
	}
	  
	public boolean isDebugger() {
		return debugger;
	}
	
	public void setDebugger(boolean debugger) {
		this.debugger = debugger;
	}  
	  
	public boolean checkCollisionWith(float x, float y, int width, int height, int id) {
		for (Entity entity: entities) {
			if(entity.getType() == EntityType.GHOST) {
				if(x+width > entity.getX() && x < entity.getX()+entity.getWidth() && y+height > entity.getY() && y < entity.getY()+entity.getHeight()) {
					if(mode == 1 && entity.getMode() == 3) {
						eatable = true;
						entity.setDestroyed(true);
						if(entity.getSubtype() == 0) {
							  ghost[0] = false;
							  ghostADead = myTimer;
						  } else if (entity.getSubtype() == 1) {
							  ghost[1] = false;
							  ghostBDead = myTimer;
						  } else if (entity.getSubtype() == 2) {
							  ghost[2] = false;
							  ghostCDead = myTimer;
						  }  else if (entity.getSubtype() == 3) {
							  ghost[3] = false;
							  ghostDDead = myTimer;
						  }
						if(contadorfantasmas < 4) {
							contadorfantasmas += 1;
						}
						score.set(id, score.get(id) + 200);
						myScore.setX((int)(x/TILESIZE)*TILESIZE);
						myScore.setY((int)(y/TILESIZE)*TILESIZE);
						myScore.setTexture(contadorfantasmas-1);
						scoretimer = 0;
						smanager.play(1);
					} else {
						eatable = false;
						smanager.play(2);
					}
					ingame = false;
					return true;
				}
			}
		}
		return false;
	}
	
	
	public TileType getTileAt(int x, int y) {
		TileType tile = tilemap.get(String.valueOf(x)+","+String.valueOf(y)); 
		if(tile != null) {
			return tile;
		}
		return TileType.AIR;
	}
	
	public int getScore(int id) {
		return score.get(id);
	}
	
	public HashMap<String, TileType> getTilemap() {
		return tilemap;
	}
	
	public int getMode() {
		return mode;
	}
	
}
