package modelweb;

public class WebVote {

  private int green;
  private int red;
  private Long questionId;
  private String voterEmail;
  
  public WebVote(int green, int red, Long questionId, String voterEmail) {
    this.green = green;
    this.red = red;
    this.questionId = questionId;
    this.voterEmail = voterEmail;
  }
  
  public int getGreen() {
    return green;
  }
  public int getRed() {
    return red;
  }
  public Long getQuestionId() {
    return questionId;
  }
  public String getVoterEmail() {
    return voterEmail;
  }
}
