package controller;

import static spark.Spark.post;

import modelweb.WebAccount;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;

import com.google.gson.Gson;

import mapper.AccountMapper;
import model.Account;
import security.AccessControl;
import security.WebLoginCredentials;
import service.AccountService;
import service.AuthenticationService;

public class AuthenticationController {

  Gson gson = new Gson();

  public AuthenticationController(AuthenticationService authenticationService, AccountService accountService,
      AccountMapper mapper, AccessControl accessControl) {

    post("/login", (req, res) -> {
      WebLoginCredentials credentials = gson.fromJson(req.body(), WebLoginCredentials.class);
      try {
        Account account = authenticationService.login(credentials, accessControl);
        WebAccount webAccount = mapper.mapAccountToWebAccount(account);
        res.status(201);
        return gson.toJson(webAccount);
      } catch (UnknownAccountException uae) {
        res.status(400);
        return "Bad Request";
      } catch (IncorrectCredentialsException ice) {
        res.status(401);
        return "Unauthorized";
      } catch (LockedAccountException lae) {
        res.status(401);
        return "Unauthorized";
      } catch (AuthenticationException ae) {
        res.status(500);
        return "Internal Server Error";
      }
    });

    post("/logout", (req, res) -> {
      String resultString = String.format("\"%s\" is logged out", accessControl.getCurrentUserEmail());
      authenticationService.logout(accessControl);
      res.removeCookie("user");
      return resultString;
    });
  }
}
