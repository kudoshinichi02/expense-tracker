package com.example.expensetracker.services;

import com.example.expensetracker.exceptions.ExpenseNotFoundException;
import com.example.expensetracker.exceptions.UserNotFoundException;
import com.example.expensetracker.mappers.ExpenseMapper;
import com.example.expensetracker.models.Expense;
import com.example.expensetracker.models.User;
import com.example.expensetracker.repos.ExpenseRepo;
import com.example.expensetracker.repos.UserRepo;
import com.example.expensetracker.requestDTOs.ExpenseRequestDTO;
import com.example.expensetracker.responseDTOs.ExpenseResponseDTO;
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

    public List<ExpenseResponseDTO> getAllExpensesByUserId(long id) throws UserNotFoundException {
        Optional<User> userOptional = userRepo.findById(id);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User with id: " + id + " does not exist");
        }

        expenseRepo.findByUserId(userOptional.get().getId());
        return userOptional.get().getExpenses().stream().map(ExpenseMapper::toDTO).toList();
    }

    public ExpenseResponseDTO createExpense(ExpenseRequestDTO expenseRequestDTO) throws ExpenseNotFoundException {
        Optional<User> userOptional = userRepo.findById(ExpenseMapper.ToEntity(expenseRequestDTO).getUser().getId());

        if (userOptional.isEmpty()) {
            throw new ExpenseNotFoundException("User with id: "  + ExpenseMapper.ToEntity(expenseRequestDTO).getUser().getId() +  " does not exist");
        }
        ExpenseMapper.ToEntity(expenseRequestDTO).setUser(userOptional.get());
        return ExpenseMapper.toDTO(expenseRepo.save(ExpenseMapper.ToEntity(expenseRequestDTO)));
    }

    public ExpenseResponseDTO updateExpense(ExpenseRequestDTO expenseRequestDTO) throws ExpenseNotFoundException {
        Optional<Expense> expenseToUpdateOptional = expenseRepo.findById(ExpenseMapper.ToEntity(expenseRequestDTO).getId());
        
        if (expenseToUpdateOptional.isEmpty()) {
            throw new ExpenseNotFoundException("Expense with the id: " + ExpenseMapper.ToEntity(expenseRequestDTO).getId() + " does not exist");
        }
        Expense expenseToUpdate = expenseToUpdateOptional.get();

        if (expenseToUpdate.getAmount() != null){
            expenseToUpdate.setAmount(ExpenseMapper.ToEntity(expenseRequestDTO).getAmount());
        }

        if (expenseToUpdate.getDate() != null){
            expenseToUpdate.setDate(ExpenseMapper.ToEntity(expenseRequestDTO).getDate());
        }

        if (expenseToUpdate.getCategory() != null){
            expenseToUpdate.setCategory(ExpenseMapper.ToEntity(expenseRequestDTO).getCategory());
        }

        return ExpenseMapper.toDTO(expenseRepo.save(expenseToUpdate));
    }

    public void deleteExpense(long id) throws ExpenseNotFoundException {
        Optional<Expense> expenseToDeleteOptional = expenseRepo.findById(id);

        if (expenseToDeleteOptional.isEmpty()) {
            throw new ExpenseNotFoundException("Expense with the id: " + id + " does not exist");
        }
        expenseRepo.delete(expenseToDeleteOptional.get());
    }


}
