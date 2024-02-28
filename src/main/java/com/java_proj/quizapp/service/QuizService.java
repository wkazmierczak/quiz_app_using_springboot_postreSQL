package com.java_proj.quizapp.service;

import com.java_proj.quizapp.dao.QuestionDao;
import com.java_proj.quizapp.dao.QuizDao;
import com.java_proj.quizapp.model.Question;
import com.java_proj.quizapp.model.QuestionWrapper;
import com.java_proj.quizapp.model.Quiz;
import com.java_proj.quizapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numOfQuestions, String title) {
        try {
            List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numOfQuestions);

            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setQuestions(questions);
            quizDao.save(quiz);
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("failure", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer quizId) {
        Optional<Quiz> quiz = quizDao.findById(quizId);
        List<Question> questionsFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();

        for(Question q: questionsFromDB){
            QuestionWrapper qw = new QuestionWrapper(q.getQuestionId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questionsForUser.add(qw);
        }

        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz = quizDao.findById(id).get();
        List<Question> questions = quiz.getQuestions();

        int rightAnsCnt = 0;
        int i = 0;

        for(Response response: responses){
            if(response.getResponse().equals(questions.get(i).getRightAnswer())){
                rightAnsCnt++;
            }
            i++;
        }
        return new ResponseEntity<>(rightAnsCnt, HttpStatus.OK);
    }
}
