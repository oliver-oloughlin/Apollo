package service;

import java.util.List;

import javax.persistence.EntityExistsException;

import org.apache.shiro.authz.AuthorizationException;

import dao.PollDAO;
import model.Poll;
import security.AccessControl;

public class PollService {

	PollDAO dao;
	
	public PollService(PollDAO dao) {
		this.dao = dao;
	}
	
	public boolean addNewPoll(Poll poll, AccessControl accessControl)
	    throws EntityExistsException, AuthorizationException {
	  
  	    if(dao.getPoll(poll.getCode()) != null) {
          throw new EntityExistsException();
        }
	  
	    if(accessControl.accessToPoll(poll)) {
	      return dao.savePoll(poll);
	    }
	    throw new AuthorizationException();
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
	
	public Poll deletePoll(Poll poll) {
	    boolean success = dao.deletePoll(poll);
	    return success ? poll : null;
	}
}
