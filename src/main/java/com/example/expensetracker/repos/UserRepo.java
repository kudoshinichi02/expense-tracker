package com.example.expensetracker.repos;

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
public interface UserRepo extends JpaRepository<User, Long> {
   Page<User> findAll(Pageable pageable);
   Optional <User> findByUsername(String username);
   Optional<User> findById(long userId);
   @Query("SELECT u FROM User u WHERE YEAR(u.createdAt) = :year AND MONTH(u.createdAt) = :month")
   Page<User> findUsersByCreationDateMonthAndYear(Pageable pageable, int year, int month);
}
