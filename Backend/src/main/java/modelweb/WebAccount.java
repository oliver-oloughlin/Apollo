package modelweb;

import java.util.Set;

public class WebAccount {

  private String email;
  private String password;
  private boolean isAdmin;
  private Set<Long> pollCodes;
  private Set<Long> voteIds;

  public WebAccount(String email, String password, boolean isAdmin, Set<Long> pollCodes, Set<Long> voteIds) {
    this.email = email;
    this.password = password;
    this.isAdmin = isAdmin;
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

  public Set<Long> getPollCodes() {
    return pollCodes;
  }

  public Set<Long> getVoteIds() {
    return voteIds;
  }
}
