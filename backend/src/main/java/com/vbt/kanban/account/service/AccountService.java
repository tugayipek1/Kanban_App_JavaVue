package com.vbt.kanban.account.service;

import java.util.List;
import java.util.Optional;

import com.vbt.kanban.account.dto.AccountDto;
import com.vbt.kanban.auth.dto.JwtResponse;
import com.vbt.kanban.entity.Account;
import com.vbt.kanban.entity.Task;

public interface AccountService {
 	Account save(Account account) throws Exception;
	Optional<Account> findByEmail(String email);
	Optional<Account> findByNickName(String nickName);
	Optional<Account> findById(Long id);
	void updateProfile(String jwtUserName, Account account) throws Exception;
	JwtResponse getUserWithJwtToken(String email, String token);
	AccountDto convert(Account account);
	long count();

}
