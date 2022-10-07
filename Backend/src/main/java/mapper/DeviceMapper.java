package mapper;

import java.util.Set;
import java.util.stream.Collectors;

import model.IoTDevice;
import model.Question;
import model.Vote;
import modelweb.WebDevice;
import service.QuestionService;
import service.VoteService;

public class DeviceMapper {

  VoteService voteService;
  QuestionService questionService;
  
  public DeviceMapper(VoteService voteService, QuestionService questionService) {
    this.voteService = voteService;
    this.questionService = questionService;
  }
  
  public IoTDevice mapWebDeviceToDevice(WebDevice webDevice) {
    
    Set<Vote> votes = webDevice.getVoteIds().stream()
        .map(id -> voteService.getVote(id))
        .collect(Collectors.toSet());
    
    Question question = questionService.getQuestion(webDevice.getQuestionId());
    
    return new IoTDevice(webDevice.getToken(), question, votes);
  }
  
  public WebDevice mapDeviceToWebDevice(IoTDevice device) {
    
    Set<Long> voteIds = device.getVotes().stream()
        .map(vote -> vote.getId())
        .collect(Collectors.toSet());
    
    return new WebDevice(device.getToken(), device.getQuestion().getId(), voteIds);
  }
}
