package com.apollo.backend;

import static spark.Spark.*;

import com.google.gson.Gson;

import model.Account;
import service.AccountService;

public class BackendApplication {

	static Gson gson = new Gson();
	static AccountService accountService = new AccountService();
	
	public static void main(String[] args) {

		if (args.length > 0) {
            port(Integer.parseInt(args[0]));
        } else {
            port(8080);
        }
		
		after((req, res) -> res.type("application/json"));
		
		get("/account/:email", (req, res) -> {
			String email = req.params("email");
			return gson.toJson(accountService.getAccountWithPassword(email, "pass123")); //How to pass passwords without using as parameter?
		});
		
		post("/account", (req, res) -> {
        	Account account = gson.fromJson(req.body(), Account.class);
        	return gson.toJson(accountService.addNewAccount(account));
        });
		
		put("/account", (req, res) -> {
			Account account = gson.fromJson(req.body(), Account.class); //Should have id param?
			return gson.toJson(accountService.updateAccount(account));
		});
		
		delete("/account/:email", (req, res) -> {
			String email = req.params("email");
			return gson.toJson(accountService.deleteAccount(email));
		});
	}
}

