package dao;

import model.Question;

public interface QuestionDAO {

  /**
   * Saves a question in the database
   * 
   * @param question: The question to be saved
   * @return true if question was persisted and false otherwise
   */
  public boolean saveQuestion(Question question);

  /**
   * Deletes a question from the database
   * 
   * @param question: The question to be deleted
   * @return True if deletion was successful and false otherwise
   */
  public boolean deleteQuestion(Question question);

  /**
   * Gets a question from the database
   * 
   * @param id: The id of the question
   * @return the fetched question
   */
  public Question getQuestion(long id);
}
