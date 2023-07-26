package com.example.SpringBootBoard.service;

import java.sql.Date;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.SpringBootBoard.entity.User;
import com.example.SpringBootBoard.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor	// DI : 생성자를 통해 객체 의존성 주입
@Service
public class UserService {
	private final UserRepository userRepository;	//@autowired를 사용하지 않고 @RequiredArgsConstructor 를 사용해 객체 주입
	private final PasswordEncoder passwordEncoder;
	
	public User create(String userid,String password,String email) {
		User user = new User();
		user.setUserid(userid);
//		user.setPassword(password);
//		method 내부에서 암호화객체를 생성하면 암호화 키가 변경될 수 있으므로 Spring IOC Container 에 객체를 생성하고 DI를 통해 의존성을 주입하여 사용한다.
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(password));
		user.setEmail(email);
		Date date =new Date(System.currentTimeMillis());
		user.setRegdate(date);
		
		userRepository.save(user);
		
		return user;
	}
}