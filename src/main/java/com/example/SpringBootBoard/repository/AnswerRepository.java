package com.example.SpringBootBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SpringBootBoard.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Integer>{

}