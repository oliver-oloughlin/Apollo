package api;

import static spark.Spark.after;
import static spark.Spark.port;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;

import controller.AccountController;
import controller.AuthenticationController;
import controller.PollController;
import controller.QuestionController;
import controller.VoteController;
import daemon.PollDaemon;
import dao.AccountDAOImpl;
import dao.PollDAOImpl;
import dao.QuestionDAOImpl;
import dao.VoteDAOImpl;
import mapper.AccountMapper;
import mapper.PollMapper;
import mapper.QuestionMapper;
import mapper.VoteMapper;
import security.AccessControl;
import security.ApolloRealm;
import service.AccountService;
import service.AuthenticationService;
import service.PollService;
import service.QuestionService;
import service.VoteService;

public class Api {

  // Creating all of the services
  static AccountService accountService = new AccountService(new AccountDAOImpl());
  static PollService pollService = new PollService(new PollDAOImpl());
  static QuestionService questionService = new QuestionService(new QuestionDAOImpl());
  static VoteService voteService = new VoteService(new VoteDAOImpl());
  static AuthenticationService authenticationService = new AuthenticationService(accountService);

  // Creating all of the mappers
  static AccountMapper accountMapper = new AccountMapper(pollService, voteService);
  static PollMapper pollMapper = new PollMapper(accountService, questionService);
  static QuestionMapper questionMapper = new QuestionMapper(voteService, pollService);
  static VoteMapper voteMapper = new VoteMapper(accountService, questionService);

  public static void main(String[] args) {

    // Setting up Authentication environment
    ApolloRealm apolloRealm = new ApolloRealm(accountService);
    SecurityManager securityManager = new DefaultSecurityManager(apolloRealm);
    SecurityUtils.setSecurityManager(securityManager);
    AccessControl accessControl = new AccessControl(SecurityUtils.getSubject());

    // Starting daemon
    new PollDaemon(pollService);

    if (args.length > 0) {
      port(Integer.parseInt(args[0]));
    } else {
      port(8080);
    }

    after((req, res) -> res.type("application/json"));

    new AuthenticationController(authenticationService, accessControl);
    new AccountController(accountService, accountMapper, accessControl);
    new PollController(pollService, pollMapper, accessControl);
    new QuestionController(questionService, questionMapper, accessControl);
    new VoteController(voteService, voteMapper, accessControl);
  }
}
