package service;

import org.springframework.stereotype.Service;

import dao.QuestionDAO;
import model.Question;

@Service
public class QuestionService {

	QuestionDAO dao;
	
	public QuestionService(QuestionDAO dao) {
		this.dao = dao;
	}
	
	public Question addNewQuestion(Question question) {
		boolean success = dao.saveQuestion(question);
		return success ? question : null;
	}

	public Question getQuestion(long id) {
		try {
			Question question = dao.getQuestion(id);
			return question;
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public Question deleteQuestion(String idString) {
		try {
			long id = Long.parseLong(idString);
			Question question = dao.getQuestion(id);
			if(question == null) {
			  return null;
			}
			boolean success = dao.deleteQuestion(question);
			return success ? question : null;
		} catch(NumberFormatException e) {
			return null;
		}
	}
}
