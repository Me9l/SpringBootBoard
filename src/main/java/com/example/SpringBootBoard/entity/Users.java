package com.example.SpringBootBoard.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Users {		// 사용자 정보를 입력하는 테이블과 매핑된 클래스
	
	@Id		// Primary Key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)	// userId : unique
	private String userid;
	
	private String password;
	
	@Column(unique = true)	// email : unique
	private String email;
	
	private Date regdate;
}