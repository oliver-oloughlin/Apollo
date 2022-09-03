package poll.app;

import java.util.Random;

public class Poll {
	
	int ID; // Poll ID to show on screen for connection
	String AUTHOR; // Author of Poll
	String POLLCODE;
	Question question;
	
	public Poll(String author) {
		this.AUTHOR = author;
		this.POLLCODE = makePollCode();
		this.question = new Question();
	}

	private String makePollCode() {
		Random random = new Random();
		int number = random.nextInt(10000);
		return String.format("%04d", number);
	}
	
	private class Question {
		
		public Question() {
			//TODO make constructor
		}
	}
}
