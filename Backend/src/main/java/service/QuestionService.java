package service;

import dao.QuestionDAO;
import model.Question;
import utils.VoteCount;

public class QuestionService {

  QuestionDAO dao;

  public QuestionService(QuestionDAO dao) {
    this.dao = dao;
  }

  public boolean addNewQuestion(Question question) {
    return dao.saveQuestion(question);
  }

  public Question getQuestion(long id) {
    return dao.getQuestion(id);
  }

  public Question getQuestionFromString(String idString) {
    try {
      long id = Long.parseLong(idString);
      Question question = dao.getQuestion(id);
      return question;
    } catch (NumberFormatException e) {
      return null;
    }
  }

  public VoteCount getVoteCount(Question question) {
    int green = question.getVotes().stream().mapToInt(vote -> vote.getGreen()).sum();
    int red = question.getVotes().stream().mapToInt(vote -> vote.getRed()).sum();

    return new VoteCount(green, red);
  }

  public Question updateQuestion(Question question) {

    Question oldQuestion = dao.getQuestion(question.getId());

    if (oldQuestion == null) {
      return null;
    }

    question.setVotes(oldQuestion.getVotes());

    return dao.updateQuestion(question);
  }

  public Question deleteQuestion(Question question) {
    if (question == null) {
      return null;
    }
    boolean success = dao.deleteQuestion(question);
    return success ? question : null;
  }
}
