import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * 
 * Contient la liste de toutes les question qui pourront apparaitre dans les briques
 *
 */
public class QuestionListe extends ArrayList<Question> {

	private static final long serialVersionUID = 1006433527661347202L;
	public QuestionListe() {
		super();
		remplirQuestions();
	}
	public QuestionListe(Collection<? extends Question> c) {
		super(c);
	}
	public QuestionListe(int initialCapacity) {
		super(initialCapacity);
	}

	public void remplirQuestions(){
		try {
			InputStreamReader isr = new InputStreamReader(getClass().getResourceAsStream("/question/questions.csv"));
			BufferedReader br = new BufferedReader(isr);
			String ligne;
			while ((ligne = br.readLine()) != null){
				String question = ligne.split(";")[0];
				String reponse = ligne.split(";")[1];
				this.add(new Question(question, reponse));
			}
			br.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	public Question getRandomQuestion(){
		Random r = new Random();
	    int idQuestion = r.nextInt(this.size());
	    return this.get(idQuestion);
	}
}
