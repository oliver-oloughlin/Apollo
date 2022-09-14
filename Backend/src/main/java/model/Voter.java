package model;

import javax.persistence.OneToMany;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "VOTER")
public class Voter {

	/**
	 * PK in db
	 * name@domain.toppdomain
	 */
	@Id
	private String username;

	/**
	 * User password
	 * TODO: hash password
	 */
	private String password;

	/**
	 * Describe if user is Admin
	 * by default false
	 */
	private boolean isAdmin;
	
	@OneToMany(mappedBy = "owner")
	private Set<Poll> polls;
	
	@OneToMany(mappedBy = "voter")
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

	public void addVote(Vote vote){
		votes.add(vote);
	}

}
