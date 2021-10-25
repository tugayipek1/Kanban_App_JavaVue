package com.vbt.kanban.account.repository;

import com.vbt.kanban.entity.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {
    @Query("select e from Account e where e.auth.email=?1")
    Optional<Account> findByEmail(String email);
    Optional<Account> findByNickName(String nickName);
    long count();
}
