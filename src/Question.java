/**
 * 
 * La classe question
 *
 */
public class Question {
	
	private String question;
	private String reponse;

	public Question(String question, String reponse) {
		this.question = question;
		this.reponse = reponse;
	}
	
	public String getQuestion(){
		return question;
	}
	public boolean checkReponse(String reponse){
		return this.reponse.equalsIgnoreCase(reponse);
	}

}
