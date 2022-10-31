package security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import model.Account;
import model.Poll;
import model.Question;
import model.Vote;

public class AccessControl {

  Subject currentUser;

  public AccessControl(Subject currentUser) {
    this.currentUser = currentUser;
  }

  public boolean userIsAuthenticated() {
    return currentUser.isAuthenticated();
  }

  public String getCurrentUserEmail() {
    return (String) currentUser.getPrincipals().getPrimaryPrincipal();
  }

  public void login(String email, String password)
      throws UnknownAccountException, IncorrectCredentialsException, LockedAccountException, AuthenticationException {
    UsernamePasswordToken token = new UsernamePasswordToken(email, password);
    currentUser.login(token);
  }

  public void logout() {
    if (currentUser.isAuthenticated()) {
      currentUser.logout();
    }
  }

  private boolean currentUserOwnsAccount(Account account) {

    if (!currentUser.isAuthenticated()) {
      return false;
    }
    return account.getEmail().equals(currentUser.getPrincipal());
  }

  public boolean accessToAccount(Account account) {
    if (account == null) {
      return false;
    }
    return currentUser.isAuthenticated() && (currentUser.hasRole("admin") || currentUserOwnsAccount(account));
  }

  public boolean accessToPoll(Poll poll) {
    if (poll == null) {
      return false;
    }
    Account owner = poll.getOwner();
    return accessToAccount(owner);
  }

  public boolean accessToQuestion(Question question) {
    if (question == null) {
      return false;
    }

    Poll poll = question.getPoll();
    return accessToPoll(poll);
  }

  public boolean accessToVote(Vote vote) {
    if (vote == null) {
      return false;
    }

    Question question = vote.getQuestion();
    return accessToQuestion(question);
  }
}
