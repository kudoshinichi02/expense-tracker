package com.example.expensetracker.requestDTOs;

import com.example.expensetracker.enums.ExpenseCategory;


public record ExpenseRequestDTO(Long id, Double amount, ExpenseCategory category) {
}
