package mapper;

import java.util.HashSet;
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

  public Account mapWebAccountToAccount(WebAccount webAccount) throws Exception {

    Set<Poll> polls = new HashSet<Poll>();
    Set<Vote> votes = new HashSet<Vote>();

    return new Account(webAccount.getEmail(), webAccount.getPassword(), polls, votes);
  }

  public WebAccount mapAccountToWebAccount(Account account) {

    if (account == null) {
      return null;
    }

    Set<Long> pollCodes = account.getPolls().stream()
        .map(poll -> poll.getCode())
        .collect(Collectors.toSet());

    Set<Long> voteIds = account.getVotes().stream()
        .map(vote -> vote.getId())
        .collect(Collectors.toSet());

    return new WebAccount(account.getEmail(), account.getPassword(), account.isAdmin(), pollCodes, voteIds);
  }
}
