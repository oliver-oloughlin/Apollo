package mapper;

import model.Account;
import model.Question;
import model.Vote;
import modelweb.WebVote;
import service.AccountService;
import service.QuestionService;

public class VoteMapper {

  AccountService accountService;
  QuestionService questionService;

  public VoteMapper(AccountService accountService, QuestionService questionService) {
    this.accountService = accountService;
    this.questionService = questionService;
  }

  public Vote mapWebVoteToVote(WebVote webVote) {

    Question question = questionService.getQuestion(webVote.getQuestionId());

    if (webVote.getVoterEmail() != null) {
      Account voter = accountService.getAccount(webVote.getVoterEmail());
      return new Vote(webVote.getGreen(), webVote.getRed(), question, voter);
    } else {
      return new Vote(webVote.getGreen(), webVote.getRed(), question);
    }
  }

  public WebVote mapVoteToWebVote(Vote vote) {

    if (vote == null) {
      return null;
    }

    if (vote.getVoter() == null) {
      return new WebVote(vote.getGreen(), vote.getRed(), vote.getQuestion().getId(), null);
    } else {
      return new WebVote(vote.getGreen(), vote.getRed(), vote.getQuestion().getId(), vote.getVoter().getEmail());
    }
  }
}
