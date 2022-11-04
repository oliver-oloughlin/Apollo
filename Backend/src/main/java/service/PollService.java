package service;

import java.util.List;

import javax.persistence.EntityExistsException;

import dao.PollDAO;
import dweet.DweetSender;
import messaging_system.MessageSender;
import model.Poll;

public class PollService {

  PollDAO dao;
  MessageSender messageSender;
  DweetSender dweetSender;

  public PollService(PollDAO dao) {
    this.dao = dao;
    this.messageSender = new MessageSender();
    this.dweetSender = new DweetSender();
  }

  public boolean addNewPoll(Poll poll) throws EntityExistsException {

    if (dao.getPoll(poll.getCode()) != null) {
      throw new EntityExistsException();
    }

    dweetSender.Send(poll, true);

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

    Poll oldPoll = dao.getPoll(poll.getCode());

    if (oldPoll == null) {
      return null;
    }

    poll.setQuestions(oldPoll.getQuestions());
    poll.setOwner(oldPoll.getOwner());

    if (!oldPoll.isClosed() && poll.isClosed()) {
      closePoll(poll.getCode());
    }

    return dao.updatePoll(poll);
  }

  public Poll deletePoll(Poll poll) {
    boolean success = dao.deletePoll(poll);
    return success ? poll : null;
  }

  public Poll closePoll(long code) {

    Poll poll = dao.getPoll(code);

    if (poll == null) {
      return null;
    }

    messageSender.sendPoll(poll);
    dweetSender.Send(poll, false);

    poll.setClosed(true);
    System.out.println("Closed poll: " + poll.getTitle());
    return dao.updatePoll(poll);
  }
}
