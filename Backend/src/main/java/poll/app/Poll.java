package poll.app;

import java.util.Random;

public class Poll {
	
	// Poll ID to show on screen for connection
	int ID; 
	// Author of the Poll
	String AUTHOR; 
	String POLLCODE;
	Question question;
	
	public Poll() {
		this.AUTHOR = "Incognito";
		this.POLLCODE = makePollCode();
		this.question = new Question();
	}
	
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
