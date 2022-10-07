package modelweb;

import java.util.Set;

import model.AccountType;

public class WebAccount {

  private String email;
  private String password;
  private boolean isAdmin;
  private AccountType accountType;
  private Set<Long> pollCodes;
  private Set<Long> voteIds;
  
  public WebAccount(String email, String password, boolean isAdmin, AccountType accountType, Set<Long> pollCodes, Set<Long> voteIds) {
    this.email = email;
    this.password = password;
    this.isAdmin = isAdmin;
    this.accountType = accountType;
    this.pollCodes = pollCodes;
    this.voteIds = voteIds;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public boolean isAdmin() {
    return isAdmin;
  }

  public AccountType getAccountType() {
    return accountType;
  }

  public Set<Long> getPollCodes() {
    return pollCodes;
  }

  public Set<Long> getVoteIds() {
    return voteIds;
  }
}
