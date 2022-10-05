package utils;

import client.PollClient;
import model.Account;
import model.Poll;
import service.AccountService;

public class Mapper {

  AccountService accountService;
  
  public Mapper(AccountService accountService) {
    this.accountService = accountService;
  }
  
  public Poll mapPollClientToPoll(PollClient pc) {
    Account owner = accountService.getAccount(pc.getOwner());
    return new Poll(pc.getCode(), pc.getTitle(), pc.getTimeToStop(), pc.isPrivatePoll(), owner);
  }
}
