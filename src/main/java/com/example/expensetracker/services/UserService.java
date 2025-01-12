package com.example.expensetracker.services;

import com.example.expensetracker.ecxeptions.UserNotFoundException;
import com.example.expensetracker.mappers.UserMapper;
import com.example.expensetracker.models.User;
import com.example.expensetracker.repos.UserRepo;
import com.example.expensetracker.requestDTOs.UserRequestDTO;
import com.example.expensetracker.responseDTOs.UserResponseDTO;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepo.findAll().stream().map(UserMapper::toDTO).toList();
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        return UserMapper.toDTO(userRepo.save(UserMapper.toEntity(userRequestDTO)));
    }

    public UserResponseDTO updateUser(UserRequestDTO userRequestDTO) throws UserNotFoundException {
        Optional<User> userToUpdateOptional = userRepo.findById(UserMapper.toEntity(userRequestDTO).getId());

        if (userToUpdateOptional.isEmpty()) {
            throw new UserNotFoundException("user with name:" + UserMapper.toEntity(userRequestDTO).getUsername() + " does not exist");
        }

        User userToUpdate = userToUpdateOptional.get();

        if (userToUpdate.getEmail() != null) {
            userToUpdate.setEmail(UserMapper.toEntity(userRequestDTO).getEmail());
        }

        if (userToUpdate.getPassword() != null) {
            userToUpdate.setPassword(UserMapper.toEntity(userRequestDTO).getPassword());
        }

        if (userToUpdate.getUsername() != null) {
            userToUpdate.setUsername(UserMapper.toEntity(userRequestDTO).getUsername());
        }

        return UserMapper.toDTO(userRepo.save(userToUpdate));
    }

    public void deleteUser(long id) throws UserNotFoundException {
        Optional<User> userToDeleteOptional = userRepo.findById(id);
        if (userToDeleteOptional.isEmpty()) {
            throw new UserNotFoundException("user with id: " + id + " does not exist");
        }
        userRepo.deleteById(userToDeleteOptional.get().getId());

    }

    public UserResponseDTO findUserById(long id) throws UserNotFoundException {
        Optional<User> userToFindOptional = userRepo.findById(id);

        if (userToFindOptional.isEmpty()) {
            throw new UserNotFoundException("user with id: " + id + " does not exist");
        }

        return UserMapper.toDTO(userToFindOptional.get());
    }
}
