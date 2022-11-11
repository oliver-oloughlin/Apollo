package service;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;

import model.Account;
import security.AccessControl;
import security.PasswordHasher;
import security.WebLoginCredentials;

public class AuthenticationService {

  AccountService accountService;
  PasswordHasher hasher;

  public AuthenticationService(AccountService accountService) {
    this.accountService = accountService;
    this.hasher = new PasswordHasher();
  }

  public Account login(WebLoginCredentials credentials, AccessControl accessControl)
      throws UnknownAccountException, IncorrectCredentialsException, LockedAccountException, AuthenticationException {

    if (credentials == null) {
      throw new AuthenticationException();
    }

    Account account = accountService.getAccount(credentials.getEmail());

    if (account == null) {
      throw new UnknownAccountException();
    }

    String hashedPassword = hasher.hashPassword(account.getSalt(), credentials.getPassword());
    accessControl.login(credentials.getEmail(), hashedPassword);

    return account;
  }

  public void logout(AccessControl accessControl) {
    accessControl.logout();
  }
}
