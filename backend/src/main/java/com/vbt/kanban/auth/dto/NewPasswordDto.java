package com.vbt.kanban.auth.dto;

import lombok.Data;

@Data
public class NewPasswordDto {
	private String password;
	private String resetPasswordToken;
}
