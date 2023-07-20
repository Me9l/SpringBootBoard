package com.example.SpringBootBoard;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.SpringBootBoard.entity.Question;
import com.example.SpringBootBoard.repository.QuestionRepository;

@SpringBootTest
class SpringBootBoardApplicationTests {

	@Autowired
	private QuestionRepository qRepo ;

	@Test
	void contextLoads() {
		Question q = new Question();
		q.setSubject("Test subject");
		q.setContent("Test Content");
		q.setRegdate(LocalDateTime.now());
		
		qRepo.save(q);
	}

}
