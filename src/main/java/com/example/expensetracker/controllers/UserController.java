package com.example.expensetracker.controllers;

import com.example.expensetracker.exceptions.UserException;
import com.example.expensetracker.requestDTOs.UserRequestDTO;
import com.example.expensetracker.responseDTOs.UserResponseDTO;
import com.example.expensetracker.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all")
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
        return new ResponseEntity<>(userService.getAllUsers(page, size) , HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/get-user-by-id")
    public ResponseEntity<UserResponseDTO> getUserByName(@RequestParam long id) throws UserException {
        return new ResponseEntity<>(userService.getUserById(id) , HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/get-users-by-date")
    public ResponseEntity<Page<UserResponseDTO>> getUsersByDate(@RequestParam int year ,@RequestParam int month,
    @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size){
        return new ResponseEntity<>(userService.getUsersByYearAndMonth(page, size, year, month) , HttpStatus.OK);
    }

    @PostMapping("/add-user")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) throws UserException {
        return new ResponseEntity<>(userService.createUser(userRequestDTO) , HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PutMapping("/update-user")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserRequestDTO userRequestDTO) throws UserException {
        return new ResponseEntity<>(userService.updateUser(userRequestDTO) , HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-user")
    public ResponseEntity<UserResponseDTO> deleteUser(@RequestParam long id) throws UserException {

        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
