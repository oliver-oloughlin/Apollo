package poll.app;

import java.io.Serializable;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class Poll implements Serializable{
	
	private final String TITLE; //Required
	private String author = "Incognito"; //optional, should give INCOGNITO if applicable
	private String POLLCODE; //Required
	private Question question; //Required
	
	public Poll(PollBuilder builder) {
		this.TITLE = builder.TITLE;
		this.author = builder.AUTHOR;
		this.POLLCODE = makePollCode();
		this.question = new Question();
		
	}

	private String makePollCode() {
		Random random = new Random();
		int number = random.nextInt(10000);
		return String.format("%04d", number);
	}
	
	@PostConstruct
	public String getPollAndAnswers() {
		return "Poll on " + this.TITLE + " red answers: " + question.red + " green answers: " + question.green;
	}

	public String getAuthor() {
		return author;
	}


	public String getPOLLCODE() {
		return POLLCODE;
	}

	@Component
	public static class PollBuilder {
		private String TITLE;
		private String AUTHOR;

		public PollBuilder(String title) {
			this.TITLE = title;
		}
		
		public PollBuilder author(String author) {
			this.AUTHOR = author;
			return this;
		}
		
		//Returns final object
		public Poll build(){
			Poll poll = new Poll(this);
			
			return poll;
		}
		
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

		public int getGreen() {
			return green;
		}

		public int getRed() {
			return red;
		}

		public void setRed(int red) {
			this.red = red;
		}

		public void setGreen(int green) {
			this.green = green;
		}
		
		
	}
}
