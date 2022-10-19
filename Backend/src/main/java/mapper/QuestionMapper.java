package mapper;

import java.util.Set;
import java.util.stream.Collectors;

import model.IoTDevice;
import model.Poll;
import model.Question;
import model.Vote;
import modelweb.WebQuestion;
import service.IoTService;
import service.PollService;
import service.VoteService;

public class QuestionMapper {
  
  IoTService deviceService;
  VoteService voteService;
  PollService pollService;
  
  public QuestionMapper(IoTService deviceService, VoteService voteService, PollService pollService) {
    this.deviceService = deviceService;
    this.voteService = voteService;
    this.pollService = pollService;
  }
  
  public Question mapWebQuestionToQuestion(WebQuestion webQuestion) {
    Set<IoTDevice> devices = webQuestion.getDeviceTokens().stream()
        .map(token -> deviceService.getDevice(token))
        .collect(Collectors.toSet());
    
    Set<Vote> votes = webQuestion.getDeviceTokens().stream()
        .map(id -> voteService.getVote(id))
        .collect(Collectors.toSet());
    
    Poll poll = pollService.getPoll(webQuestion.getPollCode());
    
    return new Question(webQuestion.getText(), poll, votes, devices);
  }
  
  public WebQuestion mapQuestionToWebQuestion(Question question) {
    
    if(question == null) {
      return null;
    }
    
    Set<Long> deviceTokens = question.getDevices().stream()
        .map(device -> device.getToken())
        .collect(Collectors.toSet());
    
    Set<Long> voteIds = question.getVotes().stream()
        .map(vote -> vote.getId())
        .collect(Collectors.toSet());
    
    return new WebQuestion(question.getId(), question.getText(), question.getPoll().getCode(), voteIds, deviceTokens);
  }
}
