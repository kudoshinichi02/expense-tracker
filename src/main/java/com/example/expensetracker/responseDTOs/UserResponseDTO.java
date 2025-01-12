package com.example.expensetracker.responseDTOs;

import com.example.expensetracker.enums.ExpenseCategory;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
@Builder
public record UserResponseDTO(Long id, String username, String email, List<Long> expenseIds) {
}
