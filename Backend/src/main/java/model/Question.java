package model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "QUESTION")
public class Question {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String text;
	
	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
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
	
	public Set<Vote> getVotes() {
		return votes;
	}
	
	public void addVote(Vote vote) {
		votes.add(vote);
	}
}
