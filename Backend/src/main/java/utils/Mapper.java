package utils;

import client.PollClient;
import client.VoteClient;
import model.Account;
import model.IoTDevice;
import model.Poll;
import model.Question;
import model.Vote;
import service.AccountService;
import service.IoTService;
import service.QuestionService;

public class Mapper {

  AccountService accountService;
  QuestionService questionService;
  IoTService deviceService;
  
  public Mapper(AccountService accountService, QuestionService questionService, IoTService deviceService) {
    this.accountService = accountService;
    this.questionService = questionService;
    this.deviceService = deviceService;
  }
  
  public Poll mapPollClientToPoll(PollClient pollClient) {
    Account owner = accountService.getAccount(pollClient.getOwner());
    return new Poll(pollClient.getCode(), pollClient.getTitle(), pollClient.getTimeToStop(), pollClient.isPrivatePoll(), owner);
  }
  
  public Vote mapVoteClientToVote(VoteClient voteClient) {
    
    Question question = questionService.getQuestion(voteClient.getQuestionId());
    
    if(voteClient.getVoter() != null) {
      Account voter = accountService.getAccount(voteClient.getVoter());
      return new Vote(voteClient.getGreen(), voteClient.getRed(), question, voter);
    }
    else {
      IoTDevice device = deviceService.getDevice(voteClient.getDevice());
      return new Vote(voteClient.getGreen(), voteClient.getRed(), question, device);
    }
  }
}
