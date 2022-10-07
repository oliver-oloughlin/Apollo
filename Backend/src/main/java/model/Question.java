package model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	
	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<Vote> votes;
	
	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<IoTDevice> devices;

	public Question () {}
	
	public Question(String text, Set<Vote> votes, Set<IoTDevice> devices) {
		this.text = text;
		this.votes = votes;
		this.devices = devices;
	}

	public Long getId() {
	  return id;
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
	
	public Set<IoTDevice> getDevices() {
	  return devices;
	}
	
	public void addVote(Vote vote) {
		votes.add(vote);
	}
}
