package messaging_system;

import java.util.List;

import model.Poll;

@SuppressWarnings("unused")
public class PollResult {

  private String title;
  private List<QuestionResult> answers;

  public PollResult(Poll poll) {
    this.title = poll.getTitle();
    this.answers = new Util().getQuestionResultsFromPoll(poll);
  }
}
