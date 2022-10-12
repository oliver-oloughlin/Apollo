package service;

import java.util.List;

import dao.PollDAO;
import model.Poll;

public class PollService {

	PollDAO dao;
	
	public PollService(PollDAO dao) {
		this.dao = dao;
	}
	
	public Poll addNewPoll(Poll poll) {
		boolean success = dao.savePoll(poll);
		return success ? dao.getPoll(poll.getCode()) : null;
	}
	
	public Poll getPollFromString(String codeString) {
		try {
			long code = Long.parseLong(codeString);
			return dao.getPoll(code);
		} catch(NumberFormatException e) {
			return null;
		}
	}
	
	public Poll getPoll(long code) {
	  return dao.getPoll(code);
	}
	
	public List<Poll> getAllPolls(){
		return dao.getAllPolls();
	}
	
	public Poll updatePoll(Poll poll) {
		return dao.updatePoll(poll);
	}
	
	public Poll deletePoll(String codeString) {
		try {
			long code = Long.parseLong(codeString);
			Poll poll = dao.getPoll(code);
			boolean success = dao.deletePoll(poll);
			return success ? poll : null;
		} catch(NumberFormatException e) {
			return null;
		}
	}
	
}
