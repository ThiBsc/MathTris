package game;

import java.awt.Color;

public class Shape {
	
	public static final int GRID_SIZE = 4;
	private Color color;
	private TileShape tileShape;
	private int x, y;
	
	/**
	 * Represent a tile with coordinates in a square
	 * Ex. (TTile)
	 *  _ _ _ _
	 * |_|1|_|_|
	 * |1|1|1|_|
	 * |_|_|_|_|
	 * |_|_|_|_|
	 */
	enum TileShape {
		ZTile(new boolean[][] {
			{false, true, true, false},
			{false, false, true, true},
			{false, false, false, false},
			{false, false, false, false}
		}),
		STile(new boolean[][] {
			{false, true, true, false},
			{true, true, false, false},
			{false, false, false, false},
			{false, false, false, false}
		}),
		SquareTile(new boolean[][] {
			{false, true, true, false},
			{false, true, true, false},
			{false, false, false, false},
			{false, false, false, false}
		}),
		LineTile(new boolean[][] {
			{false, true, false, false},
			{false, true, false, false},
			{false, true, false, false},
			{false, true, false, false}
		}),
		TTile(new boolean[][] {
			{false, true, false, false},
			{true, true, true, false},
			{false, false, false, false},
			{false, false, false, false}
		}),
		LTile(new boolean[][] {
			{false, true, false, false},
			{false, true, false, false},
			{false, true, true, false},
			{false, false, false, false}
		}),
		JTile(new boolean[][] {
			{false, true, false, false},
			{false, true, false, false},
			{true, true, false, false},
			{false, false, false, false}
		});
		
		private boolean[][] coords;
		
		private TileShape(boolean[][] coords) {
			this.coords = coords;
		}
		
		public boolean[][] getCoords(){
			return coords;
		}
	}
	
	public Shape(Color c, TileShape ts) {
		color = c;
		tileShape = ts;
		x = y = 0;
	}
	
	public Color getColor() {
		return color;
	}
	
	public boolean[][] getTileShapeCoords(){
		return tileShape.getCoords();
	}
	
	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getXPos() {
		return x;
	}
	
	public int getYPos() {
		return y;
	}
	
	public void moveDown() {
		y++;
	}
	
	public void moveLeft() {
		x--;
	}
	
	public void moveRight() {
		x++;
	}
	
	public void rotateLeft() {
		// do something
	}
	
	public void rotateRight() {
		// do something
	}

}
