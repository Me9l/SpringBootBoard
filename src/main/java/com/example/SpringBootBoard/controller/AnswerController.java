package com.example.SpringBootBoard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.SpringBootBoard.dto.AnswerForm;
import com.example.SpringBootBoard.entity.Question;
import com.example.SpringBootBoard.service.AnswerService;
import com.example.SpringBootBoard.service.QuestionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
	
	private final QuestionService questionService;
	private final AnswerService answerService;
	
	@PostMapping("/create/{id}")
	public String createAnswer(@PathVariable Integer id, @Valid AnswerForm answerForm, BindingResult bindingResult, Model model) {
		Question question = questionService.getQuestion(id);
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("question",question);
			return "question/detail";
		}
		answerService.createAnswer(question, answerForm.getContent());
		return String.format("redirect:/question/detail/%d",id);
	}
}
