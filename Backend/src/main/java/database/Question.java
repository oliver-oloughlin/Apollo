package database;

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
@Table(name = "QUESTION")
public class Question {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String text;
	
	@ManyToOne
	@JoinTable(
		name = "QUESTIONS_POLL",
		joinColumns = @JoinColumn(name = "QUESTION_ID"), 
		inverseJoinColumns = @JoinColumn(name = "POLL_ID"))
	private Poll poll;
	
	@OneToMany(mappedBy = "question")
	private Set<Vote> votes;

	public Question () {}
	
	public Question(String text) {
		this.text = text;
		this.votes = new HashSet<Vote>();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public void setPoll(Poll poll) {
		this.poll = poll;
	}
	
	public void addVote(Vote vote) {
		votes.add(vote);
	}
}
