package exception;

public class VoteOnOtherAccountException extends Exception {

  public VoteOnOtherAccountException(String errorMessage) {
    super(errorMessage);
  }
}
