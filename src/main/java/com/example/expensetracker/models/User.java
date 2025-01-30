package com.example.expensetracker.models;

import com.example.expensetracker.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "\"users\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Expense> expenses;
}
