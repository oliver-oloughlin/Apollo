package controller;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;

import com.google.gson.Gson;

import mapper.PollMapper;
import model.Poll;
import modelweb.WebPoll;
import security.AccessControl;
import security.InputValidator;
import service.PollService;

public class PollController {

  Gson gson = new Gson();
  InputValidator inputValidator = new InputValidator();

  public PollController(PollService pollService, PollMapper pollMapper, AccessControl accessControl) {

    post("/poll", (req, res) -> {
      WebPoll webPoll = gson.fromJson(req.body(), WebPoll.class);

      if (!inputValidator.isValidWebPoll(webPoll)) {
        res.status(400);
        return "Bad request";
      }

      Poll poll;
      try {
        poll = pollMapper.mapWebPollToPoll(webPoll);
      } catch (Exception e) {
        res.status(400);
        return "Bad request";
      }

      if (!accessControl.accessToPoll(poll)) {
        res.status(401);
        return "Not authorized to add poll to account: " + webPoll.getOwnerEmail();
      }

      try {
        boolean success = pollService.addNewPoll(poll);

        if (success) {
          res.status(200);
          return "Success";
        } else {
          res.status(500);
          return "Error";
        }

      } catch (EntityExistsException ee) {
        res.status(400);
        return "Poll already exists";
      }
    });

    get("/poll/:code", (req, res) -> {
      String code = req.params("code");
      Poll poll = pollService.getPollFromString(code);

      if (poll == null) {
        res.status(404);
        return "Poll does not exist";
      }

      return gson.toJson(pollMapper.mapPollToWebPoll(poll));
    });

    get("/poll", (req, res) -> {
      List<Poll> polls = pollService.getAllPolls(accessControl);
      List<WebPoll> webPolls = polls.stream().map(poll -> pollMapper.mapPollToWebPoll(poll))
          .collect(Collectors.toList());
      return gson.toJson(webPolls);
    });

    put("/poll", (req, res) -> {
      WebPoll webPoll = gson.fromJson(req.body(), WebPoll.class);

      if (!inputValidator.isValidWebPoll(webPoll)) {
        res.status(400);
        return "Bad request";
      }

      Poll poll;

      try {
        poll = pollMapper.mapWebPollToPoll(webPoll);
      } catch (Exception e) {
        res.status(400);
        return "Bad request";
      }

      if (poll == null) {
        res.status(404);
        return "Poll does not exist";
      }

      if (accessControl.accessToPoll(poll)) {
        return gson.toJson(pollMapper.mapPollToWebPoll(pollService.updatePoll(poll)));
      }
      res.status(401);
      return "Dont have access to given poll";
    });

    delete("/poll/:code", (req, res) -> {
      String code = req.params("code");
      Poll poll = pollService.getPollFromString(code);

      if (poll == null) {
        res.status(404);
        return "Poll does not exist";
      }

      if (accessControl.accessToPoll(poll)) {
        return gson.toJson(pollMapper.mapPollToWebPoll(pollService.deletePoll(poll)));
      }
      res.status(401);
      return "Dont have access to given poll";
    });
  }
}
