package com.vbt.kanban.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vbt.kanban.entity.Invite;

@Repository
public interface InviteRepository extends JpaRepository<Invite, Long> {
	Optional<Invite> findByInvitationToken(String invitationToken);
}
