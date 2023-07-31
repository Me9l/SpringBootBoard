package com.example.SpringBootBoard.controller;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.SpringBootBoard.dto.AnswerForm;
import com.example.SpringBootBoard.dto.QuestionForm;
import com.example.SpringBootBoard.entity.Question;
import com.example.SpringBootBoard.entity.Users;
import com.example.SpringBootBoard.service.QuestionService;
import com.example.SpringBootBoard.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {
	
	private final QuestionService questionService;
	private final UserService userService;

	// QuestionList 요청 처리
	@GetMapping("/list")
	public String questionList(Model model,@RequestParam(value="page",defaultValue="0") int page) {
//		List<Question> questionList = questionService.getAllQuestion();
		Page<Question> paging = questionService.getQuestionList(page);
		model.addAttribute("paging",paging);
		return "question/list";
	}
	
	// 상세페이지
	@GetMapping("/detail/{id}")
	public String questionDetail(@PathVariable Integer id,Model model, AnswerForm answerForm) {
		Question question = questionService.getQuestion(id);
		model.addAttribute("question",question);
		return "question/detail";
	}
	
	// 질문 등록 페이지
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String questionCreate(QuestionForm questionForm) {
		return "question/form";
	}
	
	// 질문 등록 요청 처리
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String questionCreate(@Valid QuestionForm questionForm,BindingResult bindingResult, Principal principal) {
								//@Valid questionForm 의 유효성 검사 // 유효성 문제가 있으면 bindingResult에 전송.
		if ( bindingResult.hasErrors() ) {
			return "question/form";
		}
		Users users =
				userService.getUser(principal.getName());
		questionService.createQuestion(questionForm.getSubject(), questionForm.getContent(), users);
		return "redirect:/question/list";
	}
	
	// 글 수정 ( 페이지 이동 )
		@PreAuthorize("isAuthenticated()")
		@GetMapping("/modify/{id}")
		public String questionModify(
				@PathVariable("id") Integer id,
				QuestionForm questionForm,
				Principal principal) {
			
			// id를 이용해 Question.repo의 값을 받아온다. ( Service )
			Question question = questionService.getQuestion(id);
			// DB에서 가져온 question 객체의 값을 questionForm에 주입
			questionForm.setSubject(question.getSubject());
			questionForm.setContent(question.getContent());
			
			return "question/form";
		}
		
		@PreAuthorize("isAuthenticated()")
		@PostMapping("/modify/{id}")
		public String questionModify(
				@PathVariable("id") Integer id,
				@Valid QuestionForm questionForm, BindingResult bindingResult,
				Principal principal
				) {
			
			// 글 수정시 제목,내용이 null 일때 
			if (bindingResult.hasErrors()) {
				return "question/form";
			}
			
			Question question = questionService.getQuestion(id);		
			questionService.modifyQuestion(question, questionForm.getSubject(), questionForm.getContent());
			
			return String.format("redirect:/question/detail/%s", id);
		}
		
		// 글 삭제
		@PreAuthorize("isAuthenticated()")
		@GetMapping("/delete/{id}")
		public String delete(@PathVariable("id") Integer id) {
			
			// id로 question객체 받기
			Question question = questionService.getQuestion(id);
			questionService.deleteQuestion(question);
			
			return "redirect:/";	// http://localhost:8585/
		}
		
		// 추천 기능
		@GetMapping("/vote/{id}")
		public String Vote(
				@PathVariable Integer id,
				Principal principal
				) {
			// 전달받은 변수로 question 객체 요청
			Question question = questionService.getQuestion(id);
			
			// principal 객체로 로그인한 Users 정보 요청
			Users users = userService.getUser(principal.getName());
			
			questionService.voteQuestion(question, users);
			
			return String.format("redirect:/question/detail/%s", id);
		}
}
