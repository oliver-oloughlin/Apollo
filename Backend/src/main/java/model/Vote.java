package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	@JoinColumn(name = "questionId")
	private Question question;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "accountId")
	private Account voter;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "deviceId")
	private IoTDevice device;
	
	
	public Vote() {}
	
	public Vote (int green, int red, Question question) {
		this.green = green;
		this.red = red;
		this.question = question;
	}
	
	public Vote (int green, int red, Question question, Account voter) {
		this.green = green;
		this.red = red;
		this.question = question;
		this.voter = voter;
	}
	
	public Vote (int green, int red, Question question, IoTDevice device) {
		this.green = green;
		this.red = red;
		this.question = question;
		this.device = device;
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

	public Question getQuestion() {
		return question;
	}

	public Account getVoter() {
		return voter;
	}

	public IoTDevice getDevice() {
		return device;
	}
	
}
