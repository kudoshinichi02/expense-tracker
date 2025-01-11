package com.example.expensetracker.repos;
import com.example.expensetracker.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ExpenseRepo extends JpaRepository<Expense, Long> {
    List<Expense> findByUserId(Long user_id);

}
