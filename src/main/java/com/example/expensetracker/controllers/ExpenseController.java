package com.example.expensetracker.controllers;
import com.example.expensetracker.exceptions.ExpenseException;
import com.example.expensetracker.requestDTOs.ExpenseRequestDTO;
import com.example.expensetracker.responseDTOs.ExpenseResponseDTO;
import com.example.expensetracker.services.ExpenseService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all")
    public ResponseEntity<Page<ExpenseResponseDTO>> getAllExpenses(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
        return new ResponseEntity<>(expenseService.getAllExpenses(page, size) , HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/get-expenses-by-user-name")
    public ResponseEntity<Page<ExpenseResponseDTO>> getExpensesByUsername(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "3") int size){
        return new ResponseEntity<>(expenseService.getAllExpensesByUsername(page, size) , HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/get-total-expenses-by-user-name")
    public ResponseEntity<String> getTotalExpensesByUsername(){
        return new ResponseEntity<>(expenseService.getTotalExpensesByUsername() , HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/get-total-expenses-by-user-name-and-category")
    public ResponseEntity<String> getTotalExpensesByUsernameAndCategory(@RequestParam String category){
        return new ResponseEntity<>(expenseService.getTotalExpensesByUsernameAndCategory(category) , HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/get-expenses-by-year-and-month")
    public ResponseEntity<Page<ExpenseResponseDTO>> getExpensesByYearAndMonth(@RequestParam int year, @RequestParam int month,
                                                                              @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) throws ExpenseException {
        return new ResponseEntity<>(expenseService.getExpensesByYearAndMonth(year, month, page, size) , HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("/add-expense")
    public ResponseEntity<ExpenseResponseDTO> createExpense(@RequestBody ExpenseRequestDTO expenseRequestDTO) throws ExpenseException {
        return new ResponseEntity<>(expenseService.createExpense(expenseRequestDTO) , HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PutMapping("/update-expense")
    public ResponseEntity<ExpenseResponseDTO> updateExpense(@RequestBody ExpenseRequestDTO expenseRequestDTO) throws ExpenseException {
        return new ResponseEntity<>(expenseService.updateExpense(expenseRequestDTO) , HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @DeleteMapping("/delete-expense")
    public ResponseEntity<ExpenseResponseDTO> deleteExpense(@RequestParam long id) throws ExpenseException {
        expenseService.deleteExpense(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
