package security;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;

import org.apache.commons.validator.routines.EmailValidator;

import modelweb.WebAccount;
import modelweb.WebPoll;
import modelweb.WebQuestion;
import modelweb.WebVote;

//Checks for errors that will not result in exceptions when mapping web objects to server objects
public class InputValidator {

  public boolean isVaildWebAccount(WebAccount webAccount) {

    return EmailValidator.getInstance().isValid(webAccount.getEmail());
  }

  public boolean isValidWebPoll(WebPoll webPoll, boolean fromPost) {

    boolean validTimeStamp = false;
    SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      format.parse(webPoll.getTimeToStop());
      validTimeStamp = true;
    } catch (ParseException pe) {
    }

    if (fromPost && validTimeStamp) {
      Timestamp instant = Timestamp.from(Instant.now());
      Timestamp pollTimeToStop = Timestamp.valueOf(webPoll.getTimeToStop());

      if (instant.compareTo(pollTimeToStop) >= 0) {
        validTimeStamp = false;
      }
    }
    return webPoll.getTitle().length() < 50
        && validTimeStamp;
  }

  public boolean isValidWebQuestion(WebQuestion webQuestion) {
    return webQuestion.getText().length() < 300;
  }

  public boolean isValidWebVote(WebVote webVote) {
    return webVote.getGreen() + webVote.getRed() < 100;
  }
}
