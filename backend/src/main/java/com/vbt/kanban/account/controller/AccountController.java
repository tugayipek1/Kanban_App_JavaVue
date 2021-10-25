package com.vbt.kanban.account.controller;

import java.util.Optional;

import com.vbt.kanban.account.dto.AccountDto;
import com.vbt.kanban.account.exception.PermissionException;
import com.vbt.kanban.entity.Account;
import com.vbt.kanban.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import com.vbt.kanban.account.service.AccountService;

@RestController
@RequestMapping("/account")
@CrossOrigin
public class AccountController {
	@Autowired
	private AccountService accountService;

	@GetMapping
	private ResponseEntity myAccount() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User a = (User) auth.getPrincipal();
		String jwtUserName = a.getUsername();

		Optional<Account> op = accountService.findByEmail(jwtUserName);
		if (op.isPresent()) {
			AccountDto account = accountService.convert(op.get());
			return ResponseEntity.ok(account);
		}

		return ResponseEntity.notFound().build();
	}

	@GetMapping("/{id}")
	private ResponseEntity getAccount(@PathVariable("id") Long id) {
		Optional<Account> op = accountService.findById(id);
		if (op.isPresent()) {
			AccountDto account = accountService.convert(op.get());
			return ResponseEntity.ok(account);
		}

		return ResponseEntity.notFound().build();
	}

	@PutMapping
	public ResponseEntity update(@RequestBody Account account) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User a = (User) auth.getPrincipal();
		String jwtUserName = a.getUsername();

		try {
			accountService.updateProfile(jwtUserName, account);
			return ResponseEntity.ok().build();
		} catch (PermissionException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

}
