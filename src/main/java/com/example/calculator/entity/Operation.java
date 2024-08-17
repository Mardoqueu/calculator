package com.example.calculator.entity;

import com.example.calculator.entity.enums.OperationType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "operations")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OperationType type;

    @Column(nullable = false)
    private Double cost;
}
