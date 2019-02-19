package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TreeSet;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import generator.Operation;

public class SettingOptionPane extends JOptionPane {
	
	private JPanel panel;
	private JLabel lblTable, lblOperation;
	private JTextField txtTable, txtOperation;
	private JCheckBox chkModeMath;
	private MyGridLayout gLayout;

	public SettingOptionPane() {
		panel =  new JPanel();
		gLayout = new MyGridLayout(panel);
		
		lblTable = new JLabel("Table:");
		lblOperation = new JLabel("Operation:");
		
		txtTable = new JTextField("1, 2, 3, 4, 5, 6, 7, 8, 9");
		txtOperation = new JTextField("x");
		lblTable.setLabelFor(txtTable);
		lblOperation.setLabelFor(txtOperation);
		
		chkModeMath = new JCheckBox("Mode Math", true);
		chkModeMath.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtTable.setEnabled(chkModeMath.isSelected());
				txtOperation.setEnabled(chkModeMath.isSelected());
			}
		});
		
		gLayout.addComponent(lblTable, 0, 0);
		gLayout.addComponent(txtTable, 0, 1);
		gLayout.addComponent(lblOperation, 1, 0);
		gLayout.addComponent(txtOperation, 1, 1);
		gLayout.addComponent(chkModeMath, 2, 0, 1, 2);
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
	
	public String getOperations() {
		if (txtOperation.getText().isEmpty()) {
			return "x";
		} else {
			return txtOperation.getText();
		}
	}
	
	public void setOperations(TreeSet<Operation> operations) {
		txtOperation.setText(operations.toString().replaceAll("\\[|\\]", ""));
	}
	
	public boolean isModeMath() {
		return chkModeMath.isSelected();
	}
	
	public void setModeMath(boolean math) {
		chkModeMath.setSelected(math);
		txtTable.setEnabled(chkModeMath.isSelected());
		txtOperation.setEnabled(chkModeMath.isSelected());
	}
}