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

  @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
  private Set<Vote> votes;

  public Question() {
  }

  public Question(String text, Poll poll, Set<Vote> votes) {
    this.text = text;
    this.poll = poll;
    this.votes = votes;
  }

  public Question(long id, String text, Poll poll, Set<Vote> votes) {
    this.id = id;
    this.text = text;
    this.poll = poll;
    this.votes = votes;
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

  public void setVotes(Set<Vote> votes) {
    this.votes = votes;
  }

  public void addVote(Vote vote) {
    votes.add(vote);
  }

  public void removeVote(Vote vote) {
    votes.remove(vote);
  }
}
