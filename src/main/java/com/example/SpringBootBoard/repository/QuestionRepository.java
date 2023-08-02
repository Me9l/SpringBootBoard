package com.example.SpringBootBoard.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.SpringBootBoard.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

//	SELECT * FROM question WHERE subject LIKE '%?%'
	List<Question> findBySubjectLike(String subject);
//	SELECT * FROM question WHERE content LIKE '%?%'
	List<Question> findByContentLike(String content);
	
//	SELECT * FROM question WHERE subject LIKE () or content LIKE()
	List<Question> findBySubjectLikeOrContentLike(String subject, String content);
	
//	SELECT * FROM question ORDER BY regdate ASC,DESC;
	List<Question> findAllByOrderByRegdateAsc();
	List<Question> findAllByOrderByRegdateDesc();
	
//  Paging
	Page<Question> findAll(Pageable pageable);
	
//	Paging with search
	@Query(
			"SELECT distinct q " // 공백 주의
			+ "FROM Question q "
			+"LEFT OUTER JOIN Users u1 "
				+"ON q.author = u1 "
			+"LEFT OUTER JOIN Answer a "
				+"ON a.question = q "
			+"LEFT OUTER JOIN Users u2 "
				+"ON a.author = u2 "
			+"WHERE q.subject LIKE %:kw% "
				+ "or q.content LIKE %:kw% "
				+ "or u1.userid LIKE %:kw% "
				+ "or a.content LIKE %:kw% "
				+ "or u2.userid LIKE %:kw%"
			)
	Page<Question> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
}
