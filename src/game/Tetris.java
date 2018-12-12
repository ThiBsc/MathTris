package game;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JToolBar;

public class Tetris extends JFrame {
	
	public static final int XCASE=10, YCASE=20;
	private Board board;
	private ToolBar toolBar;
	
	public Tetris() {
		super("MathTris!");

		toolBar = new ToolBar(JToolBar.HORIZONTAL);
		board = new Board();
		
		//add(toolBar, BorderLayout.NORTH);
		add(board, BorderLayout.CENTER);

		setSize(700, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		Tetris tetris = new Tetris();
		tetris.setLocationRelativeTo(null); // center
		tetris.setVisible(true);
	}

}
