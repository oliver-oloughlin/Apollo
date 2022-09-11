package poll.app;

public class Poll {
	
	int ID; // Poll ID to show on screen for connection
	String AUTHOR; // Author of Poll
	int POLLCODE;
	
	public Poll(String author) {
		this.AUTHOR = author;
		this.POLLCODE = makePollCode();
	}

	private int makePollCode() {
		// TODO make algorithm for Code
		return 0;
	}
	
	private class Question {
		
		public Question() {
			//TODO make constructor
		}
	}
}
