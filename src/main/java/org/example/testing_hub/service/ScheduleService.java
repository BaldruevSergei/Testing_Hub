package org.example.testing_hub.service;

import org.example.testing_hub.entity.Schedule;
import org.example.testing_hub.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public Schedule getScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
    }

    public List<Schedule> getSchedulesByCourse(Long courseId) {
        return scheduleRepository.findByCourse_Id(courseId);
    }

    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }
    public Schedule updateSchedule(Long id, Schedule updatedSchedule) {
        // Найти расписание по ID
        Schedule existingSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with id " + id));

        // Обновить поля расписания
        existingSchedule.setName(updatedSchedule.getName());
        existingSchedule.setStartDate(updatedSchedule.getStartDate());
        existingSchedule.setEndDate(updatedSchedule.getEndDate());
        existingSchedule.setDescription(updatedSchedule.getDescription());

        // Сохранить изменения
        return scheduleRepository.save(existingSchedule);
    }

}
