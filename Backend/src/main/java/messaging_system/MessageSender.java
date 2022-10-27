package messaging_system;

import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import model.Poll;

public class MessageSender {

  private final String QUEUE_NAME = "PollQueue";
  private Gson gson;
  private ConnectionFactory factory;
  private Connection connection;
  private Channel channel;

  public MessageSender() {

    gson = new Gson();
    factory = new ConnectionFactory();
    factory.setHost("localhost");

    try {
      connection = factory.newConnection();
      channel = connection.createChannel();
      channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    } catch (Exception e) {
      System.out.println("Could not create Queue");
    }
  }

  public void sendPoll(Poll poll) {

    PollResult result = new PollResult(poll);
    String message = gson.toJson(result);
    try {
      channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
      System.out.println("API Sent " + message + " to Message System");
    } catch (Exception e) {
      System.out.println("Could not send Poll to messaging system");
    }
  }
}
