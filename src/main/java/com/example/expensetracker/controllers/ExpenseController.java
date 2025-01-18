package com.example.expensetracker.controllers;

import com.example.expensetracker.exceptions.ExpenseNotFoundException;
import com.example.expensetracker.exceptions.UserNotFoundException;
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

    @GetMapping("/get-expenses-by-user-id")
    public ResponseEntity<List<ExpenseResponseDTO>> getExpensesById(@RequestParam long id) throws UserNotFoundException {
        return new ResponseEntity<>(expenseService.getAllExpensesByUserId(id) , HttpStatus.OK);
    }

    @GetMapping("/get-total-expenses-by-user-id")
    public ResponseEntity<String> getTotalExpensesByUserId(@RequestParam long id) throws UserNotFoundException {
        return new ResponseEntity<>(expenseService.getTotalExpensesByUserId(id) , HttpStatus.OK);
    }

    @GetMapping("/get-total-expenses-by-user-id-and-category")
    public ResponseEntity<String> getTotalExpensesByUserIdAndCategory(@RequestParam long id , @RequestParam String category) throws UserNotFoundException {
        return new ResponseEntity<>(expenseService.getTotalExpensesByUserIdAndCategory(id , category) , HttpStatus.OK);
    }

    @PostMapping("/add-expense")
    public ResponseEntity<ExpenseResponseDTO> createExpense(@RequestBody ExpenseRequestDTO expenseRequestDTO) throws ExpenseNotFoundException {
        return new ResponseEntity<>(expenseService.createExpense(expenseRequestDTO) , HttpStatus.CREATED);
    }

    @PutMapping("/update-expense")
    public ResponseEntity<ExpenseResponseDTO> updateExpense(@RequestBody ExpenseRequestDTO expenseRequestDTO) throws ExpenseNotFoundException {
        return new ResponseEntity<>(expenseService.updateExpense(expenseRequestDTO) , HttpStatus.OK);
    }

    @DeleteMapping("/delete-expense")
    public ResponseEntity<ExpenseResponseDTO> deleteExpense(@RequestParam long id) throws ExpenseNotFoundException {
        expenseService.deleteExpense(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
