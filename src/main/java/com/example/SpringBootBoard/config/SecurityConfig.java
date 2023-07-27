package com.example.SpringBootBoard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	// Spring Security control class
	/*
	TestDemoApplication.java	: springBoot가 부팅될 때 모든 설정이 적용되며 실행.
	@Configuration				: 수동으로 스프링 컨테이너에 빈을 등록하기 위해 사용하는 어노테이션.
								  Configuration 안에 @Bean 어노테이션을 사용해 빈을 등록한다.
								  Configuration 안에서 @Bean을 사용해야 singleton 을 보장받을 수 있다.
//	@Component					: 자동으로 스프링 컨테이너에 빈을 등록. ( 하위 어노테이션으로 @Configuration, @Controller, @Service, @Repository 등이 있다.)
	@Bean						: method위에 사용. 리턴 객체를 Spring IoC Container 에 등록
	@EnableWebSecurity			: 스프링 security 설정
	*/
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		// http://localhost:8686/ 요청에 대해 인증없이 접근 허용.
		.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
		.requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
//		.csrf().disable() // csrf 비활성화
		.csrf( (csrf)->csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")) ) // H2DB : csrf 설정 적용이 안되므로 request에 대해 ignore 설정.
		// H2Console 에서 frame 작동 설정.
		.headers( (headers)->headers.addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)) )
		
		// Login setting : Controller 에서 처리하지 않고 Spring Security 에서 처리하도록 설정.
		.formLogin(
			(formLogin)->formLogin
			.usernameParameter("userid")
			.loginPage("/user/login")	// POST요청을 Security 에서 처리.
			.defaultSuccessUrl("/")		// 인증 성공시 이동할 경로 : "/"
			)
		// Logout setting
		.logout(
			(logout)->logout
			.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
			.logoutSuccessUrl("/")
			.invalidateHttpSession(true)
			)

		;
		return http.build();
	}
	
	@Bean	// Spring Container ( IOC Container ) 객체를 등록
	PasswordEncoder passwordEncoder() {
		// PasswordEncoder : Interface
		// BCryptPasswordEncoder : Implements
		return new BCryptPasswordEncoder();
	}
	
	// 인증 처리하는 객체의 Bean 등록
	// UserSecurityService.java 를 위한 bean 등록
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}