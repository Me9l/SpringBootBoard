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
import com.example.SpringBootBoard.entity.Users;
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
	public Page<Question> getQuestionList(int page, String kw) {
		// Pageable 객체에 특정 컬럼을 정렬할 객체를 생성해서 인자로 넣는다.
		List<Sort.Order> sort = new ArrayList<>();
		sort.add(Sort.Order.desc("id"));
		// page : client에서 parameter로 넘긴 페이지 번호
		// 10 : 한 페이지에서 출력할 레코드 수
		// Sort.by(sort) : 정렬할 컬럼
		Pageable pageable = PageRequest.of(page, 10,Sort.by(sort));
		Page<Question> pageQuestion = questionRepository.findAllByKeyword(kw, pageable);
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

	// 질문 저장 (글 등록)
	public void createQuestion(String subject, String content, Users user) {
		Question question = new Question();
		question.setSubject(subject);
		question.setContent(content);
		question.setRegdate(LocalDateTime.now());
		question.setAuthor(user);
		
		questionRepository.save(question);
	}
	// 글 수정
	public void modifyQuestion( Question question, String subject, String content ) {
		
//			System.out.println("수정된 제목 : " + subject);
//			System.out.println("수정된 내용 : " + content);
		question.setSubject(subject);
		question.setContent(content);
		question.setRegdate(LocalDateTime.now());
		
		questionRepository.save(question);
	}
	
	// 글 삭제
	public void deleteQuestion( Question question ) {
		// 삭제할 Question 객체를 가져온다.
		questionRepository.delete(question);
	}
	
	// 추천 기능
	public void voteQuestion( Question question, Users users ) {
		question.getVote().add(users);
		questionRepository.save(question);
	}
}
