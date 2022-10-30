package controller;

import static spark.Spark.get;
import static spark.Spark.post;

import com.google.gson.Gson;

import exception.NotAuthorizedVoterException;
import exception.PrivatePollNotAuthenticatedException;
import exception.VoteOnClosedPollException;
import exception.VoteOnOtherAccountException;
import mapper.VoteMapper;
import model.Vote;
import modelweb.WebVote;
import security.AccessControl;
import security.InputValidator;
import service.VoteService;

public class VoteController {

  Gson gson = new Gson();
  InputValidator inputValidator = new InputValidator();

  public VoteController(VoteService voteService, VoteMapper voteMapper, AccessControl accessControl) {

    post("/vote", (req, res) -> {
      WebVote webVote = gson.fromJson(req.body(), WebVote.class);

      if (!inputValidator.isValidWebVote(webVote)) {
        res.status(400);
        return "Bad request";
      }

      Vote vote;

      try {
        vote = voteMapper.mapWebVoteToVote(webVote);
      } catch (Exception e) {
        res.status(400);
        return "Bad request";
      }

      try {
        boolean success = voteService.addNewVote(vote, accessControl);

        if (success) {
          res.status(200);
          return "Success";
        } else {
          res.status(500);
          return "Error";
        }
      } catch (VoteOnClosedPollException vcpe) {
        res.status(400);
        return "Cant vote on closed poll";
      } catch (PrivatePollNotAuthenticatedException pnae) {
        res.status(401);
        return "Unauthorized to vote on private poll";
      } catch (VoteOnOtherAccountException voae) {
        res.status(403);
        return "Can only vote with your own authorized account";
      } catch (NotAuthorizedVoterException e) {
        res.status(400);
        return "Invalid or unauthorized voter provided";
      }
    });

    get("/vote/:id", (req, res) -> {
      String id = req.params("id");
      Vote vote = voteService.getVoteFromString(id);

      if (vote == null) {
        res.status(404);
        return "Vote does not exist";
      }
      if (!accessControl.accessToVote(vote)) {
        res.status(401);
        return "Dont have access to given vote";
      }

      return gson.toJson(voteMapper.mapVoteToWebVote(vote));
    });
  }
}
