package model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "IOT_DEVICE")
public class IoTDevice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long token;
	
	@OneToMany(mappedBy = "device", cascade = CascadeType.PERSIST)
	private Set<Vote> votes = new HashSet<Vote>();
	
	@ManyToOne
	@JoinColumn(name = "questionId")
	private Question question;
	
	public IoTDevice () {}
	
	public IoTDevice (Question question) {
		this.question = question;
	}
	
	public long getToken () {
		return token;
	}

	public Set<Vote> getVotes() {
		return votes;
	}
	
	public void addVote(Vote vote) {
		votes.add(vote);
	}
}
