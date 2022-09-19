package com.apollo.backend;

import javax.persistence.EntityManager;

/*
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
*/

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import model.*;

public class BackendApplication {

	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ApolloPU");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		//Create DB code:
		/*
		Account anders = new Account("Anders@email.com", "Password", true, AccountType.FACEBOOK);
		Account oliver = new Account("Oliver@email.com", "Pass123", false, AccountType.NORMAL);
		Question myQuestion1 = new Question("Have you had a nice day?");
		Question myQuestion2 = new Question("Do you like cars?");
		Question myQuestion3 = new Question("Do you like food?");
		Question myQuestion4 = new Question("Do you like politics?");
		IoTDevice device = new IoTDevice(myQuestion1);
		Poll poll1 = new Poll(4829L, "My Private Poll", "testTime", true, anders);
		Poll poll2 = new Poll(4830L, "My Public Poll", "testTime", false, anders);
		Vote vote1 = new Vote(1, 0, myQuestion1, oliver);
		Vote vote2 = new Vote(1, 2, myQuestion2, oliver);
		Vote vote3 = new Vote(4, 0, myQuestion3, oliver);
		Vote vote4 = new Vote(0, 5, myQuestion4, oliver);
		Vote vote5 = new Vote(1, 1, myQuestion1, device);
		
		anders.addPoll(poll1);
		anders.addPoll(poll2);
		device.addVote(vote5);
		poll1.addQuestion(myQuestion1);
		poll1.addQuestion(myQuestion2);
		poll2.addQuestion(myQuestion3);
		poll2.addQuestion(myQuestion4);
		
		myQuestion1.addVote(vote1);
		myQuestion2.addVote(vote2);
		myQuestion3.addVote(vote3);
		myQuestion4.addVote(vote4);
		
		tx.begin();
		em.persist(anders);
		em.persist(oliver);
		em.persist(device);
		tx.commit();
		*/
		
		//Test DB code:
		
		Account anders = em.find(Account.class, "Anders@email.com");
		System.out.println("\n" + (anders.getPassword().equals("Password") ? "Admin fetched successfully" : "Error"));
		System.out.println("AccountType: " + anders.getAccountType());
		System.out.println("Admin polls:");
		anders.getPolls().forEach((Poll p) -> System.out.println(p.getTitle()));
		System.out.println("Questions in \"My Public Poll\":");
		Poll publicPoll = anders.getPolls().stream().filter((Poll p) -> p.getTitle().equals("My Public Poll")).findAny().get();
		publicPoll.getQuestions().forEach((Question q) -> System.out.println(q.getText()));
		System.out.println("Votes for in \"Do you like food\":");
		Question foodQuestion = publicPoll.getQuestions().stream().filter((Question q) -> q.getText().equals("Do you like food?")).findAny().get();
		foodQuestion.getVotes().forEach((Vote v) -> System.out.println("Green: " + v.getGreen() + ", Red: " + v.getRed()));
		IoTDevice device = (IoTDevice) em.createQuery("SELECT d FROM IoTDevice d").getSingleResult();
		device.getVotes().forEach((Vote v) -> System.out.println("Green: " + v.getGreen() + ", Red: " + v.getRed()));
		em.close();
		emf.close();
		
	}
}

