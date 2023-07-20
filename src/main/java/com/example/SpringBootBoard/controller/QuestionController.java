package com.example.SpringBootBoard.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.SpringBootBoard.entity.Question;
import com.example.SpringBootBoard.service.QuestionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class QuestionController {
	
	private final QuestionService questionService;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	@GetMapping("/question/list")
	public String questionList(Model model) {
		List<Question> questionList = questionService.getAllQuestion();
		model.addAttribute("questionList",questionList);
		return "question/lists";
	}
}
