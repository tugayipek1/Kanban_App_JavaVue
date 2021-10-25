package com.vbt.kanban.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
public class ResetPasswordToken {
	private final int EXPIRATION = 60 ;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NonNull
	@NotBlank
	private String token;

	@NotBlank
	private boolean used;

	@NonNull
	@NotBlank
	private LocalDateTime createDateTime;

	private LocalDateTime usingDateTime;

	@OneToOne(targetEntity = Auth.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "auth_id")
	private Auth auth;

	public ResetPasswordToken(Auth auth) {
		this.auth=auth;
		this.token = UUID.randomUUID().toString();
		this.createDateTime = LocalDateTime.now();
		this.used = false;

	}

	public boolean isExpired() {
		LocalDateTime expireTime = createDateTime.plusMinutes(EXPIRATION);
		return LocalDateTime.now().isAfter(expireTime);
	}
}
