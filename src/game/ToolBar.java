package game;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import generator.EquationGenerator;

/**
 * @author thibdev
 */
public class ToolBar extends JToolBar implements ActionListener {
	
	/**
	 * Icons made by "https://www.flaticon.com/authors/smashicons" from "https://www.flaticon.com/" is licensed by CC 3.0 BY
	 * Icons made by "https://www.freepik.com/" from "https://www.flaticon.com/" is licensed by CC 3.0 BY
	 */
	private JButton btnSettings;
	private JFrame parent;
	private Board board;

	public ToolBar(JFrame parent, Board board) {
		super("ToolBar");
		this.parent = parent;
		this.board = board;
		init();
	}

	public ToolBar(JFrame parent, Board board, int orientation) {
		super("ToolBar", orientation);
		this.parent = parent;
		this.board = board;
		init();
	}
	
	private void init() {
		setFloatable(false);
		
		btnSettings = new JButton();

		addButton(btnSettings, getClass().getResource("/icons/settings.png"), "Game settings", "Games settings", "settings");
	}
	
	private void addButton(JButton btn, URL icon_url, String tooltip, String text, String actionCommand) {
		try {
			ImageIcon icon = new ImageIcon(new ImageIcon(icon_url).getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT));
			btn.setIcon(icon);
			btn.setToolTipText(tooltip);
			//btn.setBorderPainted(false);
		} catch (Exception e) {
			btn.setText(text);
		} finally {
			btn.setActionCommand(actionCommand);
			btn.addActionListener(this);
			add(btn);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "settings":
			SettingOptionPane sop = new SettingOptionPane();
			sop.setTables(board.getEquationGenerator().getTable());
			if (sop.displaySettings() == JOptionPane.OK_OPTION) {
				EquationGenerator eg = board.getEquationGenerator();
				eg.setTable(sop.getTables());
				eg.generate();
				board.repaint();
			}
			break;
		default:
			break;
		}
		board.requestFocusInWindow();
	}

}