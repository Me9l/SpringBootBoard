package com.example.SpringBootBoard.entity;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(columnDefinition = "text")
//	@Column(length=1000)	// Oracle
	private String content;
	
	@CreatedDate
	private LocalDateTime regdate;

	private LocalDateTime modifiedDate;

	@ManyToOne(fetch = FetchType.LAZY)
	private Question question;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Users author;
	
	@ManyToMany(fetch = FetchType.LAZY)
	Set<Users> vote;
}
