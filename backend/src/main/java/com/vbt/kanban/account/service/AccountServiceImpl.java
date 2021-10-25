package com.vbt.kanban.account.service;

import com.vbt.kanban.account.dto.AccountDto;
import com.vbt.kanban.account.exception.PermissionException;
import com.vbt.kanban.account.repository.AccountRepository;
import com.vbt.kanban.auth.dto.AuthDto;
import com.vbt.kanban.auth.dto.JwtResponse;
import com.vbt.kanban.entity.Account;
import com.vbt.kanban.entity.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountRepository accountRepository;

	@Transactional
	@Override
	public Account save(Account account) throws Exception {


		return this.accountRepository.save(account);
	}

	@Override
	public Optional<Account> findByEmail(String email) {
		return accountRepository.findByEmail(email);
	}

	@Override
	public Optional<Account> findByNickName(String nickName) {
		return accountRepository.findByNickName(nickName);
	}

	@Override
	public Optional<Account> findById(Long id) {
		return this.accountRepository.findById(id);
	}

	@Transactional
	@Override
	public void updateProfile(String jwtUserName, Account account) throws Exception {
		Account record = accountRepository.findByEmail(jwtUserName)
				.orElseThrow(() -> new Exception("Böyle bir kayıt yok"));

		if (jwtUserName.equals(record.getAuth().getEmail())) {
			record.setNickName(account.getNickName());
			record.setName(account.getName());
			accountRepository.save(record);
		} else {
			throw new PermissionException("Bu işlem için yetkiniz bulunmuyor");
		}
	}

	@Override
	public JwtResponse getUserWithJwtToken(String email, String token) {
		Optional<Account> control = findByEmail(email);
		if (control.isPresent()) {
			Account account = control.get();
			AccountDto dto = this.convert(account);
			return new JwtResponse(token, dto);
		}
		return null;
	}

	@Override
	public AccountDto convert(Account account) {
		AccountDto accountDto = new AccountDto();
		accountDto.setId(account.getId());
		accountDto.setNickName(account.getNickName());
		accountDto.setName(account.getName());
		AuthDto authDto = new AuthDto();
		authDto.setEmail(account.getAuth().getEmail());
		authDto.setId(account.getAuth().getId());
		accountDto.setAuth(authDto);
		return accountDto;
	}


	@Override
	public long count() {
		return this.accountRepository.count();
	}
}
