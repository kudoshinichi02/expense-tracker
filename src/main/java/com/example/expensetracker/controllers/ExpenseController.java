package com.example.expensetracker.controllers;

import com.example.expensetracker.ecxeptions.ExpenseNotFoundException;
import com.example.expensetracker.ecxeptions.UserNotFoundException;
import com.example.expensetracker.models.Expense;
import com.example.expensetracker.services.ExpenseService;
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
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @GetMapping("/get-expenses-by-user-id")
    public List<Expense> getExpensesById(@RequestParam long id) throws UserNotFoundException {
        return expenseService.getAllExpensesByUserId(id);
    }

    @PostMapping("/add-expense")
    public Expense createExpense(@RequestBody Expense expense) throws ExpenseNotFoundException {
        return expenseService.createExpense(expense);
    }

    @PutMapping("/update-expense")
    public Expense updateExpense(@RequestBody Expense expense) throws ExpenseNotFoundException {
        return expenseService.updateExpense(expense);
    }

    @DeleteMapping("/delete-expense")
    public void deleteExpense(@RequestParam long id) throws ExpenseNotFoundException {
        expenseService.deleteExpense(id);
    }
}
