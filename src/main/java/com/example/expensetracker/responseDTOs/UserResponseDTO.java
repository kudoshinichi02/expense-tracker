package com.example.expensetracker.responseDTOs;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
@Builder
public record UserResponseDTO(Long id, String username, String email, LocalDateTime createdAt, LocalDateTime updatedAt, List<Long> expenseIds) {
}
