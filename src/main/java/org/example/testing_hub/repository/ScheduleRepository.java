package org.example.testing_hub.repository;

import org.example.testing_hub.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByCourse_Id(Long courseId); // Поиск расписаний по курсу
}
