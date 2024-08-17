package com.example.calculator.controller;

import com.example.calculator.dto.OperationRequest;
import com.example.calculator.dto.OperationResponse;
import com.example.calculator.entity.Operation;
import com.example.calculator.entity.Record;
import com.example.calculator.entity.User;
import com.example.calculator.entity.enums.OperationType;
import com.example.calculator.service.OperationService;
import com.example.calculator.service.RecordService;
import com.example.calculator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/operations")
public class OperationController {

    @Autowired
    private OperationService operationService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private UserService userService;

    @PostMapping("/calculate")
    public ResponseEntity<OperationResponse> performOperation(@RequestBody OperationRequest request) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());

        Operation operation = operationService.findByType(request.getOperationType()).orElseThrow(() -> new RuntimeException("Operation not found"));

        if (user.getBalance() < operation.getCost()) {
            return ResponseEntity.badRequest().body(new OperationResponse("Insufficient balance", null));
        }

        double result = 0;
        switch (operation.getType()) {
            case ADDITION:
                result = request.getAmount1() + request.getAmount2();
                break;
            case SUBTRACTION:
                result = request.getAmount1() - request.getAmount2();
                break;
            case MULTIPLICATION:
                result = request.getAmount1() * request.getAmount2();
                break;
            case DIVISION:
                if (request.getAmount2() == 0) {
                    return ResponseEntity.badRequest().body(new OperationResponse("Division by zero", null));
                }
                result = request.getAmount1() / request.getAmount2();
                break;
            case SQUARE_ROOT:
                if (request.getAmount1() < 0) {
                    return ResponseEntity.badRequest().body(new OperationResponse("Square root of negative number", null));
                }
                result = Math.sqrt(request.getAmount1());
                break;
            case RANDOM_STRING:
                // Handle random string generation (possibly via third-party API)
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + operation.getType());
        }

        user.setBalance(user.getBalance() - operation.getCost());
        userService.save(user);

        Record record = new Record();
        record.setOperation(operation);
        record.setUser(user);
        record.setAmount(result);
        record.setUserBalance(user.getBalance());
        record.setOperationResponse(String.valueOf(result));
        record.setDate(LocalDateTime.now());
        recordService.save(record);

        return ResponseEntity.ok(new OperationResponse("Operation successful", result));
    }

    // Additional endpoints as required
}
