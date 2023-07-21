package com.example.SpringBootBoard.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.SpringBootBoard.entity.Question;
import com.example.SpringBootBoard.exception.DataNotFoundException;
import com.example.SpringBootBoard.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {
	
	private final QuestionRepository questionRepository;
	
	public Question getQuestion(Integer id) {
		Optional<Question> question = questionRepository.findById(id);
		
		if ( question.isPresent() ) {
			return question.get();
		}else {
			throw new DataNotFoundException("Can't find value.. check your data.");
		}
	}
	
	public List<Question> getAllQuestion() {
		return  questionRepository.findAllByOrderByRegdateDesc();
	}
}
