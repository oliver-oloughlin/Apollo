package exception;

public class PrivatePollNotAuthenticatedException extends Exception {

  public PrivatePollNotAuthenticatedException(String errorMessage) {
    super(errorMessage);
  }
}
