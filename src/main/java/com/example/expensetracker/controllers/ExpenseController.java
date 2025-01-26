package com.example.expensetracker.controllers;
import com.example.expensetracker.exceptions.ExpenseException;
import com.example.expensetracker.exceptions.UserException;
import com.example.expensetracker.requestDTOs.ExpenseRequestDTO;
import com.example.expensetracker.responseDTOs.ExpenseResponseDTO;
import com.example.expensetracker.services.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ExpenseResponseDTO>> getAllExpenses() {
        return new ResponseEntity<>(expenseService.getAllExpenses() , HttpStatus.OK);
    }

    @GetMapping("/get-expenses-by-user-name")
    public ResponseEntity<List<ExpenseResponseDTO>> getExpensesByUsername() throws UserException {
        return new ResponseEntity<>(expenseService.getAllExpensesByUsername() , HttpStatus.OK);
    }

    @GetMapping("/get-total-expenses-by-user-name")
    public ResponseEntity<String> getTotalExpensesByUsername() throws UserException {
        return new ResponseEntity<>(expenseService.getTotalExpensesByUsername() , HttpStatus.OK);
    }

    @GetMapping("/get-total-expenses-by-user-name-and-category")
    public ResponseEntity<String> getTotalExpensesByUsernameAndCategory(@RequestParam String category) throws UserException {
        return new ResponseEntity<>(expenseService.getTotalExpensesByUsernameAndCategory(category) , HttpStatus.OK);
    }

    @PostMapping("/add-expense")
    public ResponseEntity<ExpenseResponseDTO> createExpense(@RequestBody ExpenseRequestDTO expenseRequestDTO) throws ExpenseException {
        return new ResponseEntity<>(expenseService.createExpense(expenseRequestDTO) , HttpStatus.CREATED);
    }

    @PutMapping("/update-expense")
    public ResponseEntity<ExpenseResponseDTO> updateExpense(@RequestBody ExpenseRequestDTO expenseRequestDTO) throws ExpenseException {
        return new ResponseEntity<>(expenseService.updateExpense(expenseRequestDTO) , HttpStatus.OK);
    }

    @DeleteMapping("/delete-expense")
    public ResponseEntity<ExpenseResponseDTO> deleteExpense(@RequestParam long id) throws ExpenseException {
        expenseService.deleteExpense(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
