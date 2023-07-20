package com.example.SpringBootBoard.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.SpringBootBoard.entity.Question;
import com.example.SpringBootBoard.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {
	
	private final QuestionRepository questionRepository;
	
	public List<Question> getAllQuestion() {
		return  questionRepository.findAllByOrderByRegdateDesc();
	}
}
