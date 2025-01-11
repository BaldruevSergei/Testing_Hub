package org.example.testing_hub.service;

import org.example.testing_hub.entity.Teacher;
import org.example.testing_hub.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    // Создание нового учителя
    @Transactional
    public Teacher createTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    // Получение учителя по ID
    public Optional<Teacher> getTeacherById(Long id) {
        return teacherRepository.findById(id);
    }

    // Получение всех учителей
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    // Обновление информации об учителе
    @Transactional
    public Optional<Teacher> updateTeacher(Long id, Teacher updatedTeacher) {
        return teacherRepository.findById(id).map(existingTeacher -> {
            existingTeacher.setFirstName(updatedTeacher.getFirstName());
            existingTeacher.setLastName(updatedTeacher.getLastName());
            existingTeacher.setEmail(updatedTeacher.getEmail());
            existingTeacher.setSubject(updatedTeacher.getSubject());
            existingTeacher.setPosition(updatedTeacher.getPosition());
            return teacherRepository.save(existingTeacher);
        });
    }

    // Удаление учителя по ID
    @Transactional
    public boolean deleteTeacher(Long id) {
        if (teacherRepository.existsById(id)) {
            teacherRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Получение учителей по предмету
    public List<Teacher> findTeachersBySubject(String subject) {
        return teacherRepository.findBySubject(subject);
    }

    // Получение учителей по должности
    public List<Teacher> findTeachersByPosition(String position) {
        return teacherRepository.findByPosition(position);
    }

    // Получение учителя по email
    public Optional<Teacher> findTeacherByEmail(String email) {
        return teacherRepository.findByEmail(email);
    }


}
