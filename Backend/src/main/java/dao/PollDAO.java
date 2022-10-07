package dao;

import java.util.List;

import model.Poll;

public interface PollDAO {

	/**
	 * Creates a new poll in the db
	 * @param poll: The poll that is to be saved in the db
	 * @return True if poll was created was and false if poll already existed in the db
	 */
    public boolean savePoll(Poll poll);

    /**
     * Fetches a poll from the db
     * @param id: The id of the poll that is to be fetched
     * @return The poll with given id
     */
    public Poll getPoll(long code);

    
    /**
     * Updates a poll
     * @param poll: The poll-object with the updated information
     * @return The managed updated poll or NULL if update failed
     */
    public Poll updatePoll(Poll poll);
    
    /**
     * Deletes a poll from the db
     * @param poll: The poll that is to be deleted
     * @return True if the poll was deleted and false otherwise
     */
    public boolean deletePoll(Poll poll);
    
    /**
     * Returns all polls in the db
     * @return: A list with all of the polls
     */
    public List<Poll> getAllPolls();
}
