package com.example.SpringBootBoard.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.SpringBootBoard.entity.Answer;
import com.example.SpringBootBoard.entity.Question;
import com.example.SpringBootBoard.entity.Users;
import com.example.SpringBootBoard.repository.AnswerRepository;
import com.example.SpringBootBoard.exception.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service

public class AnswerService {
	private final AnswerRepository answerRepository;
	
	// 답글 등록
	public void createAnswer(Question question,String content, Users author) {
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setQuestion(question);
		answer.setRegdate(LocalDateTime.now());
		
		// 답글 작성자
		answer.setAuthor(author);
		
		answerRepository.save(answer);
	}
	
	// 답글 수정을 위해 답글 받아오기
	public Answer getAnswer(Integer id) {
		Optional<Answer> _answer = answerRepository.findById(id);
		
		if ( _answer.isPresent() ) {
			return _answer.get();
		} else {
			throw new DataNotFoundException("answer data is empty");
		}
	}
	
	// 답글 수정
	public void modifyAnswer(Answer answer , String content) {
		answer.setContent(content);
		answer.setModifiedDate(LocalDateTime.now());
		
		answerRepository.save(answer);
	}
	
	// 답글 삭제
	public void deleteAnswer(Answer answer) {
		answerRepository.delete(answer);
	}
	
	// 답글 추천
	public void voteAnswer(Answer answer , Users users) {
		answer.getVote().add(users);
		answerRepository.save(answer);
	}
}
