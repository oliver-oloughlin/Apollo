package dweet;

import messaging_system.PollResult;
import model.Poll;

@SuppressWarnings("unused")
public class PollResultWithStatus extends PollResult {

  private boolean active;

  public PollResultWithStatus(Poll poll, boolean active) {
    super(poll);
    this.active = active;
  }

}
