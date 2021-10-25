package com.vbt.kanban.auth.service;

import com.vbt.kanban.auth.dto.AuthDto;
import com.vbt.kanban.auth.dto.NewPasswordDto;
import com.vbt.kanban.auth.dto.RegisterRequest;
import com.vbt.kanban.entity.Auth;
import com.vbt.kanban.exception.ResetPasswordRequestNotFoundException;
import com.vbt.kanban.exception.ResetPasswordTokenIsNotValidException;
import com.vbt.kanban.exception.UserNotFoundException;

import javax.mail.MessagingException;
import java.util.Optional;

public interface AuthService {
	AuthDto register(RegisterRequest request) throws Exception;

	Optional<Auth> findByEmail(String email);

	void createResetPasswordRequest(String email) throws UserNotFoundException, MessagingException;

	void resetPassword(NewPasswordDto dto)
			throws ResetPasswordRequestNotFoundException, ResetPasswordTokenIsNotValidException;
}
