package com.vbt.kanban.comment.service;

import com.vbt.kanban.account.service.AccountService;
import com.vbt.kanban.comment.repository.CommentRepository;
import com.vbt.kanban.dto.EmailContentDto;
import com.vbt.kanban.entity.Account;
import com.vbt.kanban.entity.Comment;
import com.vbt.kanban.entity.Project;
import com.vbt.kanban.entity.Task;
import com.vbt.kanban.exception.AccountNotFoundException;
import com.vbt.kanban.exception.NotFoundException;
import com.vbt.kanban.service.*;
import com.vbt.kanban.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    TaskService taskService;

    @Autowired
    AccountService accountService;

    @Autowired
    MentionExtractor mentionExtractor;

    @Autowired
    EmailService emailService;

    @Autowired
    ProjectService projectService;

    @Autowired
    private PropertyAccessorService properties;


    @Override
    public Comment create(Comment comment) throws AccountNotFoundException, NotFoundException {

        Comment record = commentRepository.save(comment);
        Account commentOwner = accountService.findById(comment.getCommentOwner().getId()).get();
        Project project = projectService.findByTaskId(comment.getTask().getId());


        mentionExtractor
                .extract(comment.getComment())
                .forEach(account -> {
                    try {
                        sendNotification(comment, commentOwner, account, project);
                    } catch (NotFoundException | MessagingException e) {
                        e.printStackTrace();
                    }
                });


        return record;
    }

    @Override
    public Comment update(Comment comment) throws Exception {
        return commentRepository.save(comment);
    }

    @Override
    public void delete(long commentId) throws Exception {
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<Comment> getByTaskId(long task) throws Exception {
        Optional<Task> taskOp = taskService.getByTaskId(task);
        return commentRepository.findByTaskOrderByAtDesc(taskOp.get());
    }


    public void sendNotification(Comment comment, Account commentOwner, Account account, Project project) throws NotFoundException, MessagingException {
        String taskCode = project.getProjectCode() + "-" + comment.getTask().getId();
        String subject = project.getProjectName() + " isimli projede " + taskCode + " taskında " + commentOwner.getName() + " sizden bahsetti.";

        EmailContentDto content = new EmailContentDto();
        content.setTemplateName("MentionCommentMailTemplate");
        content.setTo(account.getAuth().getEmail());
        content.setSubject(subject);
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", account.getName());
        model.put("projectName", project.getProjectName());
        model.put("taskCode", taskCode);
        model.put("link", properties.getFrontendHost() +"/board?project_id="+project.getId()+"&task_id="+comment.getTask().getId());
        model.put("commentOwner", commentOwner.getName());
        model.put("comment", comment.getComment());
        content.setProps(model);

        emailService.sendHtmlEmail(content);
    }
}

