package com.vbt.kanban.comment.repository;

import com.vbt.kanban.entity.Comment;
import com.vbt.kanban.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTaskOrderByAtDesc(  Task task);

}
