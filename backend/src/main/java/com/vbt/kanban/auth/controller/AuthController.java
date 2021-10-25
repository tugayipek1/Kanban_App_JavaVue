package com.vbt.kanban.auth.controller;

import com.vbt.kanban.account.service.AccountService;
import com.vbt.kanban.auth.dto.AuthDto;
import com.vbt.kanban.auth.dto.RegisterRequest;
import com.vbt.kanban.auth.util.JwtTokenUtil;
import com.vbt.kanban.dto.response.MessageResponse;
import com.vbt.kanban.auth.dto.JwtRequest;
import com.vbt.kanban.auth.dto.JwtResponse;
import com.vbt.kanban.auth.dto.NewPasswordDto;
import com.vbt.kanban.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vbt.kanban.exception.ResetPasswordRequestNotFoundException;
import com.vbt.kanban.exception.ResetPasswordTokenIsNotValidException;
import com.vbt.kanban.exception.UserNotFoundException;

import javax.mail.MessagingException;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private AuthService userAuthService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtUserDetailsService;

	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
		final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequest.getEmail());

		final String token = jwtTokenUtil.generateToken(userDetails);

		JwtResponse jwt = accountService.getUserWithJwtToken(userDetails.getUsername(), token);
		return ResponseEntity.ok(jwt);
	}

	@PostMapping("/register")
	public AuthDto add(@RequestBody RegisterRequest request) throws Exception {
		return this.userAuthService.register(request);
	}

	private void authenticate(String email, String password) throws Exception {
		Objects.requireNonNull(email);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (DisabledException e) {
			throw new Exception("User is not active", e);
		} catch (BadCredentialsException e) {
			throw new Exception("Invalid credentials", e);
		}
	}

	@GetMapping("/reset-password/{email}")
	public ResponseEntity<?> resetPasswordRequest(@PathVariable("email") String email) throws UserNotFoundException, MessagingException {
		userAuthService.createResetPasswordRequest(email);
		return ResponseEntity
				.ok(new MessageResponse("Şifre sıfırlama talebiniz alınmıştır. Lütfen emailinizi kontrol edin"));

	}

	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@RequestBody NewPasswordDto newPasswordDto)
			throws ResetPasswordRequestNotFoundException, ResetPasswordTokenIsNotValidException {

		userAuthService.resetPassword(newPasswordDto);
		return ResponseEntity.ok(new MessageResponse("Şifre sıfırlama işlemi başarılı"));

	}

}
