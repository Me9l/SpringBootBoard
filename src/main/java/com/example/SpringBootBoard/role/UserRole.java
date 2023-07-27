package com.example.SpringBootBoard.role;

import lombok.Getter;

@Getter
public enum UserRole {
	// 로그인 후 사용자의 권한 적용	
	ADMIN("ROLE_ADMIN"),
	USER("ROLE_USER");
	UserRole(String value) {
		this.value = value;
	}
	
	private String value;
}
