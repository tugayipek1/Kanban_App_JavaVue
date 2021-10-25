package com.vbt.kanban.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vbt.kanban.entity.ResetPasswordToken;

@Repository
public interface ResetPasswordRepository extends JpaRepository<ResetPasswordToken, Long> {
	Optional<ResetPasswordToken> findByToken(String token);
}
