package com.apollo.backend;

import static spark.Spark.*;

import com.google.gson.Gson;

import dao.AccountDAOImpl;
import dao.IoTDeviceDAOImpl;
import dao.PollDAOImpl;
import dao.QuestionDAOImpl;
import dao.VoteDAOImpl;
import model.Account;
import model.IoTDevice;
import model.Poll;
import model.Question;
import service.AccountService;
import service.IoTService;
import service.PollService;
import service.QuestionService;
import service.VoteService;
import utils.Mapper;
import web.WebDevice;
import web.WebPoll;
import web.WebVote;

public class BackendApplication {

    //Services
	static AccountService accountService = new AccountService(new AccountDAOImpl());
	static IoTService iotService = new IoTService(new IoTDeviceDAOImpl());
	static PollService pollService = new PollService(new PollDAOImpl());
	static QuestionService questionService = new QuestionService(new QuestionDAOImpl());
	static VoteService voteService = new VoteService(new VoteDAOImpl());
	
	//Utilities
	static Gson gson = new Gson();
	static Mapper mapper = new Mapper(accountService, questionService, iotService);
	
	public static void main(String[] args) {

		if (args.length > 0) {
            port(Integer.parseInt(args[0]));
        } else {
            port(8080);
        }
		
		after((req, res) -> res.type("application/json"));
		
		//Account
		post("/account", (req, res) -> {
        	Account account = gson.fromJson(req.body(), Account.class);
        	return gson.toJson(accountService.addNewAccount(account));
        });
		
		get("/account/:email/:pass", (req, res) -> {
			String email = req.params("email");
			String pass = req.params("pass");
			return gson.toJson(accountService.getAccountWithPassword(email, pass)); //How to pass passwords without using as parameter?
		});
		
		put("/account", (req, res) -> {
			Account account = gson.fromJson(req.body(), Account.class); //Should have id param?
			return gson.toJson(accountService.updateAccount(account));
		});
		
		delete("/account/:email", (req, res) -> {
			String email = req.params("email");
			return gson.toJson(accountService.deleteAccount(email));
		});
		
		//IoTDevice
		post("/device", (req, res) -> {
			WebDevice webDevice = gson.fromJson(req.body(), WebDevice.class);
			Question question = questionService.getQuestion(webDevice.getQuestionId());
        	return gson.toJson(iotService.addNewDevice(new IoTDevice(question)));
        });
		
		get("/device/:token", (req, res) -> {
			String token = req.params("token");
			return gson.toJson(iotService.getDevice(token));
		});
		
		put("/device", (req, res) -> {
			IoTDevice device = gson.fromJson(req.body(), IoTDevice.class);
			return gson.toJson(iotService.updateDevice(device));
		});
		
		delete("/device/:token", (req, res) -> {
			String token = req.params("token");
			return gson.toJson(iotService.deleteDevice(token));
		});
		
		//Poll
		post("/poll", (req, res) -> {
        	WebPoll webPoll = gson.fromJson(req.body(), WebPoll.class);
        	return gson.toJson(pollService.addNewPoll(mapper.mapWebPollToPoll(webPoll)));
        });
		
		get("/poll/:code", (req, res) -> {
			String code = req.params("code");
			return gson.toJson(pollService.getPoll(code));
		});
		
		get("/poll", (req, res) -> {
			return gson.toJson(pollService.getAllPolls());
		});
		
		put("/poll", (req, res) -> {
			Poll poll = gson.fromJson(req.body(), Poll.class);
			return gson.toJson(pollService.updatePoll(poll));
		});
		
		delete("/poll/:code", (req, res) -> {
			String code = req.params("code");
			return gson.toJson(pollService.deletePoll(code));
		});
		
		//Question
		post("/question", (req, res) -> {
        	Question question = gson.fromJson(req.body(), Question.class);
        	return gson.toJson(questionService.addNewQuestion(question));
        });
		
		delete("/question/:id", (req, res) -> {
			String id = req.params("id");
			return gson.toJson(questionService.deleteQuestion(id));
		});
		
		//Vote
		post("/vote", (req, res) -> {
        	WebVote webVote = gson.fromJson(req.body(), WebVote.class);
        	return gson.toJson(voteService.addNewVote(mapper.mapWebVoteToVote(webVote)));
        });
	}
}

