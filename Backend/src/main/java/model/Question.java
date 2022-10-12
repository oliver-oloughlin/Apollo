package model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	@JoinColumn(name = "poll")
	private Poll poll;
	
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<Vote> votes;
	
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private Set<IoTDevice> devices;

	public Question () {}
	
	public Question(String text, Poll poll, Set<Vote> votes, Set<IoTDevice> devices) {
		this.text = text;
		this.poll = poll;
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
	
	public Poll getPoll() {
	  return poll;
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
	
	public void addDevice(IoTDevice device) {
      devices.add(device);
  }
}
