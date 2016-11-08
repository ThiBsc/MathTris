import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * Classe plateau qui va servir pour l'affichage generale du jeu (les briques qui tombent)
 *
 */
public class Plateau extends JPanel {
	
	private static final long serialVersionUID = 2101759990723112394L;
	private Score score; // score du joueur
	private QuestionListe listeQuestion;
	private Brique brique;
	private ArrayList<Brique> briquePerdu;
	private boolean newBrique;
	private boolean partiFini;
	private Color actualColor, green;
	private int yPosHigher;
	Image dbImage;
	private JTextField fieldReponse;
	private int compteurReponse;
	
	public Plateau(){
		super();
		initialiserPlateau();
		fieldReponse.addKeyListener(new KeyListener() {			
			@Override
			public void keyTyped(KeyEvent e) {
			}			
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
					if (brique.checkReponse(fieldReponse.getText())){
						fieldReponse.setBackground(Color.white);
						startNewBrique();
						briquePerdu.remove(briquePerdu.size()-1);
						score.updateScore(1);
						compteurReponse++;
					}
					else{
						fieldReponse.setBackground(Color.red);
						score.updateScore(-1);
						compteurReponse = 0;
					}
					if (compteurReponse == 5){
						brique.levelUp();
						score.lvlUp();
						compteurReponse = 0;
					}
					fieldReponse.setText("");
				}
			}			
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		green = new Color(34, 139, 34);
	}
	public Color generateRandomColor(){
		Random r = new Random();
		int color = r.nextInt(3);
		switch (color) {
			case 0:
				return Color.blue;
			case 1:
				return Color.red;
			case 2:
				return green;
			default:
				return Color.black;
		}
	}
	public void setFocusField(){
		fieldReponse.requestFocus();
	}
	public void generateRandomX(){
		Random r = new Random();
	    brique.setXPos(r.nextInt(this.getWidth()-brique.getWidth()));
	    newBrique = false;
	}
	public Score getScore(){
		return score;
	}
	public void startNewBrique(){
		brique.setRunning(false);
		briquePerdu.add(new Brique(brique));
		brique = new Brique(listeQuestion.getRandomQuestion());
		new Thread(brique).start();
		newBrique = true;
		yPosHigher=0;
	}
	@Override
	public void paint(Graphics g){
		if (newBrique){
			generateRandomX();
			actualColor = generateRandomColor();
		}
		dbImage = createImage(getWidth(), getHeight());
		paintComponent(g);
	}
	// Dessine les objets à partir de la liste listSourisWallTrap et du chat
	public void paintComponent(Graphics g){
		// remet a blanc la zone
		g.setColor(Color.white);
	    g.fillRect(0, 0, this.getWidth(), this.getHeight());
	    // dessin de la ligne de perdu
	    g.setColor(Color.black);
	    g.drawLine(0, 25, this.getWidth(), 25);
	    // dessine la brique
	    g.setColor(actualColor);
		g.fillRect(brique.getXPos(), brique.getYPos(), brique.getWidth(), brique.getHeight());
	    g.setColor(Color.white);
		g.drawString(brique.getText(), brique.getXPos()+5, brique.getYPos()+brique.getHeight()-5);
		// on met a jour la hauteur à laquelle la brique va devoir s'arreter
		for (int i=0; i<briquePerdu.size(); i++) {
			Brique bp = briquePerdu.get(i);
			if ((brique.getXPos() >= bp.getXPos() && brique.getXPos() <= bp.getXPos()+bp.getWidth())
					|| (brique.getXPos()+brique.getWidth() >= bp.getXPos() && brique.getXPos()+brique.getWidth() <= bp.getXPos()+bp.getWidth())
					|| (brique.getXPos() < bp.getXPos() && brique.getXPos()+brique.getWidth() > bp.getXPos()+bp.getWidth())){
				if (yPosHigher == 0){
					yPosHigher = bp.getYPos();
				}
				else if (yPosHigher < 30){
					partiFini = true;
					brique.setRunning(false);
				}
				else{
					if (bp.getYPos() < yPosHigher){
						yPosHigher = bp.getYPos();
					}
				}
			}
		}
		if (!partiFini){
			// quand on passe cette condition, la brique devient perdu
			if (yPosHigher==0 && brique.getYPos() >= this.getHeight()-brique.getHeight()*2){
				startNewBrique();
			//Si yPosHigher!=0 alors la brique va rencontrer une brique perdue
			}else if(yPosHigher>0 && brique.getYPos()>=yPosHigher-brique.getHeight()){
				startNewBrique();
			}
		}
		// on dessine les briques perdu
		for (int i=0; i<briquePerdu.size(); i++) {
			Brique bp = briquePerdu.get(i);
			g.setColor(Color.gray);
			g.fillRect(bp.getXPos(), bp.getYPos(), bp.getWidth(), bp.getHeight());
		    g.setColor(Color.white);
			g.drawString(bp.getText(), bp.getXPos()+5, bp.getYPos()+bp.getHeight()-5);
		}
		repaint();
	}
	
	public void afficherAPropos(){
		String informations = "Bienvenue sur EduTris.\n"
				+ "Dev by Biscay Thibaut, Maza Yanis et Gaëtan Le Maître\n"
				+ "Licence Pro 'Logiciels Libres', Université d'Angers.";

		pausePartie();
		int input = JOptionPane.showConfirmDialog(null,informations,"A propos du projet",JOptionPane.OK_OPTION,JOptionPane.INFORMATION_MESSAGE,null);
		if(input == JOptionPane.OK_OPTION || input == JOptionPane.NO_OPTION || input == JOptionPane.CLOSED_OPTION){
			pausePartie();
		}
	}
	
	public void recommencerPartie(){
		briquePerdu.clear();
		score.resetScore();
		startNewBrique();
		briquePerdu.remove(briquePerdu.size()-1);
		Brique.vitesse = 300;
		compteurReponse=0;
	}
	
	public void pausePartie(){
		if(brique.getRunning()){
			brique.setRunning(false);
		}else{
			brique.setRunning(true);
			new Thread(brique).start();
		}
	}
	
	public void initialiserPlateau(){
		compteurReponse = 0;
		score = new Score(0);
		score.addPropertyChangeListener(score);
		fieldReponse = new JTextField();
		
		score.addFieldReponse(fieldReponse);
		setFocusable(true);
		
		briquePerdu = new ArrayList<Brique>();
		listeQuestion = new QuestionListe();
		brique = new Brique(listeQuestion.getRandomQuestion());
		newBrique = true;
		partiFini = false;
		yPosHigher = 0;
	}
}
