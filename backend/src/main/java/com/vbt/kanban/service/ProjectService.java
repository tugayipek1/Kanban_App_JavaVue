package com.vbt.kanban.service;

import java.util.List;
import java.util.Optional;

import com.vbt.kanban.entity.Account;
import com.vbt.kanban.entity.Project;
import com.vbt.kanban.entity.Task;
import com.vbt.kanban.exception.AccountNotFoundException;
import com.vbt.kanban.exception.NotFoundException;
import com.vbt.kanban.exception.ProjectNotFoundException;
import org.springframework.security.core.parameters.P;

public interface ProjectService {

	Project add(Project project) throws AccountNotFoundException, NotFoundException;

	Project update(Project project) throws Exception;

	Project delete(Long projectId) throws Exception;

	List<Project> getAll();

	Project getProject(Long id) throws Exception;

	Project findById(Long id) throws ProjectNotFoundException;

	List<Project> getProjectOwner(Long ownerId) throws Exception;

	List<Project> myProject(Long user_id) throws Exception;

	void addMember(Project project, Account account);

	void removeMember(long projectId, long accountId);

	boolean isProjectMember(long id, String email);
	boolean isProjectExists(long id);
	
	long count();
	Project findByTaskId(long Id) throws NotFoundException;

}
