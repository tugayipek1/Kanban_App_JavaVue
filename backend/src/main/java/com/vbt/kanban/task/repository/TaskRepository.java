package com.vbt.kanban.task.repository;
import java.util.List;

import com.vbt.kanban.entity.Project;
import com.vbt.kanban.entity.TaskStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.vbt.kanban.entity.Task;

public interface TaskRepository extends CrudRepository<Task, Long>{
	@Query("SELECT CASE WHEN COUNT(p)> 0 THEN true ELSE false END FROM Project p JOIN p.members pm  WHERE p.id=?1 AND pm.auth.email=?2")
	boolean existByTaskIdAndMemberEmail(long id ,String email);
	List<Task> getByProjectId(@Param("project") Long project);
	List<Task> findByStatusAndProject(TaskStatus status, Project project);
	long count();

}
