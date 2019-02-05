package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.BitSet;
import java.util.Random;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.Timer;

import game.Shape.TileShape;
import generator.EquationGenerator;

/**
 * @author thibdev
 */
public class Board extends JPanel implements KeyListener {
	
	private Shape currentShape, nextShape;
	private Timer timer;
	private Vector<Vector<Pair<Boolean, Color>>> gBoard;
	private int line;
	private Mode mode;
	private EquationGenerator equation;
	private String answer;
	private boolean isAnswered, gameOver;
	private ToolBar toolBar;
	
	enum Move {
		LEFT,
		RIGHT,
		R_LEFT,
		R_RIGHT,
		DOWN;
	}
	
	public Board() {
		super();
		line = 0;
		mode = Mode.MATH;
		equation = new EquationGenerator();
		answer = "";
		isAnswered = gameOver = false;
		// The grid of the game
		gBoard = new Vector<>();
		// Init rows
		for (int i=0; i<Tetris.YCASE; i++) {
			gBoard.add(new Vector<Pair<Boolean, Color>>());
			// Init columns
			for (int j=0; j<Tetris.XCASE; j++) {
				gBoard.get(i).add(new Pair<>(false, Color.white));
			}
		}
		currentShape = generateShape();
		nextShape = generateShape();
		timer = new Timer(500, new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (canMove(Move.DOWN)) {
					currentShape.moveDown();
				} else {
					if (!gameOver) {
						updateGameBoard();
						currentShape = nextShape;
						nextShape = generateShape();
						if (!canMove(Move.DOWN)) {
							gameOver = true;
							timer.stop();
							toolBar.setNewGameButton();
						}
					}
				}
				repaint();
			}
		});
		addKeyListener(this);
		setFocusable(true);
		setRequestFocusEnabled(true);
	}
	
	public void setToolBar(ToolBar toolBar) {
		this.toolBar = toolBar;
	}
	
	public EquationGenerator getEquationGenerator() {
		return equation;
	}
	
	public void play() {
		timer.start();
		// To display the equation quickly
		repaint();
	}
	
	public void pause() {
		timer.stop();
		// Force repaint to remove equation during pause
		repaint();
	}
	
	public void newGame() {
		line = 0;
		answer = "";
		isAnswered = gameOver = false;
		// Reset game
		for (int i=0; i<Tetris.YCASE; i++) {
			for (int j=0; j<Tetris.XCASE; j++) {
				gBoard.get(i).get(j).key = false;
				gBoard.get(i).get(j).value = Color.white;
			}
		}
		currentShape = generateShape();
		nextShape = generateShape();
		repaint();
	}
	
	private Shape generateShape() {
		Shape s;
		Random r = new Random();
		// Color: https://en.wikipedia.org/wiki/Tetris#Tetromino_colors
		switch (r.nextInt(7)) {
		case 0:
			s = new Shape(Color.red, TileShape.ZTile.getCoords());
			break;
		case 1:
			s = new Shape(Color.green.darker(), TileShape.STile.getCoords());
			break;
		case 2:
			s = new Shape(Color.blue, TileShape.JTile.getCoords());
			break;
		case 3:
			s = new Shape(Color.orange, TileShape.LTile.getCoords());
			break;
		case 4:
			s = new Shape(new Color(153, 50, 204)/*purple*/, TileShape.TTile.getCoords());
			break;
		case 5:
			s = new Shape(Color.cyan, TileShape.LineTile.getCoords());
			break;
		case 6:
			s = new Shape(Color.yellow, TileShape.SquareTile.getCoords());
			break;
		default:
			s = new Shape(Color.yellow, TileShape.SquareTile.getCoords());
			break;
		}
		equation.generate();
		answer = "";
		isAnswered = false;
		// Set the initial position of the generated shape
		// x = (xcase - TileShape xcoord size)/2
		// y = 0
		s.setPos((Tetris.XCASE-s.getCoords().length)/2, 0);
		return s;
	}
	
	private void updateGameBoard() {
		Color color = currentShape.getColor();
		int xpos = currentShape.getXPos();
		int ypos = currentShape.getYPos();
		boolean[][] coords = currentShape.getCoords();
		int tabLen = coords.length;
		BitSet shapeRowBS = new BitSet(tabLen);
		for (int i=0; i<tabLen; i++) {
			/*
			 * Get the line of the current shape
			 * Ex.
			 * 0100
			 * 1110 <- curLine
			 * 0000 
			 */
			for (int c=0; c<tabLen; c++) {
				shapeRowBS.set(c, coords[i][c]);
			}
			
			if (!shapeRowBS.isEmpty()) {
				for (int change=0; change<tabLen; change++) {
					if (shapeRowBS.get(change)) {
						gBoard.get(ypos+i).get(xpos+change).key = true;
						gBoard.get(ypos+i).get(xpos+change).value = color;
						// If the line is complete, remove it
						boolean completeLine = gBoard.get(ypos+i).stream().filter(p -> p.key).count() == Tetris.XCASE;
						if (completeLine) {
							line++;
							gBoard.remove(ypos+i);
							// insert a new empty line at the beginning
							gBoard.add(0, new Vector<>());
							for (int j=0; j<Tetris.XCASE; j++) {
								gBoard.get(0).add(new Pair<>(false, Color.white));
							}
						}
					}	
				}
			}
		}
	}
	
	private boolean canMove(Move move) {
		boolean ret = true;
		
		// For R_LEFT and R_RIGHT, we have to copy the array
		// to avoid change on currentShape
		// I think we can do better than this
		boolean[][] old_coords = currentShape.getCoords();
		int tabLen = old_coords.length;
		boolean[][] coords = new boolean[tabLen][tabLen];
		for (int i = 0; i < tabLen; i++) {
		    System.arraycopy(old_coords[i], 0, coords[i], 0, tabLen);
		}
		Shape potentialNewShape = new Shape(currentShape.getColor(), coords);
		potentialNewShape.setPos(currentShape.getXPos(), currentShape.getYPos());
		switch (move) {
		case LEFT:
			potentialNewShape.moveLeft();
			break;
		case RIGHT:
			potentialNewShape.moveRight();
			break;
		case R_LEFT:
			potentialNewShape.rotateLeft();
			break;
		case R_RIGHT:
			potentialNewShape.rotateRight();
			break;
		case DOWN:
			potentialNewShape.moveDown();
			break;
		default:
			break;
		}
		coords = potentialNewShape.getCoords();
		
		// Test if we can move
		int xpos = potentialNewShape.getXPos();
		int ypos = potentialNewShape.getYPos();
		potentialNewShape = null;
		BitSet shapeRowBS = new BitSet(tabLen);
		BitSet boardRowBS = new BitSet(tabLen);
		for (int i=0; i<tabLen && ret==true; i++) {
			/*
			 * Get the line of the current shape
			 * Ex.
			 * 0100
			 * 1110 <- curLine
			 * 0000
			 */
			for (int c=0; c<tabLen; c++) {
				shapeRowBS.set(c, coords[i][c]);
			}
			
			if (!shapeRowBS.isEmpty()) {
				if (move == Move.DOWN) {
					// Do AND for each line of the current shape and invert result
					// If the bitset is not 0, you can't move
					if (ypos+i < Tetris.YCASE) {
						boardRowBS.clear();
						Vector<Pair<Boolean, Color>> vRow = gBoard.get(ypos+i);
						for (int vr=0; vr<tabLen; vr++) {
							if (0 <= xpos+vr && xpos+vr < Tetris.XCASE) {
								boardRowBS.set(vr, vRow.get(xpos+vr).key);
							}
						}
						shapeRowBS.and(boardRowBS);
						ret = shapeRowBS.isEmpty();
					} else {
						ret = false;
					}
				} else {
					int left = shapeRowBS.nextSetBit(0);
					int right = shapeRowBS.previousSetBit(tabLen-1);
					// Check border
					ret = (0 <= xpos+left) && (xpos+right < Tetris.XCASE);
					// Check other shapes
					if (ret) {
						Vector<Pair<Boolean, Color>> vRow = gBoard.get(ypos+i);
						for (int vr=0; vr<tabLen; vr++) {
							if (0 <= xpos+vr && xpos+vr < Tetris.XCASE) {
								boardRowBS.set(vr, vRow.get(xpos+vr).key);
							}
						}
						shapeRowBS.and(boardRowBS);
						ret = shapeRowBS.isEmpty();
					}
				}
			}
		}
		
		return ret;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Font for game info
		Font f = new Font("Courier New", Font.PLAIN, g.getFont().getSize()*2);
		FontMetrics fm = g.getFontMetrics(f);
		int right_offset = fm.stringWidth(String.format("Line: %d", line));
		
		int available_width = (getWidth()-right_offset)/Tetris.XCASE;
		int available_height = getHeight()/Tetris.YCASE;
		int squared = available_width < available_height ? available_width : available_height;
		int wcase = (getWidth()-right_offset-squared)/Tetris.XCASE;
		int hcase = getHeight()/Tetris.YCASE;
		squared = wcase < hcase ? wcase : hcase;
		int xmax = Tetris.XCASE*squared;
		int ymax = Tetris.YCASE*squared;

		// Draw the board rect
		g.setColor(Color.gray);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		// Display the number of completed line
		g.setColor(Color.white);
		g.setFont(f);
		g.drawString(String.format("Line: %d", line), getWidth()-(right_offset+squared), squared);
		// Draw the next shape
		int tabLen = nextShape.getCoords().length - 1;
		if (tabLen == 1) {
			// Square
			tabLen++;
		}
		Rectangle r = new Rectangle(getWidth()-(right_offset), squared*3, squared*tabLen, squared*tabLen);
		g.drawString("Next:", getWidth()-(right_offset+squared), squared*2);
		nextShape.draw(g, r);
		
		int xPlayBegin = ((getWidth()-(right_offset+squared))/2)-(xmax/2);
		
		// Draw the grid
		g.setColor(Color.lightGray);
		for (int i=1; i<Tetris.XCASE; i++) {
			// Vertical grid
			g.drawLine(xPlayBegin+i*squared, 0, xPlayBegin+i*squared, ymax);
			for (int j=1; j<Tetris.YCASE; j++) {
				// Horizontal grid
				g.drawLine(xPlayBegin, j*squared, xPlayBegin+xmax, j*squared);
			}
		}
		g.drawRect(xPlayBegin, 0, xmax, ymax);
		
		// Draw the controllable shape
		tabLen = currentShape.getCoords().length;
		r.setBounds(xPlayBegin+currentShape.getXPos()*squared, currentShape.getYPos()*squared, squared*tabLen, squared*tabLen);
		currentShape.draw(g, r);
		
		// Draw the gBoard (dead tetrominos)
		int arc = currentShape.getArc();
		for (int i=0; i<Tetris.YCASE; i++) {
			for (int j=0; j<Tetris.XCASE; j++) {
				Pair<Boolean, Color> p = gBoard.get(i).get(j);
				if (p.key) {
					g.setColor(p.value);
					g.fillRoundRect(xPlayBegin+j*squared+1,
							i*squared+1,
							squared-1,
							squared-1,
							arc, arc);
				}
			}
		}
		
		// Setting font
		Font question_font = new Font(f.getName(), Font.BOLD, f.getSize());
		fm = g.getFontMetrics(question_font);
		int size = question_font.getSize();
		int y_pos = ymax/2 - fm.getHeight();
		while (fm.stringWidth(equation.toString()) < xmax) {
			fm = g.getFontMetrics(question_font.deriveFont((float)size++));
		}
		size--;
		g.setFont(question_font.deriveFont((float)size));
		
		if (gameOver) {
			// Draw "Game over"
			int gameover_width = fm.stringWidth("Game over!");
			g.setFont(question_font.deriveFont((float)size-1));
			g.setColor(Color.white);
			g.drawString("Game over!", xPlayBegin+(xmax/2-gameover_width/2), y_pos+fm.getHeight());
		} else {
			if (mode == Mode.MATH && !isAnswered) {
				// Draw the math equation
				Color question_color = new Color(255, 255, 255, 100);
				g.setColor(question_color);
				g.fillRect(xPlayBegin, 0, xmax, ymax);
				if (timer.isRunning()) {
					g.setColor(Color.black);
					g.drawString(equation.toString(), xPlayBegin, y_pos);
					// Draw the answer
					int answer_width = fm.stringWidth(answer);
					g.drawString(answer, xPlayBegin+(xmax/2-answer_width/2), y_pos+fm.getHeight());
				}
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (timer.isRunning() && !gameOver) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if (canMove(Move.LEFT) && isAnswered)
					currentShape.moveLeft();
				break;
			case KeyEvent.VK_RIGHT:
				if (canMove(Move.RIGHT) && isAnswered)
					currentShape.moveRight();
				break;
			case KeyEvent.VK_UP:
				if (canMove(Move.R_LEFT) && isAnswered)
					currentShape.rotateLeft();
				break;
			case KeyEvent.VK_DOWN:
				if (canMove(Move.R_RIGHT) && isAnswered)
					currentShape.rotateRight();
				break;
			case KeyEvent.VK_SPACE:
				while (canMove(Move.DOWN) && isAnswered) {
					currentShape.moveDown();
				}
				break;
			case KeyEvent.VK_0:
			case KeyEvent.VK_1:
			case KeyEvent.VK_2:
			case KeyEvent.VK_3:
			case KeyEvent.VK_4:
			case KeyEvent.VK_5:
			case KeyEvent.VK_6:
			case KeyEvent.VK_7:
			case KeyEvent.VK_8:
			case KeyEvent.VK_9:
			case KeyEvent.VK_NUMPAD0:
			case KeyEvent.VK_NUMPAD1:
			case KeyEvent.VK_NUMPAD2:
			case KeyEvent.VK_NUMPAD3:
			case KeyEvent.VK_NUMPAD4:
			case KeyEvent.VK_NUMPAD5:
			case KeyEvent.VK_NUMPAD6:
			case KeyEvent.VK_NUMPAD7:
			case KeyEvent.VK_NUMPAD8:
			case KeyEvent.VK_NUMPAD9:
				answer += String.valueOf(e.getKeyChar());
				break;
			case KeyEvent.VK_BACK_SPACE:
				if (answer.length()>0) {
					answer = answer.substring(0, answer.length()-1);
				}
				break;
			case KeyEvent.VK_ENTER:
				isAnswered = equation.answer(Integer.parseInt(answer));
				break;
			default:
				break;
			}	
		}
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}	

}
