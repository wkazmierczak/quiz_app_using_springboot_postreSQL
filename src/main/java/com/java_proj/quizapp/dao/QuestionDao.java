package com.java_proj.quizapp.dao;

import com.java_proj.quizapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {

    List<Question> findByCategory(String category);

    @Query(value = "SELECT * FROM question q where q.category=:category order by random() limit :numOfQuestions", nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(String category, int numOfQuestions);
}
