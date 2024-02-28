package com.java_proj.quizapp.controller;

import com.java_proj.quizapp.model.Question;
import com.java_proj.quizapp.model.QuestionWrapper;
import com.java_proj.quizapp.model.Response;
import com.java_proj.quizapp.service.QuizService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int numOfQuestions, @RequestParam String title) {
        return quizService.createQuiz(category, numOfQuestions, title);
    }

    @GetMapping("get/{quizId}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer quizId){
        return quizService.getQuizQuestions(quizId);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses) {
        return quizService.calculateResult(id, responses);

    }


}
