package com.example.SpringBootBoard.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.SpringBootBoard.entity.Question;
import com.example.SpringBootBoard.service.QuestionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class QuestionController {
	
	private final QuestionService questionService;

	@GetMapping("/question/lists")
	public String questionList(Model model) {
		List<Question> questionList = questionService.getAllQuestion();
		model.addAttribute("questionList",questionList);
		return "/question/lists";
	}
	
	@GetMapping("/question/detail/{id}")
	public String questionDetail(@PathVariable Integer id,Model model) {
		Question question = questionService.getQuestion(id);
		model.addAttribute("question",question);
		return "question/detail";
	}
}
