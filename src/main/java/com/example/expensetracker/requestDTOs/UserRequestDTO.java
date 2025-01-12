package com.example.expensetracker.requestDTOs;

import lombok.Builder;

@Builder
public record UserRequestDTO(Long id ,String username, String password , String email) {
}
