package com.vbt.kanban.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.vbt.kanban.exception.AccountNotFoundException;
import com.vbt.kanban.exception.NotFoundException;

import com.vbt.kanban.exception.ProjectNotFoundException;
import org.springframework.stereotype.Service;

import com.vbt.kanban.account.service.AccountService;
import com.vbt.kanban.entity.Account;
import com.vbt.kanban.entity.Project;
import com.vbt.kanban.repository.ProjectRepository;

@Service
public class ProjectServiceImpl implements ProjectService {

	private ProjectRepository projectRepository;

	private AccountService accountService;

	public ProjectServiceImpl(ProjectRepository projectRepository, AccountService accountService) {
		this.projectRepository = projectRepository;
		this.accountService = accountService;
	}

	@Override
	public Project add(Project project) throws AccountNotFoundException, NotFoundException {
		Account account = accountService.findById(project.getOwnerId())
				.orElseThrow(() -> new AccountNotFoundException("Account Bulunamadı."));
		project.getMembers().add(account);
		List<Project> projectsByProjectName = this.projectRepository.getByProjectNameIgnoreCaseAndOwnerId(project.getProjectName(), account.getId());
		List<Project> projectsByProjectCode = this.projectRepository.getByProjectCodeIgnoreCaseAndOwnerId(project.getProjectCode(), account.getId());
		if ( projectsByProjectName.size()!=0 || projectsByProjectCode.size()!=0){
			throw new NotFoundException("Proje adı veya proje kodu kullanılmaktadır.");
		}
		else{
			return this.projectRepository.save(project);
		}
	}
	@Override
	public Project update(Project project) throws NotFoundException {

	Optional<Project> op=this.projectRepository.findById(project.getId());
		if (op.isEmpty()) {
			throw new NotFoundException("Böyle bir proje bulunamadı.");
		}
		Project p=op.get();
		p.setProjectName(project.getProjectName());
		p.setProjectCode(project.getProjectCode());
	return 	this.projectRepository.save(p);

	}

	@Override
	public Project delete(Long projectId) throws NotFoundException {
		if (this.projectRepository.findById(projectId).isEmpty()) {
			throw new NotFoundException("Böyle bir proje bulunamadı.");
		} else {
			Project deletedProject = this.projectRepository.findById(projectId).get();
			this.projectRepository.delete(deletedProject);
			return deletedProject;
		}

	}

	@Override
	public List<Project> getAll() {
		return this.projectRepository.findAll();
	}

	@Override
	public Project getProject(Long id) throws NotFoundException {
		var project = this.projectRepository.findById(id);
		if (project.isEmpty()) {
			throw new NotFoundException("Böyle bir proje bulunamadı.");
		}
		return project.get();
	}

	@Override
	public Project findById(Long id) throws ProjectNotFoundException {
		return this.projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException("Proje bulunmadı"));
	}

	@Override
	public List<Project> getProjectOwner(Long ownerId) throws NotFoundException {
		List<Project> projects = this.projectRepository.getByOwnerId(ownerId);
		if (projects.isEmpty()) {
			throw new NotFoundException("Kullanıcıya ait projeler bulunmamaktadır..");
		}
		return projects;
	}

	@Override
	public List<Project> myProject(Long user_id) throws NotFoundException {
		List<Project> projects = this.projectRepository.findProjectByMembers_Auth_Id(user_id);
		return projects;
	}


	@Override
	public void addMember(Project project, Account account) {
		project.getMembers().add(account);
		projectRepository.save(project);
	}

	@Override
	public void removeMember(long projectId, long accountId) {
		Optional<Project> optProject= projectRepository.findById(projectId);
		Optional<Account> optAccount= accountService.findById(accountId);

		///
		Project project = optProject.get();
		Account account = optAccount.get();
		//

		  project.setMembers(
				project.getMembers()
				.stream()
				.filter(a->a.getId()!=account.getId())
				.collect(Collectors.toSet())
		);
		projectRepository.save(project);
	}


	@Override
	public boolean isProjectMember(long projedtId,String email) {
		return projectRepository.existByProjectIdAndMemberEmail(projedtId,email);
	}

	@Override
	public boolean isProjectExists(long id) {
		return projectRepository.existsById(id);
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return this.projectRepository.count();
	}


	@Override
	public Project findByTaskId(long id) throws NotFoundException {
		return projectRepository.findByTasks_Id(id).orElseThrow(( )-> new NotFoundException("Project Not Found"));
	}
}
