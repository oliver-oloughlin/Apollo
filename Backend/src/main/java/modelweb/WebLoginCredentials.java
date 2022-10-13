package modelweb;

public class WebLoginCredentials {

  private String email;
  private String password;
  
  public WebLoginCredentials(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}
