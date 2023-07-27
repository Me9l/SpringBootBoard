package com.example.SpringBootBoard.controller;

import java.security.Principal;

import org.springframework.data.domain.Page;
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
	@GetMapping("/create")
	public String questionCreate(QuestionForm questionForm) {
		return "question/form";
	}
	
	// 질문 등록 요청 처리
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
}
