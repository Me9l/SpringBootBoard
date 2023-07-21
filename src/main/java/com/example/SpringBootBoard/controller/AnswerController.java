package com.example.SpringBootBoard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.SpringBootBoard.entity.Question;
import com.example.SpringBootBoard.service.AnswerService;
import com.example.SpringBootBoard.service.QuestionService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
	
	private final QuestionService questionService;
	private final AnswerService answerService;
	
	@PostMapping("/create/{id}")
	public String createAnswer(@PathVariable Integer id,String content) {
		Question question = questionService.getQuestion(id);
		answerService.createAnswer(question, content);
		return String.format("redirect:/question/detail/%d",id);
	}
}
