package database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "VOTE")
public class Vote {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private int green;
	private int red;
	
	@ManyToOne
	@JoinTable(
		name = "VOTE_QUESTION",
		joinColumns = @JoinColumn(name = "VOTE_ID"), 
		inverseJoinColumns = @JoinColumn(name = "QUESTION_ID"))
	private Question question;
	
	@ManyToOne(optional = true)
	@JoinTable(
		name = "VOTE_VOTER",
		joinColumns = @JoinColumn(name = "VOTE_ID"), 
		inverseJoinColumns = @JoinColumn(name = "USER_ID"))
	private Voter voter;
	
	public Vote() {}
	
	public Vote (int green, int red, Question question, Voter voter) {
		this.green = green;
		this.red = red;
		this.question = question;
		this.voter = voter;
	}
	
	public int getGreen() {
		return green;
	}
	
	public void setGreen(int green) {
		this.green = green;
	}
	
	public int getRed() {
		return red;
	}
	
	public void setRed(int red) {
		this.red = red;
	}
	
	
	
	
}
