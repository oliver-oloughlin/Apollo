package client;

public class VoteClient {

  private int green;
  private int red;
  private Long questionId;
  private String voter;
  private String device;
  
  public VoteClient(int green, int red, Long questionId, String voter, String device) {
    this.green = green;
    this.red = red;
    this.questionId = questionId;
    this.voter = voter;
    this.device = device;
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
  public String getVoter() {
    return voter;
  }
  public String getDevice() {
    return device;
  }
  
  
}
