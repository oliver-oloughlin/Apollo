package utils;

import model.Account;
import model.IoTDevice;
import model.Poll;
import model.Question;
import model.Vote;
import service.AccountService;
import service.IoTService;
import service.QuestionService;
import web.WebPoll;
import web.WebVote;

public class Mapper {

  AccountService accountService;
  QuestionService questionService;
  IoTService deviceService;
  
  public Mapper(AccountService accountService, QuestionService questionService, IoTService deviceService) {
    this.accountService = accountService;
    this.questionService = questionService;
    this.deviceService = deviceService;
  }
  
  public Poll mapWebPollToPoll(WebPoll webPoll) {
    Account owner = accountService.getAccount(webPoll.getOwner());
    return new Poll(webPoll.getCode(), webPoll.getTitle(), webPoll.getTimeToStop(), webPoll.isPrivatePoll(), owner);
  }
  
  public Vote mapWebVoteToVote(WebVote webVote) {
    
    Question question = questionService.getQuestion(webVote.getQuestionId());
    
    if(webVote.getVoter() != null) {
      Account voter = accountService.getAccount(webVote.getVoter());
      return new Vote(webVote.getGreen(), webVote.getRed(), question, voter);
    }
    else {
      IoTDevice device = deviceService.getDevice(webVote.getDevice());
      return new Vote(webVote.getGreen(), webVote.getRed(), question, device);
    }
  }
}
