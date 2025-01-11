package com.example.expensetracker.services;

import com.example.expensetracker.ecxeptions.UserNotFoundException;
import com.example.expensetracker.models.User;
import com.example.expensetracker.repos.UserRepo;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User createUser(User user) {
        return userRepo.save(user);
    }

    public User updateUser(User user) throws UserNotFoundException {
        Optional<User> userToUpdateOptional = userRepo.findById(user.getId());

        if (userToUpdateOptional.isEmpty()) {
            throw new UserNotFoundException("user with name:" + user.getUsername() + " does not exist");
        }

        User userToUpdate = userToUpdateOptional.get();

        if (userToUpdate.getEmail() != null) {
            userToUpdate.setEmail(user.getEmail());
        }

        if (userToUpdate.getPassword() != null) {
            userToUpdate.setPassword(user.getPassword());
        }

        if (userToUpdate.getUsername() != null) {
            userToUpdate.setUsername(user.getUsername());
        }

        return userRepo.save(userToUpdate);
    }

    public void deleteUser(long id) throws UserNotFoundException {
        Optional<User> userToDeleteOptional = userRepo.findById(id);
        if (userToDeleteOptional.isEmpty()) {
            throw new UserNotFoundException("user with id: " + id + " does not exist");
        }
        userRepo.deleteById(userToDeleteOptional.get().getId());

    }

    public User findUserById(long id) throws UserNotFoundException {
        Optional<User> userToFindOptional = userRepo.findById(id);

        if (userToFindOptional.isEmpty()) {
            throw new UserNotFoundException("user with id: " + id + " does not exist");
        }

        return userToFindOptional.get();
    }
}
