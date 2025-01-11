package com.example.expensetracker.services;

import com.example.expensetracker.ecxeptions.ExpenseNotFoundException;
import com.example.expensetracker.ecxeptions.UserNotFoundException;
import com.example.expensetracker.models.Expense;
import com.example.expensetracker.models.User;
import com.example.expensetracker.repos.ExpenseRepo;
import com.example.expensetracker.repos.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    final ExpenseRepo expenseRepo;
    final UserRepo userRepo;

    public ExpenseService(ExpenseRepo expenseRepo, UserRepo userRepo) {
        this.expenseRepo = expenseRepo;
        this.userRepo = userRepo;
    }

    public List<Expense> getAllExpenses() {
        return expenseRepo.findAll();
    }

    public Expense createExpense(Expense expense) throws ExpenseNotFoundException {
        Optional<User> userOptional = userRepo.findById(expense.getUser().getId());

        if (userOptional.isEmpty()) {
            throw new ExpenseNotFoundException("user with id:"  + expense.getUser().getId() +  "does not exist");
        }
        expense.setUser(userOptional.get());
        return expenseRepo.save(expense);
    }

    public Expense updateExpense(Expense expense) throws ExpenseNotFoundException {
        Optional<Expense> expenseToUpdateOptional = expenseRepo.findById(expense.getId());
        
        if (expenseToUpdateOptional.isEmpty()) {
            throw new ExpenseNotFoundException("expenes with the id:" + expense.getId() + " does not exist");
        }
        Expense expenseToUpdate = expenseToUpdateOptional.get();

        if (expenseToUpdate.getAmount() != null){
            expenseToUpdate.setAmount(expense.getAmount());
        }

        if (expenseToUpdate.getDate() != null){
            expenseToUpdate.setDate(expense.getDate());
        }

        if (expenseToUpdate.getCategory() != null){
            expenseToUpdate.setCategory(expense.getCategory());
        }

        return expenseRepo.save(expenseToUpdate);
    }

    public void deleteExpense(long id) throws ExpenseNotFoundException {
        Optional<Expense> expenseToDeleteOptional = expenseRepo.findById(id);

        if (expenseToDeleteOptional.isEmpty()) {
            throw new ExpenseNotFoundException("expenes with the id:" + id + " does not exist");
        }
        expenseRepo.delete(expenseToDeleteOptional.get());
    }

    public List<Expense> getAllExpensesByUserId(long id) throws UserNotFoundException {
        Optional<User> userOptional = userRepo.findById(id);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("user with id: " + id + " does not exist");
        }

         expenseRepo.findByUserId(userOptional.get().getId());
        return userOptional.get().getExpenses();
    }
}
