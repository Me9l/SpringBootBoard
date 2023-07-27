package com.example.SpringBootBoard.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 200)
	private String subject;
	
	@Column(columnDefinition = "Text")
//	@Column(length=1000)	// Oracle
	private String content;
	
	@CreatedDate
	private LocalDateTime regdate;
	
	@OneToMany(mappedBy = "question" , cascade = CascadeType.REMOVE)
	private List<Answer> answerList;
	
	@ManyToOne
	private Users author;
}
