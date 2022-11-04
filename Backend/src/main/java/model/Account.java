package model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ACCOUNT")
public class Account {

  @Id
  private String email;
  private String password;
  private String salt;
  private boolean isAdmin;

  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Poll> polls;

  @OneToMany(mappedBy = "voter", cascade = CascadeType.REMOVE)
  private Set<Vote> votes;

  public Account() {
  }

  public Account(String email, String password, Set<Poll> polls,
      Set<Vote> votes) {
    this.email = email;
    this.password = password;
    this.isAdmin = false;
    this.polls = polls;
    this.votes = votes;
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

  public Set<Poll> getPolls() {
    return polls;
  }

  public void setPolls(Set<Poll> polls) {
    this.polls = polls;
  }

  public Set<Vote> getVotes() {
    return votes;
  }

  public void setVotes(Set<Vote> votes) {
    this.votes = votes;
  }

  public void addPoll(Poll poll) {
    polls.add(poll);
  }

  public void removePoll(Poll poll) {
    polls.remove(poll);
  }

  public void addVote(Vote vote) {
    votes.add(vote);
  }

  public void removeVote(Vote vote) {
    votes.remove(vote);
  }
}
