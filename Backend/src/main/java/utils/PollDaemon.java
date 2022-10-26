package utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import model.Poll;
import service.PollService;

public class PollDaemon {

  PollService pollService;

  public PollDaemon(PollService pollService) {

    this.pollService = pollService;

    Runnable daemonRunner = new Runnable() {
      public void run() {
        while (true) {
          Timestamp now = Timestamp.from(Instant.now());

          List<Poll> polls = pollService.getAllPolls();
          for (Poll poll : polls) {
            Timestamp pollTimeToStop = Timestamp.valueOf(poll.getTimeToStop());
            if (!poll.isClosed() && now.compareTo(pollTimeToStop) >= 0) {
              poll = pollService.closePoll(poll);
            }
          }
          try {
            Thread.sleep(30 * 1000);
          } catch (InterruptedException e) {
            System.out.println("Daemon error");
          }
        }
      }
    };

    Thread daemonThread = new Thread(daemonRunner);
    daemonThread.setDaemon(true);
    daemonThread.start();
  }

}
