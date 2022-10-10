package modelweb;

import java.util.Set;

public class WebQuestion {

  private long id;
  private String text;
  private long pollCode;
  private Set<Long> voteIds;
  private Set<Long> deviceTokens;
  
  public WebQuestion(long id, String text, long pollCode, Set<Long> voteIds, Set<Long> deviceTokens) {
    this.id = id;
    this.text = text;
    this.voteIds = voteIds;
    this.deviceTokens = deviceTokens;
    this.pollCode = pollCode;
  }

  public long getId() {
    return id;
  }
  
  public String getText() {
    return text;
  }
  
  public long getPollCode() {
    return pollCode;
  }

  public Set<Long> getVoteIds() {
    return voteIds;
  }

  public Set<Long> getDeviceTokens() {
    return deviceTokens;
  }
}
