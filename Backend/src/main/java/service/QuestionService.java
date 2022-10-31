package service;

import dao.QuestionDAO;
import model.Question;

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

  public Question updateQuestion(Question question, long id) {
    Question managedQuestion = dao.getQuestion(id);
    managedQuestion.setText(question.getText());
    return managedQuestion;
  }

  public Question deleteQuestion(Question question) {
    if (question == null) {
      return null;
    }
    boolean success = dao.deleteQuestion(question);
    return success ? question : null;
  }
}
