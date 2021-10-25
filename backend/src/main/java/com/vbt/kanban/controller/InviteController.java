package com.vbt.kanban.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vbt.kanban.dto.request.InviteRequest;
import com.vbt.kanban.dto.response.MessageResponse;
import com.vbt.kanban.exception.AccountNotFoundException;
import com.vbt.kanban.exception.InviteNotFoundException;
import com.vbt.kanban.exception.ProjectNotFoundException;
import com.vbt.kanban.exception.UserIsAlreadyMemberOfThisProjectException;
import com.vbt.kanban.service.InviteService;

@RestController
@RequestMapping("/invite")
@CrossOrigin
public class InviteController {

	@Autowired
	private InviteService inviteService;

	@GetMapping("/approve/invitation-token/{token}")
	public ResponseEntity<MessageResponse> approve(@PathVariable String token)
			throws AccountNotFoundException, InviteNotFoundException, UserIsAlreadyMemberOfThisProjectException {
		inviteService.approveInvite(token);
		return new ResponseEntity<>(new MessageResponse("Projeye Katılma işlemi başarılı"), HttpStatus.OK);
	}

	@PostMapping("/send-invite")
	public ResponseEntity<MessageResponse> sendInvite(@RequestBody InviteRequest request)
			throws UserIsAlreadyMemberOfThisProjectException, ProjectNotFoundException {
		inviteService.inviteAccount(request);
		return new ResponseEntity<>(new MessageResponse("Davet gönderildi"), HttpStatus.OK);
	}
}
