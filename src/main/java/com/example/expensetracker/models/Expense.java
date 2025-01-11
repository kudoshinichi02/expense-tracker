package com.example.expensetracker.models;

import com.example.expensetracker.enums.ExpenseCategory;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    @Enumerated(EnumType.STRING) // Maps to STRING values in the database
    private ExpenseCategory category;
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}