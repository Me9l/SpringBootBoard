package com.example.SpringBootBoard.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.SpringBootBoard.entity.Question;
import com.example.SpringBootBoard.exception.DataNotFoundException;
import com.example.SpringBootBoard.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {
	
	private final QuestionRepository questionRepository;
	
	// 전체 List 조회
	public List<Question> getQuestionList() {
		return questionRepository.findAllByOrderByRegdateDesc();
	}
	
	// 전체 List 조회 ( paging )
	public Page<Question> getQuestionList(int page) {
		// Pageable 객체에 특정 컬럼을 정렬할 객체를 생성해서 인자로 넣는다.
		List<Sort.Order> sort = new ArrayList<>();
		sort.add(Sort.Order.desc("regdate"));
		// page : client에서 parameter로 넘긴 페이지 번호
		// 10 : 한 페이지에서 출력할 레코드 수
		// Sort.by(sort) : 정렬할 컬럼
		Pageable pageable = PageRequest.of(page, 10,Sort.by(sort));
		Page<Question> pageQuestion = questionRepository.findAll(pageable);
		return pageQuestion;
	}
	
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
