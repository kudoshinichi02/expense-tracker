package com.example.expensetracker.services;

import com.example.expensetracker.enums.ExpenseCategory;
import com.example.expensetracker.exceptions.ExpenseException;
import com.example.expensetracker.exceptions.UserException;
import com.example.expensetracker.mappers.ExpenseMapper;
import com.example.expensetracker.models.Expense;
import com.example.expensetracker.models.User;
import com.example.expensetracker.repos.ExpenseRepo;
import com.example.expensetracker.repos.UserRepo;
import com.example.expensetracker.requestDTOs.ExpenseRequestDTO;
import com.example.expensetracker.responseDTOs.ExpenseResponseDTO;
import org.springframework.security.core.context.SecurityContextHolder;
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



    public List<ExpenseResponseDTO> getAllExpenses() {
        return expenseRepo.findAll().stream().map(ExpenseMapper::toDTO).toList();
    }

    public List<ExpenseResponseDTO> getAllExpensesByUsername() throws UserException {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOptional = userRepo.findByUsername(name);

        if (userOptional.isEmpty()) {
            throw new UserException("User with the name: " + name + " does not exist");
        }

        return userOptional.get().getExpenses().stream().map(ExpenseMapper::toDTO).toList();
    }

    public String getTotalExpensesByUsername() throws UserException {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOptional = userRepo.findByUsername(name);

        if (userOptional.isEmpty()) {
            throw new UserException("User with name: " + name + " does not exist");
        }

        Double total = expenseRepo.calculateTotalExpensesForUser(name);
        if (total == null)
            total = 0.0;

        return "your total expenses is " + total + " $";
    }

    public String getTotalExpensesByUsernameAndCategory(String category) throws UserException {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOptional = userRepo.findByUsername(name);

        if (userOptional.isEmpty()) {
            throw new UserException("User with id: " + name + " does not exist");
        }

        Double total = expenseRepo.calculateExpensesByCategoryForUser(name , ExpenseCategory.valueOf(category.toUpperCase()));

        if (total == null)
            total = 0.0;

        return "your total expenses for the " + category.toUpperCase()  + " category is " + total + " $";

    }

    public ExpenseResponseDTO createExpense(ExpenseRequestDTO expenseRequestDTO) throws ExpenseException {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<User> userOptional = userRepo.findByUsername(name);

        if (userOptional.isEmpty()) {
            throw new ExpenseException("User with name: "  + name +  " does not exist");
        }
        Expense expense = ExpenseMapper.ToEntity(expenseRequestDTO);
        expense.setUser(userOptional.get());
        return ExpenseMapper.toDTO(expenseRepo.save(expense));
    }

    public ExpenseResponseDTO updateExpense(ExpenseRequestDTO expenseRequestDTO) throws ExpenseException {
        Optional<Expense> expenseToUpdateOptional = expenseRepo.findById(expenseRequestDTO.id());
        
        if (expenseToUpdateOptional.isEmpty()) {
            throw new ExpenseException("Expense with the id: " + ExpenseMapper.ToEntity(expenseRequestDTO).getId() + " does not exist");
        }
        Expense expenseToUpdate = expenseToUpdateOptional.get();

        if (expenseRequestDTO.amount() != null){
            expenseToUpdate.setAmount(expenseRequestDTO.amount());
        }

        if (expenseRequestDTO.date() != null){
            expenseToUpdate.setDate(expenseRequestDTO.date());
        }

        if (expenseRequestDTO.category() != null){
            expenseToUpdate.setCategory(expenseRequestDTO.category());
        }

        return ExpenseMapper.toDTO(expenseRepo.save(expenseToUpdate));
    }

    public void deleteExpense(long id) throws ExpenseException {
        Optional<Expense> expenseToDeleteOptional = expenseRepo.findById(id);

        if (expenseToDeleteOptional.isEmpty()) {
            throw new ExpenseException("Expense with the id: " + id + " does not exist");
        }
        expenseRepo.delete(expenseToDeleteOptional.get());
    }
}
