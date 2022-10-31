package messaging_system;

@SuppressWarnings("unused")
public class QuestionResult {

  private String question;
  private int yes;
  private int no;

  public QuestionResult(String question, int yes, int no) {
    this.question = question;
    this.yes = yes;
    this.no = no;
  }

}
