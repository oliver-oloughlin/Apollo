package model;

import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ACCOUNT")
public class Account {

	@Id
	private String email;
	private String password;
	private boolean isAdmin;
	
	@Enumerated(EnumType.STRING)
	private AccountType accountType;
	
	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Poll> polls = new HashSet<Poll>();
	
	@OneToMany(mappedBy = "voter", cascade = CascadeType.PERSIST)
	private Set<Vote> votes = new HashSet<Vote>();
	
	public Account () {}
	
	public Account(String email, String password, boolean isAdmin, AccountType accountType) {
		this.email = email;
		this.password = password;
		this.isAdmin = isAdmin;
		this.accountType = accountType;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public String getemail() {
		return email;
	}

	public void setemail(String email) {
		this.email = email;
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
