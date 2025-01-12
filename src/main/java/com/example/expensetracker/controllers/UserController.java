package com.example.expensetracker.controllers;

import com.example.expensetracker.ecxeptions.UserNotFoundException;
import com.example.expensetracker.requestDTOs.UserRequestDTO;
import com.example.expensetracker.responseDTOs.UserResponseDTO;
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
    public List<UserResponseDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/get-user-by-id")
    public UserResponseDTO getUserByName(@RequestParam long id) throws UserNotFoundException {
        return userService.findUserById(id);
    }

    @PostMapping("/add-user")
    public UserResponseDTO createUser(@RequestBody UserRequestDTO userRequestDTO){
        return userService.createUser(userRequestDTO);
    }

    @PutMapping("/update-user")
    public UserResponseDTO updateUser(@RequestBody UserRequestDTO userRequestDTO) throws UserNotFoundException {
        return userService.updateUser(userRequestDTO);
    }

    @DeleteMapping("/delete-user")
    public void deleteUser(@RequestParam long id) throws UserNotFoundException {
        userService.deleteUser(id);
    }

}
