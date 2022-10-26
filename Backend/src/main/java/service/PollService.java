package service;

import java.util.List;

import javax.persistence.EntityExistsException;

import dao.PollDAO;
import model.Poll;

public class PollService {

  PollDAO dao;

  public PollService(PollDAO dao) {
    this.dao = dao;
  }

  public boolean addNewPoll(Poll poll) throws EntityExistsException {

    if (dao.getPoll(poll.getCode()) != null) {
      throw new EntityExistsException();
    }

    return dao.savePoll(poll);
  }

  public Poll getPollFromString(String codeString) {
    try {
      long code = Long.parseLong(codeString);
      return dao.getPoll(code);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  public Poll getPoll(long code) {
    return dao.getPoll(code);
  }

  public List<Poll> getAllPolls() {
    return dao.getAllPolls();
  }

  public Poll updatePoll(Poll poll) {
    return dao.updatePoll(poll);
  }

  public Poll deletePoll(Poll poll) {
    boolean success = dao.deletePoll(poll);
    return success ? poll : null;
  }

  public Poll closePoll(Poll poll) {
    poll.setClosed(true);
    System.out.println("Closed poll: " + poll.getTitle());
    return dao.updatePoll(poll);
  }
}
