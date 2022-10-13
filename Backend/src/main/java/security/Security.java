package security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

public class Security {

  Subject currentUser;
  
  public Security(Subject currentUser) {
    this.currentUser = currentUser;
  }
  
  public String login(String email, String password) {
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
  
  public String logout() {
    if(currentUser.isAuthenticated()) {
      currentUser.logout();
      return "OK";
    }
    return "Not logged in";
  }
}
