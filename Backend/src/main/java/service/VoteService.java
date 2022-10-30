package service;

import dao.VoteDAO;
import exception.NotAuthorizedVoterException;
import exception.PrivatePollNotAuthenticatedException;
import exception.VoteOnClosedPollException;
import exception.VoteOnOtherAccountException;
import model.Account;
import model.Poll;
import model.Question;
import model.Vote;
import security.AccessControl;

public class VoteService {

  VoteDAO dao;
  PollService pollService;

  public VoteService(VoteDAO dao, PollService pollService) {
    this.dao = dao;
    this.pollService = pollService;
  }

  public Vote getVote(long id) {
    return dao.getVote(id);
  }

  public Vote getVoteFromString(String idString) {
    try {
      long id = Long.parseLong(idString);
      return dao.getVote(id);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  public boolean addNewVote(Vote vote, AccessControl accessControl)
      throws PrivatePollNotAuthenticatedException, VoteOnOtherAccountException,
      VoteOnClosedPollException, NotAuthorizedVoterException {

    Question question = vote.getQuestion();
    if (question == null) {
      return false;
    }
    Poll poll = pollService.getPoll(question.getPoll().getCode());

    if (poll.isClosed()) {
      throw new VoteOnClosedPollException("Can not vote on a closed poll");
    }

    Account voter = vote.getVoter();

    if (poll.isPrivatePoll() && (!accessControl.userIsAuthenticated())) {
      throw new PrivatePollNotAuthenticatedException("Unauthorized to vote on private poll");
    }

    if (poll.isPrivatePoll() && (voter == null || !accessControl.accessToAccount(voter))) {
      throw new NotAuthorizedVoterException("Invalid or unauthorized voter provided");
    }

    if (voter != null && !accessControl.accessToAccount(voter)) {
      throw new VoteOnOtherAccountException("Cant vote on behalf of " + voter.getEmail());
    }

    return dao.saveVote(vote);
  }
}
