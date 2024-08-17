package com.example.calculator.service;

import com.example.calculator.entity.Operation;
import com.example.calculator.entity.enums.OperationType;
import com.example.calculator.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OperationService {

    @Autowired
    private OperationRepository operationRepository;

    public Optional<Operation> findByType(OperationType type) {
        return operationRepository.findAll().stream()
                .filter(op -> op.getType() == type)
                .findFirst();
    }

    public Operation save(Operation operation) {
        return operationRepository.save(operation);
    }

}
