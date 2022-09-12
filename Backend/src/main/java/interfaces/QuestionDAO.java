package interfaces;

import database.Question;

public interface QuestionDAO {

	/**
	 * Saves a question in the database
	 * @param question: The question to be saved
	 * @return True if the question was saved successfully and false otherwise
	 */
	public boolean saveQuestion(Question question);
}
