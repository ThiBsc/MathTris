package game;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Tetris extends JFrame {
	
	private Board board;
	
	public Tetris() {
		super("MathTris!");
		
		board = new Board();
		add(board, BorderLayout.CENTER);

		setSize(600, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		Tetris tetris = new Tetris();
		tetris.setLocationRelativeTo(null); // center
		tetris.setVisible(true);
	}

}
