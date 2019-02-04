package game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JToolBar;

/**
 * @author thibdev
 */
public class Tetris extends JFrame {
	
	public static final int XCASE=10, YCASE=20;
	private Board board;
	private ToolBar toolBar;
	
	public Tetris() {
		super("MathTris!");

		board = new Board();
		toolBar = new ToolBar(this, board, JToolBar.HORIZONTAL);
		
		add(toolBar, BorderLayout.NORTH);
		add(board, BorderLayout.CENTER);

		pack();
		setSize(450, 600);
		setMinimumSize(new Dimension(350, 450));
		board.requestFocusInWindow();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		Tetris tetris = new Tetris();
		tetris.setLocationRelativeTo(null); // center
		tetris.setVisible(true);
	}

}
