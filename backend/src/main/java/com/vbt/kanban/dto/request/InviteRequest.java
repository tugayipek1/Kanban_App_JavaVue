package com.vbt.kanban.dto.request;

import com.vbt.kanban.entity.Account;
import com.vbt.kanban.entity.Project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InviteRequest {

	private Project project;
	private Account account;
	private String invitedUserEmail;

}
