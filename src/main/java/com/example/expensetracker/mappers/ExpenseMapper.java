package com.example.expensetracker.mappers;

import com.example.expensetracker.models.Expense;
import com.example.expensetracker.requestDTOs.ExpenseRequestDTO;
import com.example.expensetracker.responseDTOs.ExpenseResponseDTO;

public class ExpenseMapper {
    public static Expense ToEntity (ExpenseRequestDTO expenseRequestDTO){
        return Expense.builder()
                .id(expenseRequestDTO.id())
                .amount(expenseRequestDTO.amount())
                .category(expenseRequestDTO.category())
                .date(expenseRequestDTO.date())
                .user(expenseRequestDTO.user())
                .build();
    }

    public static ExpenseResponseDTO toDTO (Expense expense){
        return ExpenseResponseDTO.builder()
                .id(expense.getId())
                .amount(expense.getAmount())
                .category(expense.getCategory())
                .date(expense.getDate())
                .userId(expense.getUser().getId())
                .build();
    }
}
