package com.example.SpringBootBoard.service;

import java.time.LocalDateTime;
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
	
	// 상세 조회
	public Question getQuestion(Integer id) {
		Optional<Question> question = questionRepository.findById(id);
		
		if ( question.isPresent() ) {
			return question.get();
		}else {
			throw new DataNotFoundException("Can't find value.. check your data.");
		}
	}
	
	// 전체 조회
	public List<Question> getAllQuestion() {
		return  questionRepository.findAllByOrderByRegdateDesc();
	}
	
	// 질문 저장
	public void createQuestion(String subject,String content) {
		Question question = new Question();
		question.setSubject(subject);
		question.setContent(content);
		question.setRegdate(LocalDateTime.now());
		
		questionRepository.save(question);
	}
}
