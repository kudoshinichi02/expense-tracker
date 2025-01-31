package com.example.expensetracker.services;

import com.example.expensetracker.exceptions.UserException;
import com.example.expensetracker.mappers.UserMapper;
import com.example.expensetracker.models.User;
import com.example.expensetracker.repos.UserRepo;
import com.example.expensetracker.requestDTOs.UserRequestDTO;
import com.example.expensetracker.responseDTOs.UserResponseDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepo.findAll().stream().map(UserMapper::toDTO).toList();
    }

    public UserResponseDTO getUserById(long id) throws UserException {
        Optional<User> userToFindOptional = userRepo.findById(id);

        if (userToFindOptional.isEmpty()) {
            throw new UserException("User with id: " + id + " does not exist");
        }

        return UserMapper.toDTO(userToFindOptional.get());
    }

    public List<UserResponseDTO> getUsersByYearAndMonth(int year, int month){
        return userRepo.findUsersByCreationDateMonthAndYear(year, month).stream().map(UserMapper::toDTO).toList();
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) throws UserException {
        if (userRequestDTO.password() == null)
            throw new UserException("Password must not be empty");
        User user = UserMapper.toEntity(userRequestDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return UserMapper.toDTO(userRepo.save(user));
    }

    public UserResponseDTO updateUser(UserRequestDTO userRequestDTO) throws UserException {
        Optional<User> userToUpdateOptional = userRepo.findById(userRequestDTO.id());

        if (userToUpdateOptional.isEmpty()) {
            throw new UserException("User with name:" + UserMapper.toEntity(userRequestDTO).getUsername() + " does not exist");
        }

        User userToUpdate = userToUpdateOptional.get();

        if (userRequestDTO.email() != null) {
            userToUpdate.setEmail(userRequestDTO.email());
        }

        if (userRequestDTO.password() != null) {
            userToUpdate.setPassword(passwordEncoder.encode(userRequestDTO.password()));
        }

        if (userRequestDTO.username() != null) {
            userToUpdate.setUsername(userRequestDTO.username());
        }

        if (userToUpdate.getRoles() != null) {
            userToUpdate.setRoles(userRequestDTO.roles());
        }

        return UserMapper.toDTO(userRepo.save(userToUpdate));
    }

    public void deleteUser(long id) throws UserException {
        Optional<User> userToDeleteOptional = userRepo.findById(id);
        if (userToDeleteOptional.isEmpty()) {
            throw new UserException("User with id: " + id + " does not exist");
        }
        userRepo.deleteById(userToDeleteOptional.get().getId());

    }

}
