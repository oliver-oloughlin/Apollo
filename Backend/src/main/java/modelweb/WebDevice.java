package modelweb;

import java.util.Set;

public class WebDevice {

  private long token;
  private long questionId;
  private Set<Long> voteIds;

  public WebDevice(long token, long questionId, Set<Long> voteIds) {
    this.token = token;
    this.questionId = questionId;
    this.voteIds = voteIds;
  }

  public long getToken() {
    return token;
  }
  
  public long getQuestionId() {
    return questionId;
  }
  
  public Set<Long> getVoteIds() {
    return voteIds;
  }
}
