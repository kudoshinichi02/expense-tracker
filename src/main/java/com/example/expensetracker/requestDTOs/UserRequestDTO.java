package com.example.expensetracker.requestDTOs;

import com.example.expensetracker.enums.Role;
import lombok.Builder;

import java.util.Set;

@Builder
public record UserRequestDTO(Long id ,String username, String password , String email , Set<Role> roles) {
}
