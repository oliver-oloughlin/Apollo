package api;

import static spark.Spark.*;

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

		if (args.length > 0) {
            port(Integer.parseInt(args[0]));
        } else {
            port(8080);
        }
		
		after((req, res) -> res.type("application/json"));
		
		//Account
		post("/account", (req, res) -> {
        	WebAccount webAccount = gson.fromJson(req.body(), WebAccount.class);
        	Account account = accountMapper.mapWebAccountToAccount(webAccount);
        	return gson.toJson(accountMapper.mapAccountToWebAccount(accountService.addNewAccount(account)));
        });
		
		get("/account/:email/:pass", (req, res) -> {
			String email = req.params("email");
			String pass = req.params("pass");
			return gson.toJson(accountMapper.mapAccountToWebAccount(accountService.getAccountWithPassword(email, pass)));
		});
		
		put("/account", (req, res) -> {
			WebAccount webAccount = gson.fromJson(req.body(), WebAccount.class);
			Account account = accountMapper.mapWebAccountToAccount(webAccount);
			return gson.toJson(accountMapper.mapAccountToWebAccount(accountService.updateAccount(account)));
		});
		
		delete("/account/:email", (req, res) -> {
			String email = req.params("email");
			return gson.toJson(accountMapper.mapAccountToWebAccount(accountService.deleteAccount(email)));
		});
		
		//IoTDevice
		post("/device", (req, res) -> {
			WebDevice webDevice = gson.fromJson(req.body(), WebDevice.class);
			IoTDevice device = deviceMapper.mapWebDeviceToDevice(webDevice);
        	return gson.toJson(deviceMapper.mapDeviceToWebDevice(deviceService.addNewDevice(device)));
        });
		
		get("/device/:token", (req, res) -> {
			String token = req.params("token");
			return gson.toJson(deviceMapper.mapDeviceToWebDevice(deviceService.getDeviceFromString(token)));
		});
		
		put("/device", (req, res) -> {
		    WebDevice webDevice = gson.fromJson(req.body(), WebDevice.class);
            IoTDevice device = deviceMapper.mapWebDeviceToDevice(webDevice);
			return gson.toJson(deviceMapper.mapDeviceToWebDevice(deviceService.updateDevice(device)));
		});
		
		delete("/device/:token", (req, res) -> {
			String token = req.params("token");
			return gson.toJson(deviceMapper.mapDeviceToWebDevice(deviceService.deleteDevice(token)));
		});
		
		//Poll
		post("/poll", (req, res) -> {
        	WebPoll webPoll = gson.fromJson(req.body(), WebPoll.class);
        	Poll poll = pollMapper.mapWebPollToPoll(webPoll);
        	return gson.toJson(pollMapper.mapPollToWebPoll(pollService.addNewPoll(poll)));
        });
		
		get("/poll/:code", (req, res) -> {
			String code = req.params("code");
			return gson.toJson(pollMapper.mapPollToWebPoll(pollService.getPollFromString(code)));
		});
		
		get("/poll", (req, res) -> {
		    List<Poll> polls = pollService.getAllPolls();
		    List<WebPoll> webPolls = polls.stream().map(poll -> pollMapper.mapPollToWebPoll(poll)).toList();
			return gson.toJson(webPolls);
		});
		
		put("/poll", (req, res) -> {
		    WebPoll webPoll = gson.fromJson(req.body(), WebPoll.class);
		    Poll poll = pollMapper.mapWebPollToPoll(webPoll);
			return gson.toJson(pollMapper.mapPollToWebPoll(pollService.updatePoll(poll)));
		});
		
		delete("/poll/:code", (req, res) -> {
			String code = req.params("code");
			return gson.toJson(pollMapper.mapPollToWebPoll(pollService.deletePoll(code)));
		});
		
		//Question
		post("/question", (req, res) -> {
        	WebQuestion webQuestion = gson.fromJson(req.body(), WebQuestion.class);
        	Question question = questionMapper.mapWebQuestionToQuestion(webQuestion);
        	return gson.toJson(questionMapper.mapQuestionToWebQuestion(questionService.addNewQuestion(question)));
        });
		
		get("/question/:id", (req, res) -> {
		    String idString = req.params("id");
		    return gson.toJson(questionMapper.mapQuestionToWebQuestion(questionService.getQuestionFromString(idString)));
		});
		
		put("/question", (req, res) -> {
		    WebQuestion webQuestion = gson.fromJson(req.body(), WebQuestion.class);
		    long id = webQuestion.getId();
		    Question question = questionMapper.mapWebQuestionToQuestion(webQuestion);
		    return gson.toJson(questionMapper.mapQuestionToWebQuestion(questionService.updateQuestion(question, id)));
		});
		
		delete("/question/:id", (req, res) -> {
			String id = req.params("id");
			return gson.toJson(questionMapper.mapQuestionToWebQuestion(questionService.deleteQuestion(id)));
		});
		
		//Vote
		post("/vote", (req, res) -> {
        	WebVote webVote = gson.fromJson(req.body(), WebVote.class);
        	Vote vote = voteMapper.mapWebVoteToVote(webVote);
        	return gson.toJson(voteMapper.mapVoteToWebVote(voteService.addNewVote(vote)));
        });
	}
}

