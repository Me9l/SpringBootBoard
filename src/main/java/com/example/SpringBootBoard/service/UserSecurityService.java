package com.example.SpringBootBoard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.SpringBootBoard.entity.Users;
import com.example.SpringBootBoard.repository.UserRepository;
import com.example.SpringBootBoard.role.UserRole;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

	private final UserRepository userRepository;
	
	// authentication process method
	@Override
	public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {

	// login_form.html 에서 받은 field : userid, password
			// input으로 받은 userid를 DataBase에서 조회.
			Optional<Users> _users = userRepository.findByuserid(userid);

			// DB에 userid가 없을 경우
			if ( _users.isEmpty() ) {
				// 예외를 발생시켜 view 페이지에 오류 메세지를 넘김
				throw new UsernameNotFoundException("등록되지 않은 ID. " + userid);
			// DB에 userid가 있을 경우
			}
			
			// _siteUser의 값이 비어있지 않으면 _siteUser 의 값을 siteUser에 옮겨담는다.
			Users users = _users.get();

			// GrantedAuthority : 권한을 저장하는 객체
			List<GrantedAuthority> authorities = new ArrayList<>();
			// GrantedAuthority : 인터페이스
			// SimpleGrantedAuthority : 구현체
			
			
			// 관리자/사용자 처리
			// userid.equals("admin") : 관리자 권한 부여 , 그 외엔 일반사용자.
			if ( userid.equals("admin") ) {
				// ADMIN("ROLE_ADMIN")
				authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
			} else {
				// USER("ROLE_USER")
				authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
			}

			// UserDetails : 인터페이스.
			// User : UserDetails의 구현체.
			// User ( userid, password, authority )
			return new User(users.getUserid(), users.getPassword(), authorities);
	}
}
