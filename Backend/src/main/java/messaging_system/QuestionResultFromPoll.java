package messaging_system;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import model.Poll;
import model.Question;

public class QuestionResultFromPoll {

  public static List<QuestionResult> getQuestionResultsFromPoll(Poll poll) {

    Set<Question> questions = poll.getQuestions();
    List<QuestionResult> questionResults = new ArrayList<QuestionResult>();

    for (Question question : questions) {
      String text = question.getText();
      int yesVotes = question.getVotes().stream().mapToInt(vote -> vote.getGreen()).sum();
      int noVotes = question.getVotes().stream().mapToInt(vote -> vote.getRed()).sum();
      questionResults.add(new QuestionResult(text, yesVotes, noVotes));
    }
    return questionResults;
  }
}
