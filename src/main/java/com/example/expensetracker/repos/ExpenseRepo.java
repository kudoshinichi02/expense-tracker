package com.example.expensetracker.repos;
import com.example.expensetracker.enums.ExpenseCategory;
import com.example.expensetracker.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ExpenseRepo extends JpaRepository<Expense, Long> {
    List<Expense> findByUserId(Long user_id);

    @Query("SELECT (SUM(e.amount) , 0) FROM Expense e WHERE e.user.id = :userId")
    Double calculateTotalExpensesForUser(Long userId);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.user.id = :userId AND e.category = :category")
    Double calculateExpensesByCategoryForUser(Long userId, ExpenseCategory category);
}
