package com.example.expensetracker.requestDTOs;

import com.example.expensetracker.enums.ExpenseCategory;
import java.time.LocalDate;

public record ExpenseRequestDTO(Long id, Double amount, ExpenseCategory category, LocalDate date) {
}
