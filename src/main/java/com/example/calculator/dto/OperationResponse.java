package com.example.calculator.dto;

import lombok.Data;

@Data
public class OperationResponse {
    private String message;
    private Double result;

    public OperationResponse(String message, Double result) {
        this.message = message;
        this.result = result;
    }
}
