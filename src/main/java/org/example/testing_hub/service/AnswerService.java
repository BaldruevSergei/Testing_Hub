package org.example.testing_hub.service;

import org.example.testing_hub.entity.Answer;
import org.example.testing_hub.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    public List<Answer> getAnswersByQuestion(Long questionId) {
        return answerRepository.findByQuestion_Id(questionId);
    }
    public List<Answer> getAllAnswers() {
        return answerRepository.findAll();
    }

    public Answer getAnswerById(Long id) {
        return answerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Answer not found"));
    }

    public Answer createAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

    public Answer updateAnswer(Long id, Answer updatedAnswer) {
        Answer existingAnswer = answerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Answer not found"));

        existingAnswer.setText(updatedAnswer.getText());
        existingAnswer.setCorrect(updatedAnswer.isCorrect());
        existingAnswer.setExplanation(updatedAnswer.getExplanation());
        existingAnswer.setOrderColumn(updatedAnswer.getOrderColumn());

        return answerRepository.save(existingAnswer);
    }

    public void deleteAnswer(Long id) {
        answerRepository.deleteById(id);
    }
}
