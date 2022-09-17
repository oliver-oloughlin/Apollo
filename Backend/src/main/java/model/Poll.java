package model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "POLL")
public class Poll {

	@Id
	private Long code;
	private String title;
	private String timeToStop;
	private boolean privatePoll;
	private boolean isClosed;
	
	@ManyToOne
	@JoinTable(
		name = "POLL_OWNER",
		joinColumns = @JoinColumn(name = "POLL_ID"), 
		inverseJoinColumns = @JoinColumn(name = "ACCOUNT_ID"))
	private Account owner;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(
		name = "POLL_QUESTIONS",
		joinColumns = @JoinColumn(name = "POLL_ID"), 
		inverseJoinColumns = @JoinColumn(name = "QUESTION_ID"))
	private Set<Question> questions;
	
	public Poll () {}
	
	public Poll(Long code, String title, String timeToStop, boolean privatePoll, Account owner) {
		this.title = title;
		this.code = code;
		this.timeToStop = timeToStop;
		this.privatePoll = privatePoll;
		this.owner = owner;
		this.questions = new HashSet<Question>();
		this.isClosed = false;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Long getCode() {
		return code;
	}
	
	public String getTimeToStop() {
		return timeToStop;
	}
	
	public void setTimeToStop(String timeToStop) {
		this.timeToStop = timeToStop;
	}
	
	public boolean isPrivatePoll() {
		return privatePoll;
	}
	
	public void setPrivatePoll(boolean privatePoll) {
		this.privatePoll = privatePoll;
	}
	
	public Set<Question> getQuestions(){
		return questions;
	}
	
	public void addQuestion(Question question) {
		questions.add(question);
	}

	public boolean isClosed() {
		return isClosed;
	}

	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}
}
