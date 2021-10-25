package com.vbt.kanban.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vbt.kanban.account.service.AccountService;
import com.vbt.kanban.dto.EmailContentDto;
import com.vbt.kanban.dto.request.InviteRequest;
import com.vbt.kanban.entity.Account;
import com.vbt.kanban.entity.EInviteStatus;
import com.vbt.kanban.entity.Invite;
import com.vbt.kanban.exception.AccountNotFoundException;
import com.vbt.kanban.exception.InviteNotFoundException;
import com.vbt.kanban.exception.ProjectNotFoundException;
import com.vbt.kanban.exception.UserIsAlreadyMemberOfThisProjectException;
import com.vbt.kanban.repository.InviteRepository;

@Service
public class InviteServiceImpl implements InviteService {

	@Autowired
	private InviteRepository repo;

	@Autowired
	private AccountService accountService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private PropertyAccessorService properties;

	@Override
	public Invite save(Invite invite) {
		return repo.save(invite);
	}

	@Override
	public Invite update(Invite invite) {
		return repo.save(invite);
	}

	@Override
	public Optional<Invite> findByInvitationToken(String invitationToken) {
		return repo.findByInvitationToken(invitationToken);
	}

	@Override
	public void approveInvite(String token)
			throws AccountNotFoundException, InviteNotFoundException, UserIsAlreadyMemberOfThisProjectException {
		Invite invite = findByInvitationToken(token)
				.orElseThrow(() -> new InviteNotFoundException("Belirten bilgilere sahip davet bulunamadı"));
		if (!invite.isValid()) {
			throw new UserIsAlreadyMemberOfThisProjectException("Zaten bu projenin üyesisiniz.");
		}
		Account account = accountService.findByEmail(invite.getInvitedEmail())
				.orElseThrow(() -> new AccountNotFoundException("Lütfen daveti kabul etmeden önce kayıt olun"));

		invite.setStatus(EInviteStatus.APPROVED);
		invite.setValid(false);
		projectService.addMember(invite.getProject(), account);
		this.update(invite);

	}

	@Override
	public void inviteAccount(InviteRequest request)
			throws UserIsAlreadyMemberOfThisProjectException, ProjectNotFoundException {

		if (!projectService.isProjectExists(request.getProject().getId())) {
			throw new ProjectNotFoundException("Proje bulunamadı");
			
		} else if (projectService.isProjectMember(request.getProject().getId(), request.getInvitedUserEmail())) {
			throw new UserIsAlreadyMemberOfThisProjectException("Kullanıcı zaten bu projenin üyesi");
		}

		Invite invite = new Invite(request.getProject(), request.getInvitedUserEmail(), request.getAccount());
		EmailContentDto content = new EmailContentDto();

		Optional<Account> op = accountService.findByEmail(invite.getInvitedEmail());
		op.ifPresentOrElse((account) -> emailService.send(createInviteMailContent(content, invite)),
				() -> emailService.send(createRegisterAndInviteMailContent(content, invite)));

		invite.setStatus(EInviteStatus.SENT);

		repo.save(invite);
	}

	public EmailContentDto createInviteMailContent(EmailContentDto content, Invite invite) {
		content.setSubject(invite.getInviter().getName() + " seni Kanban uygulamasını kullanmaya davet etti. ");
		content.setMessage(invite.getInviter().getName() + " seni Kanban uygulamasını kullanmaya ve "
				+ invite.getProject().getProjectName() + " isimli projede birlikte çalışmaya davet etti. "
				+ properties.getFrontendHost() + "/invite/approve/invitation-token?token=" + invite.getInvitationToken()
				+ " adresine tıklayarak onlara katılabilirsin.");
		content.setTo(invite.getInvitedEmail());
		return content;

	}

	public EmailContentDto createRegisterAndInviteMailContent(EmailContentDto content, Invite invite) {
		content.setSubject(invite.getInviter().getName() + " seni Kanban uygulamasını kullanmaya davet etti.");
		content.setMessage(invite.getInviter().getName() + " seni Kanban uygulamasını kullanmaya davet etti. "
				+ properties.getFrontendHost() + "/signup adresine tıklayarak kaydolabilir, "
				+ properties.getFrontendHost() + "/invite/approve/invitation-token?token=" + invite.getInvitationToken()
				+ " adresine tıklayarak " + invite.getProject().getProjectName() + " isimli projeye katılabilirsin.");
		content.setTo(invite.getInvitedEmail());
		return content;

	}

}
