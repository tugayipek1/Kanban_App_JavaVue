package com.vbt.kanban.service;

import java.util.Optional;

import com.vbt.kanban.dto.request.InviteRequest;
import com.vbt.kanban.entity.Invite;
import com.vbt.kanban.exception.AccountNotFoundException;
import com.vbt.kanban.exception.InviteNotFoundException;
import com.vbt.kanban.exception.ProjectNotFoundException;
import com.vbt.kanban.exception.UserIsAlreadyMemberOfThisProjectException;

public interface InviteService {
	Invite save(Invite invite);

	Invite update(Invite invite);

	Optional<Invite> findByInvitationToken(String invitationToken);

	void approveInvite(String token) throws AccountNotFoundException, InviteNotFoundException, UserIsAlreadyMemberOfThisProjectException;

	void inviteAccount(InviteRequest request) throws UserIsAlreadyMemberOfThisProjectException, ProjectNotFoundException;
}
