package game;

import java.awt.Color;

public class Shape {
	
	public static final int GRID_SIZE = 4;
	private Color color;
	private boolean[][] coords;
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
		
		public void setCoords(boolean[][] ncoords) {
			coords = ncoords;
		}
	}
	
	public Shape(Color c, boolean[][] coords) {
		color = c;
		this.coords = coords;
		x = y = 0;
	}
	
	public Color getColor() {
		return color;
	}
	
	public boolean[][] getCoords(){
		return coords;
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
	
	/**
	 * To apply a rotation, past the first row to become the first column
	 * Ex (rotate left).	|00|01|02|03|	|03|13|23|33|
	 * 	   					|10|11|12|13| ->|02|12|22|32| 
	 * 	   					|20|21|22|23|	|01|11|21|31|
	 * 	   					|30|31|32|33|	|00|10|20|30|
	 */
	public void rotateLeft() {
		if (coords != TileShape.SquareTile.getCoords()) {
			boolean [][] old_coords = coords;
			boolean [][] new_coords = new boolean[GRID_SIZE][GRID_SIZE];
			// for each row...
			for (int r=0; r<GRID_SIZE; r++) {
				new_coords[0][r] = old_coords[r][3];
				new_coords[1][r] = old_coords[r][2];
				new_coords[2][r] = old_coords[r][1];
				new_coords[3][r] = old_coords[r][0];
			}
			coords = new_coords;
		}
	}
	
	public void rotateRight() {
		if (coords != TileShape.SquareTile.getCoords()) {
			boolean [][] old_coords = coords;
			boolean [][] new_coords = new boolean[GRID_SIZE][GRID_SIZE];
			// for each row...
			for (int r=0; r<GRID_SIZE; r++) {
				new_coords[r][0] = old_coords[3][r];
				new_coords[r][1] = old_coords[2][r];
				new_coords[r][2] = old_coords[1][r];
				new_coords[r][3] = old_coords[0][r];
			}
			coords = new_coords;
		}
	}

}
