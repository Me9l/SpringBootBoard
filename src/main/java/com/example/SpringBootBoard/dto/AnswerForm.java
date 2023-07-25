package com.example.SpringBootBoard.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AnswerForm {
	
	@NotEmpty(message="내용을 반드시 입력하세요.")
	private String content;
}