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
		
		Voter anders = new Voter("Anders", "Password", true);
		Question myQuestion = new Question("Have you had a nice day?");
		Poll poll = new Poll("My Private Pole", 4820, "testTime", true, anders);
		Vote vote = new Vote(1, 0, myQuestion, anders);
		
		anders.addPoll(poll);
		anders.addVote(vote);
		myQuestion.addVote(vote);
		myQuestion.setPoll(poll);
		poll.addQuestion(myQuestion);
		
		tx.begin();
		em.persist(anders);
		em.persist(myQuestion);
		em.persist(poll);
		em.persist(vote);
		tx.commit();
		
		em.close();
		emf.close();
	}
}

