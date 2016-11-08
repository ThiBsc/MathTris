import javax.swing.JLabel;

/**
 * 
 * Objet brique (qui va tomber dans le plateau de jeu)
 *
 */
public class Brique extends JLabel implements Runnable {
	
	private static final long serialVersionUID = 1L;
	private Question question;
	private int briqueWidth;
	private int briqueHeight;
	private int xPos;
	private int yPos;
	private volatile boolean running;
	public static int vitesse = 300;

	public Brique(Question q) {
		super();
		question = q;
		setText(q.getQuestion());
		briqueWidth = this.getFontMetrics(getFont()).stringWidth(this.getText())+10;
		briqueHeight = 20;
		xPos = 0;
		yPos = 0;
		running = true;
	}
	public Brique(Brique b) {
		super();
		question = b.getQuestion();
		xPos = b.getXPos();
		yPos = b.getYPos();
		setText(question.getQuestion());
		briqueWidth = b.getWidth();
		briqueHeight = b.getHeight();
		running = false;
	}
	
	public boolean getRunning(){
		return this.running;
	}
	public void setRunning(boolean run){
		running = run;
	}
	public Question getQuestion(){
		return question;
	}
	public boolean checkReponse(String reponse){
		return question.checkReponse(reponse);
	}
	public int getWidth(){
		return briqueWidth;
	}
	public int getHeight(){
		return briqueHeight;
	}
	public int getXPos(){
		return xPos;
	}
	public void setXPos(int xPos){
		this.xPos = xPos;
	}
	public void setYPos(int yPos){
		this.yPos = yPos;
	}
	public int getYPos(){
		return yPos;
	}
	public void levelUp(){
		if (vitesse > 100)
			vitesse -= 20;
		else if (50 < vitesse && vitesse <= 100)
			vitesse -= 10;
		else
			vitesse -= 5;
	}
	@Override
	public void run() {
		try {
			while(running){
				Thread.sleep(vitesse);
				if (running)
					yPos += briqueHeight;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
