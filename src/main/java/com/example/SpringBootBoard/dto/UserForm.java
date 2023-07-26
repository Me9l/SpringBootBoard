package com.example.SpringBootBoard.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserForm {		// dto
	// client로부터 받은 필드값을 전달.
	// 유효성 체크.
	
	@NotEmpty(message = "아이디를 입력하세요.")
	@Size(min = 3 , max = 20)
	private String userid;
	
	@NotEmpty(message = "비밀번호를 입력하세요.")
	private String pw1;
	
	@NotEmpty
	private String pw2;
	
	@NotEmpty(message = "이메일을 입력하세요.")
	@Email
	private String email;
}
