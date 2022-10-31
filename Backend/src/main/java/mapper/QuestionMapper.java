package mapper;

import java.util.Set;
import java.util.stream.Collectors;

import model.Poll;
import model.Question;
import model.Vote;
import modelweb.WebQuestion;
import service.PollService;
import service.VoteService;

public class QuestionMapper {

  VoteService voteService;
  PollService pollService;

  public QuestionMapper(VoteService voteService, PollService pollService) {
    this.voteService = voteService;
    this.pollService = pollService;
  }

  public Question mapWebQuestionToQuestion(WebQuestion webQuestion) {
    Set<Vote> votes = webQuestion.getVoteIds().stream()
        .map(id -> voteService.getVote(id))
        .collect(Collectors.toSet());

    Poll poll = pollService.getPoll(webQuestion.getPollCode());
    long id = webQuestion.getId();

    if (id == 0) {
      return new Question(webQuestion.getText(), poll, votes);
    }
    return new Question(id, webQuestion.getText(), poll, votes);
  }

  public WebQuestion mapQuestionToWebQuestion(Question question) {

    if (question == null) {
      return null;
    }

    Set<Long> voteIds = question.getVotes().stream()
        .map(vote -> vote.getId())
        .collect(Collectors.toSet());

    return new WebQuestion(question.getId(), question.getText(), question.getPoll().getCode(), voteIds);
  }
}
