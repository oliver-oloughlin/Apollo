package com.apollo.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import poll.app.Poll;
import poll.app.Poll.PollBuilder;

@SpringBootTest
public class PollTest {
	
	@Test
	void pollIsCreated() {
		Poll tp = new Poll.PollBuilder("test")
				.build();
		
	}

}
