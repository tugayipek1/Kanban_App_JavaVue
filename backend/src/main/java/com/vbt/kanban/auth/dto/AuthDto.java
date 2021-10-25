package com.vbt.kanban.auth.dto;

import com.vbt.kanban.account.dto.AccountDto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthDto {
  private long id;
  private String email;
 }
