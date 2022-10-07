package mapper;

import model.Account;
import model.IoTDevice;
import model.Question;
import model.Vote;
import modelweb.WebVote;
import service.AccountService;
import service.IoTService;
import service.QuestionService;

public class VoteMapper {

  AccountService accountService;
  QuestionService questionService;
  IoTService deviceService;
  
  public VoteMapper(AccountService accountService, QuestionService questionService, IoTService deviceService) {
    this.accountService = accountService;
    this.questionService = questionService;
    this.deviceService = deviceService;
  }
  
  
  public Vote mapWebVoteToVote(WebVote webVote) {
      
      Question question = questionService.getQuestion(webVote.getQuestionId());
      
      if(webVote.getVoterEmail() != null) {
        Account voter = accountService.getAccount(webVote.getVoterEmail());
        return new Vote(webVote.getGreen(), webVote.getRed(), question, voter);
      }
      else {
        IoTDevice device = deviceService.getDevice(webVote.getDeviceToken());
        return new Vote(webVote.getGreen(), webVote.getRed(), question, device);
      }
  }
  
  public WebVote mapVoteToWebVote(Vote vote) {
    
    if(vote == null) {
      return null;
    }
    
    if(vote.getVoter() == null) {
      return new WebVote(vote.getGreen(), vote.getRed(), vote.getQuestion().getId(), null, vote.getDevice().getToken());
    }
    else {
      return new WebVote(vote.getGreen(), vote.getRed(), vote.getQuestion().getId(), vote.getVoter().getEmail(), 0);
    }
  }
}
