package com.vbt.kanban.controller;

import java.util.List;

import com.vbt.kanban.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vbt.kanban.entity.Project;
import com.vbt.kanban.service.ProjectService;

@RestController
@RequestMapping("/projects")
@CrossOrigin
public class ProjectController {

	private ProjectService projectService;

	@Autowired
	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}

	@GetMapping()
	public ResponseEntity getAll() {
		List<Project> records = this.projectService.getAll();
		return ResponseEntity.ok(records);
	}

	@GetMapping("/{projectId}")
	public ResponseEntity getProject(@PathVariable("projectId") Long projectId) throws Exception {
		Project record = this.projectService.getProject(projectId);
		return ResponseEntity.ok(record);
	}

	@GetMapping("/my/{user_id}")
	public ResponseEntity myProject(@PathVariable("user_id") Long user_id) throws Exception {
		List<Project> records = this.projectService.myProject(user_id);
		return ResponseEntity.ok(records);
	}

	@GetMapping("/projectOwner/{ownerId}")
	public ResponseEntity getProjectOwner(@PathVariable("ownerId") Long ownerId) throws Exception {
		List<Project> records = this.projectService.getProjectOwner(ownerId);
		return ResponseEntity.ok(records);
	}

	@PostMapping()
	public ResponseEntity addProject(@RequestBody Project project) throws Exception {
		Project record = this.projectService.add(project);
		return ResponseEntity.ok(record);
	}

	@PutMapping()
	public ResponseEntity updateProject(@RequestBody Project project) throws Exception {
		Project record = this.projectService.update(project);
		return ResponseEntity.ok(record);
	}

	@DeleteMapping("/{projectId}")
	public ResponseEntity deleteProject(@PathVariable("projectId") Long projectId) throws Exception {
		Project record = this.projectService.delete(projectId);
		return ResponseEntity.ok(record);
	}

	@DeleteMapping("/project/{projectId}/member/{memberId}")
	public ResponseEntity removeMember(@PathVariable("projectId") long projectId,@PathVariable("memberId") long memberId) throws Exception {
	 this.projectService.removeMember(projectId,memberId);
		return ResponseEntity.ok().build();
	}
}
