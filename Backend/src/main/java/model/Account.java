package model;

import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "ACCOUNT")
public class Account {

	@Id
	private String email;
	private String password;
	private String salt;
	private boolean isAdmin;
	
	@Enumerated(EnumType.STRING)
	private AccountType accountType;
	
	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Poll> polls;
	
	@OneToMany(mappedBy = "voter", cascade = CascadeType.REMOVE)
	private Set<Vote> votes;
	
	public Account () {}
	
	public Account(String email, String password, boolean isAdmin,AccountType accountType, Set<Poll> polls, Set<Vote> votes) {
		this.email = email;
		this.password = password;
		this.isAdmin = isAdmin;
		this.accountType = accountType;
		this.polls = polls;
		this.votes = votes;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getSalt() {
	    return salt;
	}
	
	public void setSalt(String salt) {
	    this.salt = salt;
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
