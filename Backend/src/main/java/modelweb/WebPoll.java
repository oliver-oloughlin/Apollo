package modelweb;

import java.util.Set;

public class WebPoll {

  private Long code;
  private String title;
  private String timeToStop;
  private boolean privatePoll;
  private boolean closed;
  private String ownerEmail;
  private Set<Long> questionIds;
  
  public WebPoll(Long code, String title, String timeToStop, boolean privatePoll, boolean closed, String ownerEmail, Set<Long> questionIds) {
    this.code = code;
    this.title = title;
    this.timeToStop = timeToStop;
    this.privatePoll = privatePoll;
    this.closed = closed;
    this.ownerEmail = ownerEmail;
    this.questionIds = questionIds;
  }

  public Long getCode() {
    return code;
  }
  
  public String getTitle() {
    return title;
  }

  public String getTimeToStop() {
    return timeToStop;
  }

  public boolean isPrivatePoll() {
    return privatePoll;
  }
  
  public boolean isClosed() {
    return closed;
  }

  public String getOwnerEmail() {
    return ownerEmail;
  }
  
  public Set<Long> getQuestionIds() {
    return questionIds;
  }
}
