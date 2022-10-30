package exception;

public class NotAuthorizedVoterException extends Exception {

  public NotAuthorizedVoterException(String errorMessage) {
    super(errorMessage);
  }
}
