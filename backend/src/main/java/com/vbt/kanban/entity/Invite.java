package com.vbt.kanban.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invite {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank
	@NotNull
	@JsonIgnore
	@ManyToOne(targetEntity = Project.class, fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "project_id")
	private Project project;

	@NotBlank
	@NotNull
	@Enumerated(EnumType.STRING)
	private EInviteStatus status;

	@NotBlank
	@NotNull
	@OneToOne(targetEntity = Account.class)
	private Account inviter;

	@NotBlank
	@NotNull
	private String invitedEmail;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@NotBlank
	@NotNull
	private String invitationToken;

	@NotBlank
	@NotNull
	private boolean isValid;

	public Invite(Project project, String invitedEmail, Account inviter) {
		this.project = project;
		this.invitedEmail = invitedEmail;
		this.invitationToken = UUID.randomUUID().toString();
		this.createdAt = new Date();
		this.inviter = inviter;
		this.isValid=true;
	}

}
