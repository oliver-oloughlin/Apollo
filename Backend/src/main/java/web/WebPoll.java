package web;

public class WebPoll {

  private Long code;
  private String title;
  private String timeToStop;
  private boolean privatePoll;
  private String owner;
  
  public WebPoll(Long code, String title, String timeToStop, boolean privatePoll, String owner) {
    this.code = code;
    this.title = title;
    this.timeToStop = timeToStop;
    this.privatePoll = privatePoll;
    this.owner = owner;
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

  public String getOwner() {
    return owner;
  }  
}
