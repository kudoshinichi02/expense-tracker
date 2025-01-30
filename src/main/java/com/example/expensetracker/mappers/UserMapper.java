package com.example.expensetracker.mappers;

import com.example.expensetracker.models.Expense;
import com.example.expensetracker.models.User;
import com.example.expensetracker.requestDTOs.UserRequestDTO;
import com.example.expensetracker.responseDTOs.UserResponseDTO;

import java.util.List;
import java.util.stream.Stream;

public class UserMapper {
    public static UserResponseDTO toDTO (User user){
        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .expenseIds(Stream.ofNullable(user.getExpenses())
                        .flatMap(List::stream)
                        .map(Expense::getId)
                        .toList())

                .build();
    }

    public static User toEntity (UserRequestDTO userRequestDTO){
        return User.builder()
                .id(userRequestDTO.id())
                .username(userRequestDTO.username())
                .password(userRequestDTO.password())
                .email(userRequestDTO.email())
                .roles(userRequestDTO.roles())
                .build();
    }
}
