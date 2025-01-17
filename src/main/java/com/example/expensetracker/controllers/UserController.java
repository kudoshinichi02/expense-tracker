package com.example.expensetracker.controllers;

import com.example.expensetracker.exceptions.UserNotFoundException;
import com.example.expensetracker.requestDTOs.UserRequestDTO;
import com.example.expensetracker.responseDTOs.UserResponseDTO;
import com.example.expensetracker.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers() , HttpStatus.OK);
    }

    @GetMapping("/get-user-by-id")
    public ResponseEntity<UserResponseDTO> getUserByName(@RequestParam long id) throws UserNotFoundException {
        return new ResponseEntity<>(userService.findUserById(id) , HttpStatus.OK);
    }

    @PostMapping("/add-user")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO){
        return new ResponseEntity<>(userService.createUser(userRequestDTO) , HttpStatus.CREATED);
    }

    @PutMapping("/update-user")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserRequestDTO userRequestDTO) throws UserNotFoundException {
        return new ResponseEntity<>(userService.updateUser(userRequestDTO) , HttpStatus.OK);
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<UserResponseDTO> deleteUser(@RequestParam long id) throws UserNotFoundException {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
