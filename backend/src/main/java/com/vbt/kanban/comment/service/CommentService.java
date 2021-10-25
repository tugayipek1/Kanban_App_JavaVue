package com.vbt.kanban.comment.service;

import com.vbt.kanban.entity.Comment;
import com.vbt.kanban.entity.Task;
import com.vbt.kanban.exception.AccountNotFoundException;
import com.vbt.kanban.exception.NotFoundException;

import java.util.List;

public interface CommentService {
    Comment create(Comment comment) throws AccountNotFoundException, NotFoundException;
    Comment update(Comment comment) throws Exception;
    void delete(long commentId) throws Exception;
    List<Comment> getByTaskId(long task) throws Exception;
}
