package dweet;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;

import model.Poll;

public class DweetSender {

  public void Send(Poll poll, String status) {

    PollResultWithStatus result = new PollResultWithStatus(poll, status);
    Gson gson = new Gson();
    String message = gson.toJson(result);

    byte[] out = message.getBytes(StandardCharsets.UTF_8);
    try {
      URL url = new URL("https://dweet.io/dweet/for/Poll" + poll.getCode());
      URLConnection con = url.openConnection();
      HttpURLConnection http = (HttpURLConnection) con;
      http.setRequestMethod("POST");
      http = (HttpURLConnection) con;
      http.setDoOutput(true);

      int length = out.length;

      http.setFixedLengthStreamingMode(length);
      http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
      http.connect();
      try (OutputStream os = http.getOutputStream()) {
        os.write(out);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println("API Sent: " + message + " to Dweet.io");
    System.out.println("Check it out at: https://dweet.io/follow/Poll" + poll.getCode());
  }

}
