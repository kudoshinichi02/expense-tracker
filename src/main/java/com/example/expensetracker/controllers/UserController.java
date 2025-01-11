package com.example.expensetracker.controllers;

import com.example.expensetracker.ecxeptions.UserNotFoundException;
import com.example.expensetracker.models.User;
import com.example.expensetracker.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get-all")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/get-user-by-id")
    public User getUserByName(@RequestParam long id) throws UserNotFoundException {
        return userService.findUserById(id);
    }

    @PostMapping("/add-user")
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @PutMapping("/update-user")
    public User updateUser(@RequestBody User user) throws UserNotFoundException {
        return userService.updateUser(user);
    }

    @DeleteMapping("/delete-user")
    public void deleteUser(@RequestParam long id) throws UserNotFoundException {
        userService.deleteUser(id);
    }

}
