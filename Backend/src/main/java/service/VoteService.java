package service;

import dao.VoteDAO;
import model.Vote;

public class VoteService {

  VoteDAO dao;

  public VoteService(VoteDAO dao) {
    this.dao = dao;
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

  public boolean addNewVote(Vote vote) {

    return dao.saveVote(vote);
  }
}
