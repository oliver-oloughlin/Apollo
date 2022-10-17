package security;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import model.Account;
import service.AccountService;

public class ApolloRealm extends JdbcRealm {

  private AccountService accountService;
  
  public ApolloRealm (AccountService accountService) {
    this.accountService = accountService;
  }
  
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException{
      UsernamePasswordToken uToken = (UsernamePasswordToken) token; 
      Account account = accountService.getAccount(uToken.getUsername());
      
      if(account == null) {
          throw new UnknownAccountException("Account not found");
      }
      
      String email = account.getEmail();
      String password = account.getPassword();
      String salt = account.getSalt();
      
      ByteSource saltBytes = ByteSource.Util.bytes(salt);
      
      return new SimpleAuthenticationInfo(email, password, saltBytes, getName());
  }
  
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
      Set<String> roles = new HashSet<String>();
      
      String email = (String)principals.getPrimaryPrincipal();
      Account account = accountService.getAccount(email);
      
      roles.add(account.isAdmin() ? "admin" : "normal");
      
      return new SimpleAuthorizationInfo(roles);
  }
}
