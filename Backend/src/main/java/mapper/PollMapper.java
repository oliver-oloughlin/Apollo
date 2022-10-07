package mapper;

import java.util.Set;
import java.util.stream.Collectors;

import model.Account;
import model.Poll;
import model.Question;
import modelweb.WebPoll;
import service.AccountService;
import service.QuestionService;

public class PollMapper {

  AccountService accountService;
  QuestionService questionService;
  
  public PollMapper(AccountService accountService, QuestionService questionService) {
    this.accountService = accountService;
    this.questionService = questionService;
  }
  
  public Poll mapWebPollToPoll(WebPoll webPoll) {
    Account owner = accountService.getAccount(webPoll.getOwnerEmail());
    Set<Question> questions = webPoll.getQuestionIds()
        .stream().map(id -> questionService.getQuestion(id))
        .collect(Collectors.toSet());
    return new Poll(webPoll.getCode(), webPoll.getTitle(), webPoll.getTimeToStop(),
        webPoll.isPrivatePoll(), owner, questions);
  }
  
  public WebPoll mapPollToWebPoll(Poll poll) {
    
    if(poll == null) {
      return null;
    }
    
    Set<Long> questionIds = poll.getQuestions()
        .stream().map(question -> question.getId())
        .collect(Collectors.toSet());
    
    return new WebPoll(poll.getCode(), poll.getTitle(), poll.getTimeToStop(),
        poll.isPrivatePoll(), poll.isClosed(), poll.getOwner().getEmail(), questionIds);
  }
}
