package controller;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import javax.persistence.EntityExistsException;

import com.google.gson.Gson;

import mapper.AccountMapper;
import model.Account;
import modelweb.WebAccount;
import security.AccessControl;
import security.InputValidator;
import security.WebLoginCredentials;
import service.AccountService;

import java.util.HashSet;

public class AccountController {

  Gson gson = new Gson();
  InputValidator inputValidator = new InputValidator();

  public AccountController(AccountService accountService, AccountMapper accountMapper, AccessControl accessControl) {

    post("/account", (req, res) -> {
      WebLoginCredentials credentials = gson.fromJson(req.body(), WebLoginCredentials.class);
      Account newAccount = new Account(credentials.getEmail(), credentials.getPassword(), false, new HashSet<>(), new HashSet<>());

      try {
        boolean success = accountService.addNewAccount(newAccount);
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

      if (!inputValidator.isVaildWebAccount(webAccount)) {
        res.status(400);
        return "Bad request";
      }

      Account account;

      try {
        account = accountMapper.mapWebAccountToAccount(webAccount);
      } catch (Exception e) {
        res.status(400);
        return "Bad request";
      }
      if (account == null) {
        res.status(404);
        return "Account does not exist";
      }

      if (accessControl.accessToAccount(account)) {
        return gson.toJson(accountMapper.mapAccountToWebAccount(accountService.updateAccount(account)));
      }
      res.status(401);
      return "Dont have access to given account";
    });

    delete("/account/:email", (req, res) -> {
      String email = req.params("email");
      Account account = accountService.getAccount(email);

      if (account == null) {
        res.status(404);
        return "Account does not exist";
      }

      if (accessControl.accessToAccount(account)) {
        return gson.toJson(accountMapper.mapAccountToWebAccount(accountService.deleteAccount(account)));
      }
      res.status(401);
      return "Dont have access to given account";
    });

  }
}
