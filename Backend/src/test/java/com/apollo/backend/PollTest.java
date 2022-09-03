package com.apollo.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import poll.app.Poll;

@SpringBootTest
public class PollTest {
	
	@Test
	void pollIsCreated() {
		Poll tp = new Poll("test"); //TODO: insert dependencies
		
	}

}
