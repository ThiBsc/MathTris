package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Shape {
	
	private Color color;
	private boolean[][] coords;
	private int x, y, arc;
	
	/**
	 * Represent a tile with coordinates in a square
	 * Ex. (TTile) All tetrominos must be representated in a square
	 *  _ _ _ 
	 * |_|1|_|
	 * |1|1|1|
	 * |_|_|_|
	 */
	enum TileShape {
		ZTile(new boolean[][] {
			{true, true, false},
			{false, true, true},
			{false, false, false}
		}),
		STile(new boolean[][] {
			{false, true, true},
			{true, true, false},
			{false, false, false}
		}),
		SquareTile(new boolean[][] {
			{true, true},
			{true, true}
		}),
		LineTile(new boolean[][] {
			{false, true, false, false},
			{false, true, false, false},
			{false, true, false, false},
			{false, true, false, false}
		}),
		TTile(new boolean[][] {
			{false, true, false},
			{true, true, true},
			{false, false, false}
		}),
		LTile(new boolean[][] {
			{false, true, false},
			{false, true, false},
			{false, true, true}
		}),
		JTile(new boolean[][] {
			{false, true, false},
			{false, true, false},
			{true, true, false}
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
		arc = 10;
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
	
	public int getArc() {
		return arc;
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
			int tabLen = coords.length;
			boolean [][] old_coords = coords;
			boolean [][] new_coords = new boolean[tabLen][tabLen];
			// for each row...
			for (int r=0; r<tabLen; r++) {
				for (int c=0; c<tabLen; c++) {
					new_coords[c][r] = old_coords[r][(tabLen-1)-c];
				}
			}
			coords = new_coords;
		}
	}
	
	public void rotateRight() {
		if (coords != TileShape.SquareTile.getCoords()) {
			int tabLen = coords.length;
			boolean [][] old_coords = coords;
			boolean [][] new_coords = new boolean[tabLen][tabLen];
			// for each row...
			for (int r=0; r<tabLen; r++) {
				for (int c=0; c<tabLen; c++) {
					new_coords[r][c] = old_coords[(tabLen-1)-c][r];
				}
			}
			coords = new_coords;
		}
	}
	
	public void draw(Graphics g, Rectangle rect) {
		int squared = rect.width/coords.length;
		g.setColor(color);
		int tabLen = coords.length;
		for (int i=0; i<tabLen; i++) {
			for (int j=0; j<tabLen; j++) {
				if (coords[j][i]) {
					g.fillRoundRect(rect.x+i*squared+1,
							rect.y+j*squared+1,
							squared-1,
							squared-1,
							arc, arc);
				}
			}
		}
	}

}
