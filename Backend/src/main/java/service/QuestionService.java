package service;

import dao.QuestionDAO;
import model.Question;

public class QuestionService {

	QuestionDAO dao;
	
	public QuestionService(QuestionDAO dao) {
		this.dao = dao;
	}
	
	public Question addNewQuestion(Question question) {
		boolean success = dao.saveQuestion(question);
		return success ? question : null;
	}
	
	public Question deleteQuestion(String idString) {
		try {
			long id = Long.parseLong(idString);
			Question question = dao.getQuestion(id);
			boolean success = dao.deleteQuestion(question);
			return success ? question : null;
		} catch(NumberFormatException e) {
			return null;
		}
	}
}
