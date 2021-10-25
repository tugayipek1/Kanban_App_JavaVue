package com.vbt.kanban.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vbt.kanban.entity.Auth;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, Integer>{
    Optional<Auth> findByEmail(String email);
}
