package org.example.testing_hub.controller;

import org.example.testing_hub.entity.QuestionGroup;
import org.example.testing_hub.service.QuestionGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/question-groups")
public class QuestionGroupController {

    @Autowired
    private QuestionGroupService questionGroupService;

    @GetMapping
    public List<QuestionGroup> getAllQuestionGroups() {
        return questionGroupService.getAllGroups();
    }

    @GetMapping("/{id}")
    public QuestionGroup getQuestionGroupById(@PathVariable Long id) {
        return questionGroupService.getGroupById(id);
    }

    @PostMapping
    public QuestionGroup createQuestionGroup(@RequestBody QuestionGroup questionGroup) {
        return questionGroupService.createGroup(questionGroup);
    }

    @PutMapping("/{id}")
    public QuestionGroup updateQuestionGroup(@PathVariable Long id, @RequestBody QuestionGroup updatedGroup) {
        return questionGroupService.updateGroup(id, updatedGroup);
    }

    @DeleteMapping("/{id}")
    public void deleteQuestionGroup(@PathVariable Long id) {
        questionGroupService.deleteGroup(id);
    }
}
