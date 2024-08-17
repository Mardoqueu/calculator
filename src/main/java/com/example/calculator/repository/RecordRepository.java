package com.example.calculator.repository;

import com.example.calculator.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findByUserIdAndDeletedFalse(Long userId);
}
