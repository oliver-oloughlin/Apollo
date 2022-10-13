package utils;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

public class Security {

  public Security() {}
  
  public String login(String email, String password, Subject currentUser) {
    UsernamePasswordToken token = new UsernamePasswordToken(email, password);
    try {
      currentUser.login(token);
      return "OK";
    } catch ( UnknownAccountException uae ) {
       return "No matching user";
    } catch ( IncorrectCredentialsException ice ) {
       return "Wrong password";
    } catch ( LockedAccountException lae ) {
       return "Can not log in to this account";
    } catch ( AuthenticationException ae ) {
       return "Error";
    }
  }
}
