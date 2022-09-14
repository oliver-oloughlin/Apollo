package dao;

import utils.VoteCount;

import java.util.List;

import model.Poll;

public interface PollDAO {

    public boolean savePoll(Poll poll);

    public boolean updatePoll(Poll poll);

    public Poll getPollById(int id);

    public List<Poll> getAllPolls();

    public VoteCount countVotesByPollId(int id);

}
