package utils;

public class VoteCount {

  private int greenVotes;
  private int redVotes;

  public VoteCount(int greenVotes, int redVotes) {
    this.greenVotes = greenVotes;
  }

  public int getGreenVotes() {
    return greenVotes;
  }

  public void setGreenVotes(int greenVotes) {
    this.greenVotes = greenVotes;
  }

  public int getRedVotes() {
    return redVotes;
  }

  public void setRedVotes(int redVotes) {
    this.redVotes = redVotes;
  }
}
