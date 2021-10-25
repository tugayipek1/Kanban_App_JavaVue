package com.vbt.kanban.auth.dto;

import com.vbt.kanban.account.dto.AccountDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class JwtResponse implements Serializable {
    private static final long serialVersionUID = -7091479091924046844L;
    private final String token;
    private final AccountDto account;

    public JwtResponse(String token, AccountDto account) {
        this.token = token;
        this.account = account;
    }

}
