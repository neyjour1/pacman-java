package com.mygdx.screens;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintStream;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.pacman.PacmanGame;
import com.mygdx.tiles.TileType;

public class MapEditorScreen implements Screen {

	private PacmanGame game;
	private final int COLUMNS = 23;
	private final int ROWS = 22;
	private final int TILESIZE = 32;
	private SpriteBatch batch;
	private int key;
	private int col, row;
	private String[][] map = new String[ROWS][COLUMNS];
	private ShapeRenderer shape;
	private boolean tab = false;
	
	public MapEditorScreen(PacmanGame game) {
		this.game = game;
		this.batch = new SpriteBatch();
		for(int x = 0; x<ROWS;x++) {
			for(int j = 0; j<COLUMNS;j++) {
				map[x][j] = "0";
			}
		}
		shape = new ShapeRenderer();
	}
	
	private String[][] readMap(){
		  String mapFile = System.getProperty("user.dir") + "/savemap.txt";
		  String line = null;
		  String temp[][] = new String[ROWS][COLUMNS];
		  int y = 0;
		  try{
			  FileReader f = new FileReader(mapFile);  
			  BufferedReader br = new BufferedReader(f);
			  while ((line = br.readLine()) != null){
				  for(int x = 0; x < line.length(); x++) {
					  temp[y][x] = String.valueOf(line.charAt(x));  
				  }
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
	
	private String[][] convertMap(String[][] map) {
		String[][] temp = new String[ROWS][COLUMNS];
		String[] linea = new String[ROWS];
		String tempc = null;
		String c;
		for (int y = 0; y < map.length; y++) {
			String magia = "";
			for(int x = 0; x < map[0].length; x++) {
				c = (map[y][x]);
				magia += c;
			}
			linea[y] = magia;
		}
		
		for(int y = 0; y < linea.length; y ++) {
			for(int x = 0; x < linea[0].length(); x++) {
				if(linea[y].charAt(x) == '1') {
					tempc = "0";
				} else if (linea[y].charAt(x) == '0') {
					tempc = "1";
				} else if (linea[y].charAt(x) == '2') {
					tempc = "6";
				} else if (linea[y].charAt(x) == 'C') {
					tempc = "7";
				} else if (linea[y].charAt(x) == 'D') {
					tempc = "8";
				} else if (linea[y].charAt(x) == 'P') {
					tempc = "9";
				}
				temp[y][x] = tempc;
			}
		}
		return temp;
	}
	
	
	
	public void toggleGrid() {
		if(tab) {
		    shape.begin(ShapeType.Line);
		    shape.setColor(Color.GRAY);
		    for (int i = 0; i < ROWS; i++) {
		        shape.line(0, i*32, COLUMNS*TILESIZE, i*32);
		    }
		    for (int i = 0; i < COLUMNS; i++) {
		        shape.line(i*32, 0, i*32, ROWS*TILESIZE);
		    }
		    shape.end();
			}
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		update(delta);
		batch.begin();
		renderMap();
		batch.draw(getTileById(key).getImg(), ((int)(Gdx.input.getX()/TILESIZE))*TILESIZE, (Math.abs(Gdx.input.getY()/TILESIZE-map.length)*TILESIZE));
		batch.end();
		toggleGrid();
	}

	public void update(float delta) {
		if(Gdx.input.isKeyJustPressed(Keys.NUM_1)) {
			key = 1;
		}
		if(Gdx.input.isKeyJustPressed(Keys.NUM_2)) {
			key = 6;
		}
		if(Gdx.input.isKeyJustPressed(Keys.NUM_3)) {
			key = 7;
		}
		if(Gdx.input.isKeyJustPressed(Keys.NUM_4)) {
			key = 8;
		}
		if(Gdx.input.isKeyJustPressed(Keys.NUM_0)) {
			key = 0;
		}
		if(Gdx.input.isKeyJustPressed(Keys.NUM_5)) {
			key = 9;
		}
		if(Gdx.input.isKeyJustPressed(Keys.NUM_9)) {
			map = convertMap(readMap());
		}
		if(Gdx.input.isKeyJustPressed(Keys.ALT_LEFT)) {
			game.setScreen(game.gamescreen);
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.TAB)) {
			saveMap();
		}
		if(Gdx.input.isKeyJustPressed(Keys.CONTROL_LEFT)) {
			tab = !tab;
		}
		
		if(Gdx.input.isButtonPressed(Buttons.LEFT)) {
			int col = Gdx.input.getX() / TILESIZE;
			int row = Gdx.input.getY() / TILESIZE;
			System.out.println("row: " + row + " col: " + col);
			if(col >= 0 && col < map[0].length) {
				if(row >= 0 && row < map.length) {
					fillMap(col, row);
				}
			}
		}
		if(Gdx.input.isButtonPressed(Buttons.RIGHT)) {
			key = 0;
			int col = Gdx.input.getX() / TILESIZE;
			int row = Gdx.input.getY() / TILESIZE;
			System.out.println("row: " + row + " col: " + col);
			if(col >= 0 && col < map[0].length) {
				if(row >= 0 && row < map.length) {
					fillMap(col, row);
				}
			}
			
		}
		
	}

	
	public void saveMap() {
		try {
			PrintStream f = new PrintStream(new File(System.getProperty("user.dir") + "/savemap.txt"));
			System.out.print("Ruta: " + System.getProperty("user.dir") + "/savemap.txt");
			String[][] temp = new String[map.length][map[0].length];
			String[] ftemp = new String[map.length];
			String linea = "";
			String c = null;
			System.out.println("Y: " + map.length + " X: " + map[0].length);
			for (int y = 0; y < map.length; y++) {
				for(int x = 0; x < map[0].length; x++) {
					c = (map[y][x]);
					System.out.println("C: " + c);
					if(Integer.valueOf(c) == 0) {
						c = "1";
					} else if(Integer.valueOf(c) == 1) {
						c = "0";
					} else if(Integer.valueOf(c) == 6) { 
						c = "2";
					} else if(Integer.valueOf(c) == 7) {
						c = "C";
					} else if(Integer.valueOf(c) == 8) {
						c = "D";
					} else if(Integer.valueOf(c) == 9) {
						c = "P";
					}
					temp[y][x] = c;
				}
			}
			
			
			for(int y = 0; y<temp.length; y++) {
				linea = "";
				for(int x = 0; x<=temp.length; x++) {
					linea += temp[y][x];
				}
				ftemp[y] = linea;
			}
			System.out.println("Y mapdata: " + map.length);
			System.out.println("Y TEMP: " + ftemp.length + "X TEMP: " + ftemp[0].length());
			for(int y = 0; y < ftemp.length; y++) {
				f.println(ftemp[y]);
			}
			f.close();
			System.out.println("Exito!");
		}catch(Exception e) {System.out.println(e);}{
			
		}
	}
	
	public void renderMap() {
		for(int y = 0; y < map.length; y++) {
			for(int x = 0; x < map[0].length; x++) {
				batch.draw(getTileById(Integer.valueOf(map[y][x])).getImg(), x*TILESIZE, Math.abs(y-map.length+1)*TILESIZE);
			}
		}
	}
	
	public void fillMap(int x, int y) {
		map[y][x] = String.valueOf(key);
//		System.out.println(Arrays.deepToString(map));
	}
	
	public TileType getTileById(int id) {
		for(TileType type : TileType.values()) {
			if(type.getId() == id) {
				return type;	
			}
		}
		return null;
	}
	
	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		batch.dispose();
	}


}
