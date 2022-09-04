package poll.app;

import java.io.Serializable;
import java.util.Random;

public class Poll implements Serializable{
	
	final String  TITLE;
	// Poll ID to show on screen for connection
	int ID; 
	// Author of the Poll
	private String AUTHOR; 
	private String POLLCODE;
	private Question question;
	
	public Poll(String title) {
		this.TITLE = title;
		this.AUTHOR = "Incognito";
		this.POLLCODE = makePollCode();
		this.question = new Question();
	}
	
	public Poll(String title, String author) {
		this.TITLE = title;
		this.AUTHOR = author;
		this.POLLCODE = makePollCode();
		this.question = new Question();
	}

	private String makePollCode() {
		Random random = new Random();
		int number = random.nextInt(10000);
		return String.format("%04d", number);
	}
	
	public String getPollAndAnswers() {
		return "Poll on " + this.TITLE + "red answers: " + question.red + " green answers: " + question.green;
	}
	
	
	
	public int getID() {
		return ID;
	}

	public void setID(int id) {
		ID = id;
	}

	public String getAUTHOR() {
		return AUTHOR;
	}

	public void setAUTHOR(String author) {
		AUTHOR = author;
	}

	public String getPOLLCODE() {
		return POLLCODE;
	}

	public void setPOLLCODE(String pollcode) {
		POLLCODE = pollcode;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}



	private class Question {
		//Questions are answered by RED or GREEN votes.
		private int red = 0;
		private int green = 0;
		
		public Question() {
			//TODO make constructor
		}
		
		public void vote(boolean vote) {
			if(vote) setRed(getRed() + 1);
			else setGreen(getGreen() + 1);
		}
		
		//Oh the boilerplate.
		public int getGreen() {
			return green;
		}

		public void setGreen(int green) {
			this.green = green;
		}

		public int getRed() {
			return red;
		}

		public void setRed(int red) {
			this.red = red;
		}
	}
}
