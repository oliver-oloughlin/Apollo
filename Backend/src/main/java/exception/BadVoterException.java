package exception;

public class BadVoterException extends Exception {

  public BadVoterException(String errorMessage) {
    super(errorMessage);
  }
}
