package com.recruitment.myassessment.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recruitment.myassessment.model.Usr;

public interface UserRepo extends JpaRepository<Usr, Long> {

    public Optional<Usr> findByUserNameOrEmail(String userName, String email);

    public Optional<Usr> findByUserName(String userName);

    public Optional<Usr> findByUserNameAndIsActive(String userName, Boolean isActive);
}
