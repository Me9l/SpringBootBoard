package com.example.SpringBootBoard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SpringBootBoard.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

//	SELECT * FROM question WHERE subject LIKE '%?%'
	List<Question> findBySubjectLike(String subject);
//	SELECT * FROM question WHERE content LIKE '%?%'
	List<Question> findByContentLike(String content);
	
//	SELECT * FROM question WHERE subject LIKE () or content LIKE()
	List<Question> findBySubjectLikeOrContentLike(String subject, String content);
	
//	SELECT * FROM question ORDER BY create_date ASC,DESC;
	List<Question> findAllByOrderByCreateDateAsc();
	List<Question> findAllByOrderByCreateDateDesc();
}
