package com.vbt.kanban.auth.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.vbt.kanban.account.service.AccountService;
import com.vbt.kanban.auth.dto.AuthDto;
import com.vbt.kanban.auth.dto.NewPasswordDto;
import com.vbt.kanban.auth.dto.RegisterRequest;
import com.vbt.kanban.entity.Account;
import com.vbt.kanban.entity.Auth;
import com.vbt.kanban.entity.ResetPasswordToken;
import com.vbt.kanban.exception.ResetPasswordRequestNotFoundException;
import com.vbt.kanban.exception.ResetPasswordTokenIsNotValidException;
import com.vbt.kanban.exception.UserNotFoundException;
import com.vbt.kanban.auth.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.mail.MessagingException;

@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
	private AuthRepository authRepository;

	@Autowired
	private AccountService accountService;

	@Autowired
	private ResetPasswordService resetPasswordService;

	private PasswordEncoder encoder = new BCryptPasswordEncoder();

	@Override
	public AuthDto register(RegisterRequest request) throws Exception {

		Optional<Auth> control = authRepository.findByEmail(request.getEmail());

		if (control.isPresent()) {
			throw new Exception("Email address is already in use");
		}

		Optional<Account> controlAccount = accountService.findByNickName(request.getNickName());

		if (controlAccount.isPresent()) {
			throw new Exception("Nick name is already in use");
		}

		Auth user = new Auth();
		user.setEmail(request.getEmail());
		user.setPassword(encoder.encode(request.getPassword()));

		Auth dbAuth = authRepository.save(user);
		Account account = new Account();
		account.setName(request.getName());
		account.setAuth(dbAuth);
		account.setNickName(request.getNickName());
		accountService.save(account);

		AuthDto dto = new AuthDto(user.getId(), user.getEmail());

		return dto;
	}

	@Override
	public Optional<Auth> findByEmail(String email) {
		return authRepository.findByEmail(email);
	}

	@Override
	public void createResetPasswordRequest(@PathVariable("email") String email) throws UserNotFoundException, MessagingException {
		Auth userAuth = authRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Belirtilen email bilgisine sahip hesap bulunadı"));

		resetPasswordService.createResetPasswordToken(userAuth);

	}

	@Override
	public void resetPassword(NewPasswordDto dto)
			throws ResetPasswordRequestNotFoundException, ResetPasswordTokenIsNotValidException {
		ResetPasswordToken resetPasswordToken = resetPasswordService.findByToken(dto.getResetPasswordToken())
				.orElseThrow(() -> new ResetPasswordRequestNotFoundException("Şifre Sıfırlama talebi bulunamadı"));

		if (resetPasswordToken.isExpired()) {
			throw new ResetPasswordTokenIsNotValidException(
					"Şifre sıfırlama talebinizin geçerlilik süresi dolmuş! Lütfen yeni bir tane oluşturmayı deneyin.");
		} else if (resetPasswordToken.isUsed()) {
			throw new ResetPasswordTokenIsNotValidException("Bu şifre sıfırlama bağlantısı zaten kullanıldı");
		}

		Auth userAuth = resetPasswordToken.getAuth();
		userAuth.setPassword(encoder.encode(dto.getPassword()));

		authRepository.save(userAuth);

		resetPasswordToken.setUsed(true);
		resetPasswordToken.setUsingDateTime(LocalDateTime.now());
		resetPasswordService.update(resetPasswordToken);
	}
}
