package mapper;

import java.util.Set;
import java.util.stream.Collectors;

import model.Account;
import model.Poll;
import model.Vote;
import modelweb.WebAccount;
import service.PollService;
import service.VoteService;

public class AccountMapper {

  PollService pollService;
  VoteService voteService;
  
  public AccountMapper(PollService pollService, VoteService voteService) {
    this.pollService = pollService;
    this.voteService = voteService;
  }
  
  public Account mapWebAccountToAccount(WebAccount webAccount) {
    Set<Poll> polls = webAccount.getPollCodes().stream()
        .map(code -> pollService.getPoll(code))
        .collect(Collectors.toSet());
    
    Set<Vote> votes = webAccount.getVoteIds().stream()
        .map(id -> voteService.getVote(id))
        .collect(Collectors.toSet());
    
    return new Account(webAccount.getEmail(), webAccount.getPassword(),
        webAccount.isAdmin(), webAccount.getAccountType(), polls, votes);
  }
  
  public WebAccount mapAccountToWebAccount(Account account) {
    Set<Long> pollCodes = account.getPolls().stream()
        .map(poll -> poll.getCode())
        .collect(Collectors.toSet());
    
    Set<Long> voteIds = account.getVotes().stream()
        .map(vote -> vote.getId())
        .collect(Collectors.toSet());
    
    return new WebAccount(account.getEmail(), account.getPassword(), account.isAdmin(),
        account.getAccountType(), pollCodes, voteIds);
  }
}
