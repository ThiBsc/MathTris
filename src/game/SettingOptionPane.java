package game;

import java.util.TreeSet;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SettingOptionPane extends JOptionPane {
	
	private JPanel panel;
	private JLabel lblTable;
	private JTextField txtTable;

	public SettingOptionPane() {
		panel =  new JPanel();
		lblTable = new JLabel("Table:");
		txtTable = new JTextField("1, 2, 3, 4, 5, 6, 7, 8, 9");
		panel.add(lblTable);
		panel.add(txtTable);
	}
	
	public int displaySettings() {
		int ret = showConfirmDialog(null, panel, "Game settings", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		return ret;
	}
	
	public String getTables() {
		return txtTable.getText(); 
	}
	
	public void setTables(TreeSet<Integer> tables) {
		txtTable.setText(tables.toString().replaceAll("\\[|\\]", ""));
	}
	
}