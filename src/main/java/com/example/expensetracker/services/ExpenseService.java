package com.example.expensetracker.services;

import com.example.expensetracker.enums.ExpenseCategory;
import com.example.expensetracker.exceptions.ExpenseException;
import com.example.expensetracker.mappers.ExpenseMapper;
import com.example.expensetracker.mappers.UserMapper;
import com.example.expensetracker.models.Expense;
import com.example.expensetracker.models.User;
import com.example.expensetracker.repos.ExpenseRepo;
import com.example.expensetracker.repos.UserRepo;
import com.example.expensetracker.requestDTOs.ExpenseRequestDTO;
import com.example.expensetracker.responseDTOs.ExpenseResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenseService {
    final ExpenseRepo expenseRepo;
    final UserRepo userRepo;


    public ExpenseService(ExpenseRepo expenseRepo, UserRepo userRepo) {
        this.expenseRepo = expenseRepo;
        this.userRepo = userRepo;
    }


    public Page<ExpenseResponseDTO> getAllExpenses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return expenseRepo.findAllBy(pageable).map(ExpenseMapper::toDTO);
    }

    public Page<ExpenseResponseDTO> getAllExpensesByUsername(int page, int size) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Pageable pageable = PageRequest.of(page, size);
        return expenseRepo.findByUser(userRepo.findByUsername(name).get(), pageable).map(ExpenseMapper::toDTO);
    }

    public String getTotalExpensesByUsername(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Double total = expenseRepo.calculateTotalExpensesForUser(name);
        if (total == null)
            total = 0.0;
        return "your total expenses is " + total + " $";
    }

    public String getTotalExpensesByUsernameAndCategory(String category){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        Double total = expenseRepo.calculateExpensesByCategoryForUser(name , ExpenseCategory.valueOf(category.toUpperCase()));
        if (total == null)
            total = 0.0;
        return "your total expenses for the " + category.toUpperCase()  + " category is " + total + " $";
    }

    public Page<ExpenseResponseDTO> getExpensesByYearAndMonth(int year, int month , int page, int size) throws ExpenseException{
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Pageable pageable = PageRequest.of(page, size);
        return expenseRepo.findByUsernameAndMonthAndYear(name, year, month, pageable).map(ExpenseMapper::toDTO);
    }

    public ExpenseResponseDTO createExpense(ExpenseRequestDTO expenseRequestDTO) throws ExpenseException {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByUsername(name).get();
        Expense expense = ExpenseMapper.ToEntity(expenseRequestDTO);
        expense.setUser(user);
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
