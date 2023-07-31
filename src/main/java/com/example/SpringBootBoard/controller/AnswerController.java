package com.example.SpringBootBoard.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.example.SpringBootBoard.dto.AnswerForm;
import com.example.SpringBootBoard.entity.Answer;
import com.example.SpringBootBoard.entity.Question;
import com.example.SpringBootBoard.entity.Users;
import com.example.SpringBootBoard.service.AnswerService;
import com.example.SpringBootBoard.service.QuestionService;
import com.example.SpringBootBoard.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
	
	private final QuestionService questionService;
	private final AnswerService answerService;
	private final UserService userService;
	
	@PreAuthorize("isAuthenticated()") //인증된 사용자가 아니면 login 페이지로 redirect
	@PostMapping("/create/{id}")
	public String createAnswer(@PathVariable Integer id, @Valid AnswerForm answerForm, BindingResult bindingResult, Model model, Principal principal) {
		Question question = questionService.getQuestion(id);
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("question",question);
			return "question/detail";
		}
		
		//principal 에서 userid를 받아 user 객체를 받는다.
		Users users = userService.getUser(principal.getName());
		
		answerService.createAnswer(question, answerForm.getContent(), users);
		return String.format("redirect:/question/detail/%d",id);
	}
	
	// 답글 수정페이지 이동 ( GET )
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String modifyAnswer(
			@PathVariable("id") Integer id,
			AnswerForm answerForm,
			Principal principal
			) {
		// id값으로 객체 반환
		Answer answer = answerService.getAnswer(id);
		// answer_Form : DB에서 값을 가져와 저장 후 view로 전송
		answerForm.setContent(answer.getContent());

		return "answer_form";
	}
	
	// 답글 수정
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String modifyAnswer(
			@PathVariable Integer id,
			@Valid AnswerForm answerForm, // @Valid form 유효성 검사 ( null 확인 )
			BindingResult bindingResult,
			Principal principal
			) {
		Answer answer = answerService.getAnswer(id);
		
		// AnswerForm 의 content 값이 없을 경우
		if ( bindingResult.hasErrors() ) {
			return "answer_form";
		}
		
		if (!answer.getAuthor().getUserid().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제 권한이 없습니다.");
		}
		
		answerService.modifyAnswer(answer, answerForm.getContent());
		return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
		}

	// 답글 삭제
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String DeleteAnswer(@PathVariable Integer id) {
		// id 값으로 Answer 객체 찾기
		Answer answer = answerService.getAnswer(id);
		answerService.deleteAnswer(answer);
		
		return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
	}
	
	// 답글 추천
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String voteAnswer(
			@PathVariable Integer id,
			Principal principal
			) {
		Answer answer = answerService.getAnswer(id);
		Users siteUser = userService.getUser(principal.getName());
		
		answerService.voteAnswer(answer, siteUser);
		
		return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
	}
}
