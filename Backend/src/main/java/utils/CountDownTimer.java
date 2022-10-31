package utils;

import java.util.Timer;
import java.util.TimerTask;

public class CountDownTimer {
  Timer t;

  public CountDownTimer(int seconds) {
    t = new Timer();

    // starts the timer thread, and counting down
    t.schedule(new rt(), seconds * 1000);
  }

  class rt extends TimerTask {

    @Override
    public void run() {
      System.out.println("Time is up!!");
      t.cancel();
    }
  }
}
