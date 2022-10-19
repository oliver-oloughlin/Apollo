package api;

import static spark.Spark.after;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;

import com.google.gson.Gson;

import dao.AccountDAOImpl;
import dao.PollDAOImpl;
import dao.QuestionDAOImpl;
import dao.VoteDAOImpl;
import mapper.AccountMapper;
import mapper.PollMapper;
import mapper.QuestionMapper;
import mapper.VoteMapper;
import model.Account;
import model.Poll;
import model.Question;
import model.Vote;
import modelweb.WebAccount;
import modelweb.WebPoll;
import modelweb.WebQuestion;
import modelweb.WebVote;
import security.AccessControl;
import security.ApolloRealm;
import security.WebLoginCredentials;
import service.AccountService;
import service.AuthenticationService;
import service.PollService;
import service.QuestionService;
import service.VoteService;

public class Api {

  // Services
  static AccountService accountService = new AccountService(new AccountDAOImpl());
  static PollService pollService = new PollService(new PollDAOImpl());
  static QuestionService questionService = new QuestionService(new QuestionDAOImpl());
  static VoteService voteService = new VoteService(new VoteDAOImpl());
  static AuthenticationService authenticationService = new AuthenticationService(accountService);

  // Utilities
  static Gson gson = new Gson();

  // Mappers
  static AccountMapper accountMapper = new AccountMapper(pollService, voteService);
  static PollMapper pollMapper = new PollMapper(accountService, questionService);
  static QuestionMapper questionMapper = new QuestionMapper(voteService, pollService);
  static VoteMapper voteMapper = new VoteMapper(accountService, questionService);

  public static void main(String[] args) {

    ApolloRealm apolloRealm = new ApolloRealm(accountService);
    SecurityManager securityManager = new DefaultSecurityManager(apolloRealm);

    SecurityUtils.setSecurityManager(securityManager);
    AccessControl accessControl = new AccessControl(SecurityUtils.getSubject());

    if (args.length > 0) {
      port(Integer.parseInt(args[0]));
    } else {
      port(8080);
    }

    after((req, res) -> res.type("application/json"));

    // Authentication
    post("/login", (req, res) -> {
      WebLoginCredentials credentials = gson.fromJson(req.body(), WebLoginCredentials.class);
      try {
        authenticationService.login(credentials, accessControl);
        return "Success";
      } catch (UnknownAccountException uae) {
        return "Unknown account";
      } catch (IncorrectCredentialsException ice) {
        return "Incorrect Password";
      } catch (LockedAccountException lae) {
        return "Locked account";
      } catch (AuthenticationException ae) {
        return "Error";
      }
    });

    post("/logout", (req, res) -> {
      authenticationService.logout(accessControl);
      return "Success";
    });

    // Account
    post("/account", (req, res) -> {
      WebAccount webAccount = gson.fromJson(req.body(), WebAccount.class);
      Account account = accountMapper.mapWebAccountToAccount(webAccount);
      try {
        boolean success = accountService.addNewAccount(account);
        if (success) {
          res.status(200);
          return "Success";
        } else {
          res.status(500);
          return "Error";
        }
      } catch (EntityExistsException e) {
        res.status(400);
        return "Account already exists";
      }
    });

    get("/account/:email", (req, res) -> {
      String email = req.params("email");
      Account account = accountService.getAccount(email);

      if (account == null) {
        res.status(404);
        return "Account does not exist";
      }

      if (!accessControl.accessToAccount(account)) {
        res.status(401);
        return "Dont have access to given account";
      }

      return gson.toJson(accountMapper.mapAccountToWebAccount(account));

    });

    put("/account", (req, res) -> {
      WebAccount webAccount = gson.fromJson(req.body(), WebAccount.class);
      Account account = accountService.getAccount(webAccount.getEmail());
      if (accessControl.accessToAccount(account)) {
        return gson.toJson(accountMapper.mapAccountToWebAccount(accountService.updateAccount(account)));
      }
      res.status(401);
      return "Dont have access to given account";
    });

    delete("/account/:email", (req, res) -> {
      String email = req.params("email");
      Account account = accountService.getAccount(email);
      if (accessControl.accessToAccount(account)) {
        return gson.toJson(accountMapper.mapAccountToWebAccount(accountService.deleteAccount(account)));
      }
      res.status(401);
      return "Dont have access to given account";
    });

    // Poll
    post("/poll", (req, res) -> {
      WebPoll webPoll = gson.fromJson(req.body(), WebPoll.class);
      Poll poll = pollMapper.mapWebPollToPoll(webPoll);
      try {
        boolean success = pollService.addNewPoll(poll, accessControl);
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
      } catch (AuthorizationException ae) {
        res.status(401);
        return "Unauthorized";
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
      List<Poll> polls = pollService.getAllPolls();
      List<WebPoll> webPolls = polls.stream().map(poll -> pollMapper.mapPollToWebPoll(poll))
          .collect(Collectors.toList());
      return gson.toJson(webPolls);
    });

    put("/poll", (req, res) -> {
      WebPoll webPoll = gson.fromJson(req.body(), WebPoll.class);
      Poll poll = pollService.getPoll(webPoll.getCode());
      if (accessControl.accessToPoll(poll)) {
        return gson.toJson(pollMapper.mapPollToWebPoll(pollService.updatePoll(poll)));
      }
      res.status(401);
      return "Dont have access to given poll";
    });

    delete("/poll/:code", (req, res) -> {
      String code = req.params("code");
      Poll poll = pollService.getPollFromString(code);
      if (accessControl.accessToPoll(poll)) {
        return gson.toJson(pollMapper.mapPollToWebPoll(pollService.deletePoll(poll)));
      }
      res.status(401);
      return "Dont have access to given poll";
    });

    // Question
    post("/question", (req, res) -> {
      WebQuestion webQuestion = gson.fromJson(req.body(), WebQuestion.class);
      Question question = questionMapper.mapWebQuestionToQuestion(webQuestion);
      try {
        boolean success = questionService.addNewQuestion(question, accessControl);
        if (success) {
          res.status(200);
          return "Success";
        } else {
          res.status(500);
          return "Error";
        }
      } catch (AuthorizationException ae) {
        res.status(401);
        return "Unauthorized";
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

    put("/question", (req, res) -> {
      WebQuestion webQuestion = gson.fromJson(req.body(), WebQuestion.class);
      Question question = questionService.getQuestion(webQuestion.getId());
      if (accessControl.accessToQuestion(question)) {
        long id = webQuestion.getId(); // ??
        return gson.toJson(questionMapper.mapQuestionToWebQuestion(questionService.updateQuestion(question, id)));
      }
      res.status(401);
      return "Dont have access to given question";
    });

    delete("/question/:id", (req, res) -> {
      String id = req.params("id");
      Question question = questionService.getQuestionFromString(id);
      if (accessControl.accessToQuestion(question)) {
        return gson.toJson(questionMapper.mapQuestionToWebQuestion(questionService.deleteQuestion(question)));
      }
      res.status(401);
      return "Dont have access to given question";
    });

    // Vote
    post("/vote", (req, res) -> {
      WebVote webVote = gson.fromJson(req.body(), WebVote.class);
      Vote vote = voteMapper.mapWebVoteToVote(webVote);
      boolean success = voteService.addNewVote(vote);
      if (success) {
        res.status(200);
        return "Success";
      } else {
        res.status(500);
        return "Error";
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
