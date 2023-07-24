package com.example.SpringBootBoard.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.SpringBootBoard.dto.QuestionForm;
import com.example.SpringBootBoard.entity.Question;
import com.example.SpringBootBoard.service.QuestionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {
	
	private final QuestionService questionService;

	// QuestionList 요청 처리
	@GetMapping("/list")
	public String questionList(Model model) {
		List<Question> questionList = questionService.getAllQuestion();
		model.addAttribute("questionList",questionList);
		return "question/list";
	}
	
	// 상세페이지
	@GetMapping("/detail/{id}")
	public String questionDetail(@PathVariable Integer id,Model model) {
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
	public String questionCreate(@Valid QuestionForm questionForm,BindingResult bindingResult) {
		
		if ( bindingResult.hasErrors() ) {
			return "question/form";
		}
		questionService.createQuestion(questionForm.getSubject(), questionForm.getContent());
		return "redirect:/question/list";
	}
}
