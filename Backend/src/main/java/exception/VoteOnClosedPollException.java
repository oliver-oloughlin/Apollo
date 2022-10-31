package exception;

public class VoteOnClosedPollException extends Exception {

  public VoteOnClosedPollException(String errorMessage) {
    super(errorMessage);
  }
}
