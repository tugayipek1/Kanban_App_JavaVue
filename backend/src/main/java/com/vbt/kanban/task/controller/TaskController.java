package com.vbt.kanban.task.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import com.vbt.kanban.account.service.AccountService;
import com.vbt.kanban.entity.Account;
import com.vbt.kanban.entity.TaskStatus;
import com.vbt.kanban.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vbt.kanban.entity.Task;
import com.vbt.kanban.task.service.TaskService;

@RestController
@RequestMapping("/task")
@CrossOrigin
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	@Autowired
	private AccountService accountService;
	
	@GetMapping("/{projectId}")
	public ResponseEntity getAllByProjectId(@PathVariable("projectId") Long projectId) throws Exception{
		List<Task> records = this.taskService.getByProjectId(projectId);
		return ResponseEntity.ok(records);
	}

	@GetMapping("/{projectId}/status/backlog")
	public ResponseEntity findByStatusAndProjectId(@PathVariable("projectId") Long projectId) throws Exception{
		List<Task> records = this.taskService.findByStatusAndProjectId(TaskStatus.BACKLOG,projectId);
		return ResponseEntity.ok(records);
	}

	@GetMapping("/id/{taskId}")
	public ResponseEntity getAllByTaskId(@PathVariable("taskId") Long taskId) throws Exception{
		Task records = this.taskService.getByTaskId(taskId).orElseThrow(()-> new NotFoundException("Task bulunamadı"));
		return ResponseEntity.ok(records);
	}
	
	@PostMapping
	public ResponseEntity addTask(Principal principal, @RequestBody Task task) throws Exception {
		Optional<Account> account = accountService.findByEmail(principal.getName());
		task.setReporter(account.get());
		Task record = this.taskService.create(task);
		return ResponseEntity.ok(record);
	}
	
	@PutMapping
	public ResponseEntity updateTask(@RequestBody Task task) throws Exception {
		Task record = this.taskService.update(task);
		return ResponseEntity.ok(record);
	}
	
	@DeleteMapping("/{taskId}")
	public ResponseEntity deleteTask(@PathVariable("taskId") Long taskId) throws Exception {
		Task record = this.taskService.delete(taskId);
		return ResponseEntity.ok(record);
	}
}
