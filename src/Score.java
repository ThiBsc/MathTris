import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * Classe permettant de gérer l'affichage du score et du niveau
 *
 */
public class Score extends PropertyChangeSupport implements PropertyChangeListener {

	private static final long serialVersionUID = 4236833058759200471L;
	private int score;
	private int lvl;
	private JLabel lblScore;
	private JPanel mainPanel;
	private GridLayout gLayout;
	
	public Score(int sourceBean) {
		super(sourceBean);
		score = sourceBean;
		lvl = 1;
		lblScore = new JLabel();
		lblScore.setText("Niveau: "+lvl+" | Score: "+score);
		gLayout = new GridLayout(2, 1);
		mainPanel = new JPanel();
		mainPanel.add(lblScore);
		mainPanel.setLayout(gLayout);
	}
	
	public void addFieldReponse(JTextField jtf){
		mainPanel.add(jtf, 0);
	}
	public JPanel getPanel(){
		return mainPanel;
	}
	public void resetScore(){
		firePropertyChange("score", score, 0);
		lvl = 1;
		firePropertyChange("lvl", 0, lvl);
	}
	public JLabel getLabelScore(){
		return lblScore;
	}
	public void updateScore(int toAdd){
		firePropertyChange("score", score, score+toAdd);
	}
	public void lvlUp(){
		lvl++;
		firePropertyChange("lvl", lvl, lvl+1);
	}
	public int getScore(){
		return score;
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("score")){
			score = (int) evt.getNewValue();
			lblScore.setText("Niveau: "+lvl+" | Score: "+score);
		}
		else if (evt.getPropertyName().equals("lvl"))
			lblScore.setText("Niveau: "+lvl+" | Score: "+score);
	}
}
