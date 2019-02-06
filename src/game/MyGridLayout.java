package game;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;

public class MyGridLayout extends GridBagLayout {
	
	private static final long serialVersionUID = 1L;
	private GridBagConstraints constraint;
	private JComponent mainComponent;
	
	public MyGridLayout(JComponent mc) {
		constraint = new GridBagConstraints();
		constraint.fill = GridBagConstraints.BOTH;
		mainComponent = mc;
		mainComponent.setLayout(this);
	}
	
	public void addComponent(JComponent comp, int row, int col) {
		addComponent(comp, row, col, 1, 1);
	}
	
	public void addComponent(JComponent comp, int row, int col, int rowSpan, int colSpan) {
		constraint.gridy = row;
		constraint.gridx = col;
		constraint.gridheight = rowSpan;
		constraint.gridwidth = colSpan;
		constraint.weightx = 0.5;
		constraint.weighty = 0.5;
		mainComponent.add(comp, constraint);
	}

}
