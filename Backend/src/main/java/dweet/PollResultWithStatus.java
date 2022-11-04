package dweet;

import messaging_system.PollResult;
import model.Poll;

@SuppressWarnings("unused")
public class PollResultWithStatus extends PollResult {

  private String status;

  public PollResultWithStatus(Poll poll, String status) {
    super(poll);
    this.status = status;
  }

}
