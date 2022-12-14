package controller;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import com.google.gson.Gson;

import mapper.QuestionMapper;
import model.Question;
import modelweb.WebQuestion;
import security.AccessControl;
import security.InputValidator;
import service.QuestionService;
import utils.VoteCount;

public class QuestionController {

  Gson gson = new Gson();
  InputValidator inputValidator = new InputValidator();

  public QuestionController(QuestionService questionService, QuestionMapper questionMapper,
      AccessControl accessControl) {

    post("/question", (req, res) -> {
      WebQuestion webQuestion = gson.fromJson(req.body(), WebQuestion.class);

      if (!inputValidator.isValidWebQuestion(webQuestion)) {
        res.status(400);
        return "Bad request";
      }

      Question question;

      try {
        question = questionMapper.mapWebQuestionToQuestion(webQuestion);
      } catch (Exception e) {
        res.status(400);
        return "Bad request";
      }

      if (!accessControl.accessToQuestion(question)) {
        res.status(401);
        return "Unauthorized";
      }

      boolean success = questionService.addNewQuestion(question);

      if (success) {
        res.status(200);
        return "Success";
      } else {
        res.status(500);
        return "Error";
      }

    });

    get("/question/:id", (req, res) -> {
      String idString = req.params("id");
      Question question = questionService.getQuestionFromString(idString);

      if (question == null) {
        res.status(404);
        return "Question does not exist";
      }

      return gson.toJson(questionMapper.mapQuestionToWebQuestion(question));
    });

    get("/question-score/:id", (req, res) -> {
      String idString = req.params("id");
      Question question = questionService.getQuestionFromString(idString);

      if (question == null) {
        res.status(404);
        return "Question does not exist";
      }

      VoteCount voteCount = questionService.getVoteCount(question);

      return gson.toJson(voteCount);
    });

    put("/question", (req, res) -> {
      WebQuestion webQuestion = gson.fromJson(req.body(), WebQuestion.class);

      if (!inputValidator.isValidWebQuestion(webQuestion)) {
        res.status(400);
        return "Bad request";
      }

      Question question;

      try {
        question = questionMapper.mapWebQuestionToQuestion(webQuestion);
      } catch (Exception e) {
        res.status(400);
        return "Bad request";
      }

      if (accessControl.accessToQuestion(questionService.getQuestion(question.getId()))) {

        Question newQuestion = questionService.updateQuestion(question);

        if (newQuestion == null) {
          res.status(404);
          return "Question does not exist";
        }
        return gson.toJson(questionMapper.mapQuestionToWebQuestion(newQuestion));
      }
      res.status(401);
      return "Dont have access to given question";
    });

    delete("/question/:id", (req, res) -> {
      String id = req.params("id");
      Question question = questionService.getQuestionFromString(id);

      if (question == null) {
        res.status(404);
        return "Question does not exist";
      }
      if (accessControl.accessToQuestion(question)) {
        boolean success = questionService.deleteQuestion(question);
        res.status(success ? 200 : 500);
        return success ? "Success" : "Error";
      }
      res.status(401);
      return "Dont have access to given question";
    });

  }
}
