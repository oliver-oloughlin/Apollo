package poll.app;

import java.util.Random;

public class Poll {
	
	int ID; // Poll ID to show on screen for connection
	String AUTHOR; // Author of Poll
	String POLLCODE;
	
	public Poll(String author) {
		this.AUTHOR = author;
		this.POLLCODE = makePollCode();
	}

	private String makePollCode() {
		Random random = new Random;
		
		return 0;
	}
	
	private class Question {
		
		public Question() {
			//TODO make constructor
		}
	}
}
