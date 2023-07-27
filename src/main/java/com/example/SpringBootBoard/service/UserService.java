package com.example.SpringBootBoard.service;

import java.sql.Date;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.SpringBootBoard.entity.Users;
import com.example.SpringBootBoard.exception.DataNotFoundException;
import com.example.SpringBootBoard.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor	// DI : 생성자를 통해 객체 의존성 주입
@Service
public class UserService {
	private final UserRepository userRepository;	//@autowired를 사용하지 않고 @RequiredArgsConstructor 를 사용해 객체 주입
	private final PasswordEncoder passwordEncoder;
	
	public Users create(String userid,String password,String email) {
		Users users = new Users();
		users.setUserid(userid);
//		users.setPassword(password);
//		method 내부에서 암호화객체를 생성하면 암호화 키가 변경될 수 있으므로 Spring IOC Container 에 객체를 생성하고 DI를 통해 의존성을 주입하여 사용한다.
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		users.setPassword(passwordEncoder.encode(password));
		users.setEmail(email);
		Date date =new Date(System.currentTimeMillis());
		users.setRegdate(date);
		
		userRepository.save(users);
		
		return users;
	}
	
	// 사용자 정보 읽기
		public void selectUser(String userid) {
			Optional<Users> _Users =
			userRepository.findByuserid(userid);
			
			if (_Users.isPresent()) {
				System.out.println("이미 등록된 사용자 입니다.");
				Users users = _Users.get();
				System.out.println("userId : " + users.getUserid());
				System.out.println("userPw : " + users.getPassword());
				System.out.println("userId : " + users.getUserid());
				
			} else {
				System.out.println("등록되지 않은 사용자 입니다.");
			}
		}
		
		// userid를 받아 DB에서 값을 가져오는 method
		
		public Users getUser(String userid) {
			
			Optional<Users> _users = userRepository.findByuserid(userid);
			// 값이 있을 경우
			if ( _users.isPresent() ) {
				return _users.get();
			} else {
				} throw new DataNotFoundException("해당하는 아이디가 없습니다.");
		}
}