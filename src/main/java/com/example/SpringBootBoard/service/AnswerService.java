package com.example.SpringBootBoard.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.SpringBootBoard.entity.Answer;
import com.example.SpringBootBoard.entity.Question;
import com.example.SpringBootBoard.repository.AnswerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service

public class AnswerService {
	private final AnswerRepository answerRepository;
	
	public void createAnswer(Question question,String content) {
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setQuestion(question);
		answer.setRegdate(LocalDateTime.now());
		
		answerRepository.save(answer);
	}
}
