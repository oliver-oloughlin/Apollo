package controller;

import static spark.Spark.post;

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
        return gson.toJson(mapper.mapAccountToWebAccount(account));
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
      String resultString = String.format("\"%s\" is logged out", accessControl.getCurrentUserEmail());
      authenticationService.logout(accessControl);
      return resultString;
    });
  }
}
