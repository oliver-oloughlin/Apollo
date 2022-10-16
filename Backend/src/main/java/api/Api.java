package api;

import static spark.Spark.*;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.text.IniRealm;

import java.util.List;

import com.google.gson.Gson;

import dao.AccountDAOImpl;
import dao.IoTDeviceDAOImpl;
import dao.PollDAOImpl;
import dao.QuestionDAOImpl;
import dao.VoteDAOImpl;
import mapper.AccountMapper;
import mapper.DeviceMapper;
import mapper.PollMapper;
import mapper.QuestionMapper;
import mapper.VoteMapper;
import model.Account;
import model.IoTDevice;
import model.Poll;
import model.Question;
import model.Vote;
import modelweb.WebAccount;
import modelweb.WebDevice;
import modelweb.WebPoll;
import modelweb.WebQuestion;
import modelweb.WebVote;
import security.AccessControl;
import security.WebLoginCredentials;
import service.AccountService;
import service.IoTService;
import service.PollService;
import service.QuestionService;
import service.VoteService;

public class Api {

    //Services
	static AccountService accountService = new AccountService(new AccountDAOImpl());
	static IoTService deviceService = new IoTService(new IoTDeviceDAOImpl());
	static PollService pollService = new PollService(new PollDAOImpl());
	static QuestionService questionService = new QuestionService(new QuestionDAOImpl());
	static VoteService voteService = new VoteService(new VoteDAOImpl());
	
	//Utilities
	static Gson gson = new Gson();
	
