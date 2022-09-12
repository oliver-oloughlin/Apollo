package database;

import interfaces.PollDAO;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String title;
	private int code;
	private String timeToStop;
	private boolean privatePoll;
	
	@ManyToOne
	@JoinTable(
		name = "POLL_OWNER",
		joinColumns = @JoinColumn(name = "POLL_ID"), 
		inverseJoinColumns = @JoinColumn(name = "USER_ID"))
	private Voter owner;
	
	@OneToMany(mappedBy = "poll")
	private Set<Question> questions;
	
	public Poll () {}
	
	public Poll(String title, int code, String timeToStop, boolean privatePoll, Voter owner) {
		this.title = title;
		this.code = code;
		this.timeToStop = timeToStop;
		this.privatePoll = privatePoll;
		this.owner = owner;
		this.questions = new HashSet<Question>();
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
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
	
	public void addQuestion(Question question) {
		questions.add(question);
	}
}
