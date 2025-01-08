package org.example.testing_hub.repository;

import org.example.testing_hub.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findByUser_Id(Long userId); // Поиск логов по пользователю
}
