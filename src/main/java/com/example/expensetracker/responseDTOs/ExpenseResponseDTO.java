package com.example.expensetracker.responseDTOs;

import com.example.expensetracker.enums.ExpenseCategory;
import com.example.expensetracker.models.User;
import lombok.Builder;

import java.time.LocalDate;

@Builder

public record ExpenseResponseDTO(Long id , Double amount, ExpenseCategory category, LocalDate date, Long userId) {
}
