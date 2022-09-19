package dao;

import model.Question;

public interface QuestionDAO {

	/**
	 * Saves a question in the database
	 * @param question: The question to be saved
	 * @return True if the question was saved successfully and false otherwise
	 */
	public boolean saveQuestion(Question question);
	
	
	/**
	 * Deletes a question from db
	 * @param question: The question to be deleted
	 * @return True if deletion was successful and false otherwise
	 */
	public boolean deleteQuestion(Question question);
}
