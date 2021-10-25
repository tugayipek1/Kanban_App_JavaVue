package com.vbt.kanban.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.vbt.kanban.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
	@Query("SELECT CASE WHEN COUNT(p)> 0 THEN true ELSE false END FROM Project p JOIN p.members pm  WHERE p.id=?1 AND pm.auth.email=?2")
	boolean existByProjectIdAndMemberEmail(long id ,String email);

	List<Project> getByOwnerId(@Param("ownerId") Long ownerId);

	List<Project> findProjectByMembers_Auth_Id(Long user_id);
	
	long count();

	List<Project> getByProjectNameIgnoreCaseAndOwnerId(String projectName, long account);

	List<Project> getByProjectCodeIgnoreCaseAndOwnerId(String projectCode, long account);


	Optional<Project> findByTasks_Id(long Id);


}
