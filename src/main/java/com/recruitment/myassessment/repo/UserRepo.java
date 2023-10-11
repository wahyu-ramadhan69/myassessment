package com.recruitment.myassessment.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recruitment.myassessment.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);

    public Optional<User> findByEmailAndIsActive(String email, Boolean isActive);
}
