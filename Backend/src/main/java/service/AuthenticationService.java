package service;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;

import model.Account;
import security.AccessControl;
import security.WebLoginCredentials;
import utils.PasswordHasher;

public class AuthenticationService {

    AccountService accountService;
    PasswordHasher hasher;
    
    public AuthenticationService(AccountService accountService) {
        this.accountService = accountService;
        this.hasher = new PasswordHasher();
    }
    
    public void login(WebLoginCredentials credentials, AccessControl accessControl) 
        throws UnknownAccountException, IncorrectCredentialsException, LockedAccountException, AuthenticationException {
      
        Account account = accountService.getAccount(credentials.getEmail());
        if(account != null) {
            String hashedPassword = hasher.hashPassword(account.getSalt(), credentials.getPassword());
            accessControl.login(credentials.getEmail(), hashedPassword);
        }
    }
    
    public void logout(AccessControl accessControl) {
        accessControl.logout();
    }
}