	//Mappers
	static VoteMapper voteMapper = new VoteMapper(accountService, questionService, deviceService);
	static PollMapper pollMapper = new PollMapper(accountService, questionService);
	static QuestionMapper questionMapper = new QuestionMapper(deviceService, voteService, pollService);
	static AccountMapper accountMapper = new AccountMapper(pollService, voteService);
	static DeviceMapper deviceMapper = new DeviceMapper(voteService, questionService);
	
	
	public static void main(String[] args) {

	    IniRealm iniRealm = new IniRealm("classpath:shiro.ini");
	    SecurityManager securityManager = new DefaultSecurityManager(iniRealm);

	    SecurityUtils.setSecurityManager(securityManager);
	    AccessControl accessControl = new AccessControl(SecurityUtils.getSubject());
	    
		if (args.length > 0) {
            port(Integer.parseInt(args[0]));
        } else {
            port(8080);
        }
		
		after((req, res) -> res.type("application/json"));
		
		//Authentication
		post("/login", (req, res) -> {
		    WebLoginCredentials credentials = gson.fromJson(req.body(), WebLoginCredentials.class);
		    return accessControl.login(credentials.getEmail(), credentials.getPassword());
		});
		
		post("/logout", (req, res) -> accessControl.logout());
		
		//Account
		post("/account", (req, res) -> {
        	WebAccount webAccount = gson.fromJson(req.body(), WebAccount.class);
        	Account account = accountMapper.mapWebAccountToAccount(webAccount);
        	return gson.toJson(accountMapper.mapAccountToWebAccount(accountService.addNewAccount(account)));
        });
		
		get("/account/:email", (req, res) -> {
			String email = req.params("email");
			Account account = accountService.getAccount(email);
			if(account == null) {
			  res.status(404);
			  return "Account does not exist";
			}
			
			if(accessControl.accessToAccount(account)) {
			    return  gson.toJson(accountMapper.mapAccountToWebAccount(account));
			}
			res.status(401);
			return "Dont have access to given account";
		});
		
		put("/account", (req, res) -> {
			WebAccount webAccount = gson.fromJson(req.body(), WebAccount.class);
			Account account = accountMapper.mapWebAccountToAccount(webAccount);
			if(accessControl.accessToAccount(account)) {
			  return gson.toJson(accountMapper.mapAccountToWebAccount(accountService.updateAccount(account)));
            }
			res.status(401);
			return "Dont have access to given account";
		});
		
		delete("/account/:email", (req, res) -> {
			String email = req.params("email");
			Account account = accountService.getAccount(email);
            if(accessControl.accessToAccount(account)) {
              return gson.toJson(accountMapper.mapAccountToWebAccount(accountService.deleteAccount(account)));
            }
            res.status(401);
            return "Dont have access to given account";
		});
		
		//Poll
		post("/poll", (req, res) -> {
        	WebPoll webPoll = gson.fromJson(req.body(), WebPoll.class);
        	Poll poll = pollMapper.mapWebPollToPoll(webPoll);
        	if(accessControl.accessToPoll(poll)) {
        	  return gson.toJson(pollMapper.mapPollToWebPoll(pollService.addNewPoll(poll)));
        	}
        	res.status(401);
        	return "Dont have access to owning account";
        });
		
		get("/poll/:code", (req, res) -> {
			String code = req.params("code");
			Poll poll = pollService.getPollFromString(code);
			if(poll == null) {
              res.status(404);
              return "Poll does not exist";
            }
			return gson.toJson(pollMapper.mapPollToWebPoll(poll));
		});
		
		get("/poll", (req, res) -> {
		    List<Poll> polls = pollService.getAllPolls();
		    List<WebPoll> webPolls = polls.stream().map(poll -> pollMapper.mapPollToWebPoll(poll)).toList();
			return gson.toJson(webPolls);
		});
		
		put("/poll", (req, res) -> {
		    WebPoll webPoll = gson.fromJson(req.body(), WebPoll.class);
		    Poll poll = pollMapper.mapWebPollToPoll(webPoll);
		    if(accessControl.accessToPoll(poll)) {
		      return gson.toJson(pollMapper.mapPollToWebPoll(pollService.updatePoll(poll)));
		    }
		    res.status(401);
		    return "Dont have access to given poll";
		});
		
		delete("/poll/:code", (req, res) -> {
			String code = req.params("code");
			Poll poll = pollService.getPollFromString(code);
			if(accessControl.accessToPoll(poll)) {
			  return gson.toJson(pollMapper.mapPollToWebPoll(pollService.deletePoll(poll)));
			}
			res.status(401);
			return "Dont have access to given poll";
		});
		
		//Question
		post("/question", (req, res) -> {
        	WebQuestion webQuestion = gson.fromJson(req.body(), WebQuestion.class);
        	Question question = questionMapper.mapWebQuestionToQuestion(webQuestion);
        	if(accessControl.accessToQuestion(question)) {
        	  return gson.toJson(questionMapper.mapQuestionToWebQuestion(questionService.addNewQuestion(question)));
        	}
        	res.status(401);
        	return "Dont have access to corresponding poll";
        });
		
		get("/question/:id", (req, res) -> {
		    String idString = req.params("id");
		    Question question = questionService.getQuestionFromString(idString);
		    if(question == null) {
              res.status(404);
              return "Question does not exist";
            }
		    return gson.toJson(questionMapper.mapQuestionToWebQuestion(question));
		});
		
		put("/question", (req, res) -> {
		    WebQuestion webQuestion = gson.fromJson(req.body(), WebQuestion.class);
		    Question question = questionMapper.mapWebQuestionToQuestion(webQuestion);
		    if(accessControl.accessToQuestion(question)) {
		      long id = webQuestion.getId(); //??
		      return gson.toJson(questionMapper.mapQuestionToWebQuestion(questionService.updateQuestion(question, id)));
		    }
		    res.status(401);
		    return "Dont have access to given question";
		});
		
		delete("/question/:id", (req, res) -> {
			String id = req.params("id");
			Question question = questionService.getQuestionFromString(id);
			if(accessControl.accessToQuestion(question)) {
			  return gson.toJson(questionMapper.mapQuestionToWebQuestion(questionService.deleteQuestion(question)));
			}
			res.status(401);
			return "Dont have access to given question";
		});
		
		//IoTDevice
        post("/device", (req, res) -> {
            WebDevice webDevice = gson.fromJson(req.body(), WebDevice.class);
            IoTDevice device = deviceMapper.mapWebDeviceToDevice(webDevice);
            return gson.toJson(deviceMapper.mapDeviceToWebDevice(deviceService.addNewDevice(device)));
        });
        
        get("/device/:token", (req, res) -> {
            String token = req.params("token");
            IoTDevice device = deviceService.getDeviceFromString(token);
            if(device == null) {
              res.status(404);
              return "Device does not exist";
            }
            if(accessControl.accessToDevice(device)) {
              return gson.toJson(deviceMapper.mapDeviceToWebDevice(device));
            }
            res.status(401);
            return "Dont have access to given device";
        });
        
        put("/device", (req, res) -> {
            WebDevice webDevice = gson.fromJson(req.body(), WebDevice.class);
            IoTDevice device = deviceMapper.mapWebDeviceToDevice(webDevice);
            if(accessControl.accessToDevice(device)) {
              return gson.toJson(deviceMapper.mapDeviceToWebDevice(deviceService.updateDevice(device)));
            }
            res.status(401);
            return "Dont have access to given device";
        });
        
        delete("/device/:token", (req, res) -> {
            String token = req.params("token");
            IoTDevice device = deviceService.getDeviceFromString(token);
            if(accessControl.accessToDevice(device)) {
              return gson.toJson(deviceMapper.mapDeviceToWebDevice(deviceService.deleteDevice(device)));
            }
            res.status(401);
            return "Dont have access to given device";
        });
		
		//Vote
		post("/vote", (req, res) -> {
        	WebVote webVote = gson.fromJson(req.body(), WebVote.class);
        	Vote vote = voteMapper.mapWebVoteToVote(webVote);
        	return gson.toJson(voteMapper.mapVoteToWebVote(voteService.addNewVote(vote)));
        });
	}
}

