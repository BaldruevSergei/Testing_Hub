package org.example.testing_hub.service;

import org.example.testing_hub.entity.QuestionGroup;
import org.example.testing_hub.repository.QuestionGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionGroupService {

    @Autowired
    private QuestionGroupRepository questionGroupRepository;

    public List<QuestionGroup> getAllGroups() {
        return questionGroupRepository.findAll();
    }

    public List<QuestionGroup> getGroupsByTestId(Long testId) {
        return questionGroupRepository.findByTest_Id(testId);
    }

    public QuestionGroup getGroupById(Long id) {
        return questionGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("QuestionGroup not found"));
    }

    public QuestionGroup createGroup(QuestionGroup group) {
        return questionGroupRepository.save(group);
    }

    public QuestionGroup updateGroup(Long id, QuestionGroup updatedGroup) {
        QuestionGroup existingGroup = questionGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("QuestionGroup not found"));

        existingGroup.setName(updatedGroup.getName());
        existingGroup.setQuestions(updatedGroup.getQuestions());

        return questionGroupRepository.save(existingGroup);
    }

    public void deleteGroup(Long id) {
        questionGroupRepository.deleteById(id);
    }
}
