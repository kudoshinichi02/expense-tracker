package com.example.expensetracker.repos;

import com.example.expensetracker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
   Optional <User> findByUsername(String username);
   Optional<User> findById(long userId);
   @Query("SELECT u FROM User u WHERE YEAR(u.createdAt) = :year AND MONTH(u.createdAt) = :month")
   List<User> findUsersByCreationDateMonthAndYear(int year, int month);
}
