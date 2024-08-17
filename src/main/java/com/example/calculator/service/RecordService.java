package com.example.calculator.service;

import com.example.calculator.entity.Record;
import com.example.calculator.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordService {

    @Autowired
    private RecordRepository recordRepository;

    public List<Record> findByUserId(Long userId) {
        return recordRepository.findByUserIdAndDeletedFalse(userId);
    }

    public Record save(Record record) {
        return recordRepository.save(record);
    }

    public void softDelete(Long recordId) {
        Record record = recordRepository.findById(recordId).orElseThrow();
        record.setDeleted(true);
        recordRepository.save(record);
    }

    // Additional business logic methods as needed
}
