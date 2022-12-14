package model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "POLL")
public class Poll {

  @Id
  private Long code;
  private String title;
  private String timeToStop;
  private boolean privatePoll;
  private boolean closed;

  @ManyToOne
  @JoinColumn(name = "accountId")
  private Account owner;

  @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
  private Set<Question> questions;

  public Poll() {
  }

  public Poll(Long code, String title, String timeToStop, boolean privatePoll, boolean closed, Account owner,
      Set<Question> questions) {
    this.title = title;
    this.code = code;
    this.timeToStop = timeToStop;
    this.privatePoll = privatePoll;
    this.owner = owner;
    this.closed = closed;
    this.questions = questions;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Long getCode() {
    return code;
  }

  public String getTimeToStop() {
    return timeToStop;
  }

  public void setTimeToStop(String timeToStop) {
    this.timeToStop = timeToStop;
  }

  public boolean isPrivatePoll() {
    return privatePoll;
  }

  public void setPrivatePoll(boolean privatePoll) {
    this.privatePoll = privatePoll;
  }

  public Account getOwner() {
    return owner;
  }

  public void setOwner(Account owner) {
    this.owner = owner;
  }

  public Set<Question> getQuestions() {
    return questions;
  }

  public void setQuestions(Set<Question> questions) {
    this.questions = questions;
  }

  public void addQuestion(Question question) {
    questions.add(question);
  }

  public void removeQuestion(Question question) {
    questions.remove(question);
  }

  public boolean isClosed() {
    return closed;
  }

  public void setClosed(boolean isClosed) {
    this.closed = isClosed;
  }
}
