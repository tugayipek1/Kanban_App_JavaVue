package com.vbt.kanban.task.service;

import java.util.List;
import java.util.Optional;

import com.vbt.kanban.entity.Account;
import com.vbt.kanban.entity.Project;
import com.vbt.kanban.entity.Task;
import com.vbt.kanban.entity.TaskStatus;
import com.vbt.kanban.exception.AccountNotFoundException;
import com.vbt.kanban.exception.NotFoundException;
import com.vbt.kanban.exception.ProjectNotFoundException;

import javax.mail.MessagingException;

public interface TaskService {
	Task create(Task task) throws AccountNotFoundException, NotFoundException, MessagingException;
	Task update(Task task) throws Exception;
	Task delete(Long taskId) throws Exception;
	List<Task> getByProjectId(Long projectId) throws Exception;
	long count();
    Optional<Task> getByTaskId(Long taskId);
	List<Task> findByStatusAndProjectId(TaskStatus status, long projectId) throws ProjectNotFoundException;
}
