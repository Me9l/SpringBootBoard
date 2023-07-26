package com.example.SpringBootBoard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.SpringBootBoard.dto.UserForm;
import com.example.SpringBootBoard.entity.User;
import com.example.SpringBootBoard.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/user")
@RequiredArgsConstructor
@Controller
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/signup")
	public String signUp(UserForm userForm) {
		return "signup_form";
	}
	
	@GetMapping("/signupTest")
	public String signUpTest(@RequestParam(value = "userid") String userid,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "email") String email) {
		
		System.out.println("RequestParam > userid : " + userid);
		System.out.println("RequestParam > password : " + password);
		System.out.println("RequestParam > email : " + email);
		
		User siteUser =
		userService.create(userid, password, email);
		
		return "redirect:/";
	}
	
	@PostMapping("/signup")
	public String signUp(@Valid UserForm userForm, BindingResult bindingResult) {

//		System.out.println("userid : " + userForm.getUserid());
//		System.out.println("userpw1 : " + userForm.getPw1());
//		System.out.println("userpw2 : " + userForm.getPw2());
//		System.out.println("email : " + userForm.getEmail());
//		Date date =new Date(System.currentTimeMillis());
//		System.out.println("date : " + date);
		
		// 1. userForm 유효성 확인
		
		if ( bindingResult.hasErrors() ) {
			return "signup_form";
		}
		// 2. pw1 , pw2 같은지 확인
		if ( !userForm.getPw1().equals(userForm.getPw2()) ) {
			// Error message client에게 전달
			bindingResult.rejectValue("pw2", "passwordInCorrect", "패스워드가 일치하지 않습니다.");
			return "signup_form";
		}
		// 3. Service.create(userid,password,email) 호출 및 저장.
		userService.create(userForm.getUserid(), userForm.getPw1(), userForm.getEmail());
		
		return "redirect:/";
	}
}