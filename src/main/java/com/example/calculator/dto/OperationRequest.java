package com.example.calculator.dto;

import com.example.calculator.entity.enums.OperationType;
import lombok.Data;

@Data
public class OperationRequest {
    private OperationType operationType;
    private double amount1;
    private double amount2; // Second amount if required (for non-unary operations)
}
