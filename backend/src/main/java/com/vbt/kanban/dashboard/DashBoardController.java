package com.vbt.kanban.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vbt.kanban.account.service.AccountService;
import com.vbt.kanban.service.ProjectService;
import com.vbt.kanban.task.service.TaskService;

@RestController
@CrossOrigin
@RequestMapping("/dashboard")

public class DashBoardController {
	
	@Autowired
	private TaskService taskService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private ProjectService projectService;

	@GetMapping("/countAccount")
	public ResponseEntity getAllByAccount() throws Exception{
		long records = this.accountService.count();
		return ResponseEntity.ok(records);
	}

	@GetMapping("/countTask")
	public ResponseEntity getAllByTask() throws Exception{
		long records = this.taskService.count();
		return ResponseEntity.ok(records);
	}
	
	@GetMapping("/countProject")
	public ResponseEntity getAllByProject() throws Exception{
		long records = this.projectService.count();
		return ResponseEntity.ok(records);
	}
}
