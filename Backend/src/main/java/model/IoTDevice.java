package model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "IOT_DEVICE")
public class IoTDevice {

	@Id
	private Long token;
	
	@OneToMany(mappedBy = "device", cascade = CascadeType.REMOVE)
	private Set<Vote> votes;
	
	@ManyToOne
	@JoinColumn(name = "questionId")
	private Question question;
	
	public IoTDevice () {}
	
	public IoTDevice (long token, Question question, Set<Vote> votes) {
		this.token = token;
	    this.question = question;
	    this.votes = votes;
	}
	
	public long getToken () {
		return token;
	}
	
	public Question getQuestion() {
	  return question;
	}

	public Set<Vote> getVotes() {
		return votes;
	}
	
	public void addVote(Vote vote) {
		votes.add(vote);
	}
	
	public void removeVote(Vote vote) {
      votes.remove(vote);
    }
}
