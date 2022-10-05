package client;

public class DeviceClient {

  private long questionId;

  public DeviceClient(long questionId) {
    this.questionId = questionId;
  }

  public long getQuestionId() {
    return questionId;
  }
}
