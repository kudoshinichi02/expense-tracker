package com.example.expensetracker.repos;
import com.example.expensetracker.enums.ExpenseCategory;
import com.example.expensetracker.models.Expense;
import com.example.expensetracker.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepo extends JpaRepository<Expense, Long> {

    Page<Expense> findAllBy(Pageable pageable);
    Page<Expense> findByUser(User user, Pageable pageable);


    @Query("SELECT (SUM(e.amount) , 0) FROM Expense e WHERE e.user.username = :username")
    Double calculateTotalExpensesForUser(String username);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.user.username = :username AND e.category = :category")
    Double calculateExpensesByCategoryForUser(String username, ExpenseCategory category);

    @Query("SELECT e FROM Expense e WHERE e.user.username = :username AND " +
            "YEAR(e.createdAt) = :year AND MONTH(e.createdAt) = :month")
    Page<Expense> findByUsernameAndMonthAndYear(String username, int year, int month, Pageable pageable);
}
