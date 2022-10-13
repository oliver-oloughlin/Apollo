package security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import model.*;

public class AccessControl {

  Subject currentUser;
  
  public AccessControl(Subject currentUser) {
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
  
  private boolean currentUserOwnsAccount(Account account) {
    return account.getEmail().equals(currentUser.getPrincipal());
  }
  
  public boolean accessToAccount(Account account) {
    if(account == null) {
      return false;
    }
    return currentUser.isAuthenticated() && (currentUser.hasRole("admin") || currentUserOwnsAccount(account));
  }
  
  public boolean accessToPoll(Poll poll) {
    if(poll == null) {
      return false;
    }
    Account owner = poll.getOwner();
    return accessToAccount(owner);
  }
  
  public boolean accessToQuestion(Question question) {
    if(question == null) {
      return false;
    }
    
    Poll poll = question.getPoll();
    return accessToPoll(poll);
  }
  
  public boolean accessToDevice(IoTDevice device) {
    if(device == null) {
      return false;
    }
    
    Question question = device.getQuestion();
    return accessToQuestion(question);
  }
}
