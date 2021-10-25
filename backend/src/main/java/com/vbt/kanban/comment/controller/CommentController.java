package com.vbt.kanban.comment.controller;

import com.vbt.kanban.account.service.AccountService;
import com.vbt.kanban.comment.service.CommentService;
import com.vbt.kanban.entity.Comment;

import com.vbt.kanban.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TaskService taskService;




    @GetMapping("/{taskId}")
    public ResponseEntity getAll(@PathVariable("taskId") long taskId) throws Exception {
        List<Comment> records = this.commentService.getByTaskId(taskId);
        return ResponseEntity.ok(records);
    }

    @PostMapping
    public ResponseEntity addComment(@RequestBody Comment comment) throws Exception {
        Date now = new Date();
        comment.setAt(now);
        Comment record = this.commentService.create(comment);
         return ResponseEntity.ok(record);
    }

    @PutMapping
    public ResponseEntity updateComment(@RequestBody Comment comment) throws Exception {
        Comment record = this.commentService.update(comment);
        return ResponseEntity.ok(record);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable("commentId") long commentId) throws Exception {
        this.commentService.delete(commentId);
        return ResponseEntity.ok().build();
    }
}
