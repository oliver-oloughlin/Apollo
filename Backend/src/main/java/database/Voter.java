package database;

import interfaces.VoterDAO;

import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "VOTER")
public class Voter implements VoterDAO {

	@Id
	private String username;
	private String password;
	private boolean isAdmin;
	
	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
	private Set<Poll> polls;
	
	@OneToMany(mappedBy = "voter", cascade = CascadeType.ALL)
	private Set<Vote> votes;
	
	public Voter () {}
	
	public Voter(String username, String password, boolean isAdmin) {
		this.username = username;
		this.password = password;
		this.isAdmin = isAdmin;
		
		polls = new HashSet<Poll>();
		votes = new HashSet<Vote>();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public Set<Poll> getPolls(){
		return polls;
	}
	
	public Set<Vote> getVotes(){
		return votes;
	}
	
	public void addPoll(Poll poll) {
		polls.add(poll);
	}

	@Override
	public Voter getVoter(String username, String password) {
		return ;
	}

	public boolean addVote(Vote vote) {
		votes.add(vote);
		return true;
	}

	@Override
	public Voter updateVoter(Voter voter) {
		return null;
	}

	@Override
	public boolean deleteVoter(Voter voter) {
		return false;
	}
}
