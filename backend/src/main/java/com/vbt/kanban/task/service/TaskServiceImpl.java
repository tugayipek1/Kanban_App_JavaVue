package com.vbt.kanban.task.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.vbt.kanban.account.service.AccountService;
import com.vbt.kanban.auth.service.AuthService;
import com.vbt.kanban.dto.EmailContentDto;
import com.vbt.kanban.entity.Account;
import com.vbt.kanban.entity.Project;
import com.vbt.kanban.entity.TaskStatus;
import com.vbt.kanban.exception.ProjectNotFoundException;
import com.vbt.kanban.service.EmailService;
import com.vbt.kanban.service.ProjectService;
import com.vbt.kanban.service.PropertyAccessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.incrementer.SybaseMaxValueIncrementer;
import org.springframework.stereotype.Service;

import com.vbt.kanban.entity.Task;
import com.vbt.kanban.exception.AccountNotFoundException;
import com.vbt.kanban.exception.NotFoundException;
import com.vbt.kanban.task.repository.TaskRepository;

import javax.mail.MessagingException;

@Service
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private AccountService accountService;
    @Autowired
	private EmailService emailService;
    @Autowired
	private AuthService authService;
	@Autowired
	private PropertyAccessorService properties;
    @Autowired
	private ProjectService projectService;

	@Override
	public Task create(Task task) throws AccountNotFoundException, NotFoundException, MessagingException {
		Task record = taskRepository.save(task);
		sendTaskAssigneeEmail(record);
		return record;
	}

	@Override
	public Task update(Task task) throws Exception {
		Optional<Task> taskOp = this.taskRepository.findById(task.getId());
		if (taskOp.isEmpty()) {
			throw new NotFoundException("Böyle bir task bulunamadı.");
		}

        sendTaskAssigneeEmail(task);
		return this.taskRepository.save(task);
	}

	@Override
	public Task delete(Long taskId) throws Exception {
		if (this.taskRepository.findById(taskId).isEmpty()) {
			throw new NotFoundException("Böyle bir task bulunamadı.");
		}
		 else {
				Task deletedTask = this.taskRepository.findById(taskId).get();
				this.taskRepository.delete(deletedTask);
				return deletedTask;
			}
	}

	@Override
	public List<Task> getByProjectId(Long projectId) throws Exception {
		List<Task> tasks = this.taskRepository.getByProjectId(projectId);
		return tasks;
	}

	@Override
	public long count() {
		return this.taskRepository.count();
	}

	@Override
	public Optional<Task> getByTaskId(Long taskId) {

		return this.taskRepository.findById(taskId);
	}

	@Override
	public List<Task> findByStatusAndProjectId(TaskStatus status, long id) throws ProjectNotFoundException {
		Project project = this.projectService.findById(id);
		return this.taskRepository.findByStatusAndProject(status, project);

	}


	public void sendTaskAssigneeEmail(Task task) throws MessagingException {
		if (task.getAssignee() == null) {
			return;
		}
		String taskCode = task.getProject().getProjectCode() + "-" + task.getId();

		String subject = task.getProject().getProjectName() + " isimli projede " + taskCode + " taskı size atandı.";

		String link=properties.getFrontendHost() +"/board?project_id="+task.getProject().getId()+"&task_id="+task.getId();

		EmailContentDto content = new EmailContentDto();
		content.setTemplateName("AssigneeMailTemplate");
		content.setTo(task.getAssignee().getAuth().getEmail());
		content.setSubject(subject);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", task.getAssignee().getName());
		model.put("projectName", task.getProject().getProjectName());
		model.put("taskCode", taskCode);
		model.put("link", link);

		content.setProps(model);

		emailService.sendHtmlEmail(content);
	}
}