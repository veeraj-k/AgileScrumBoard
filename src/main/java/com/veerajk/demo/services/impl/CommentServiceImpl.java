package com.veerajk.demo.services.impl;

import com.veerajk.demo.dtos.CommentDto;
import com.veerajk.demo.model.Task;
import com.veerajk.demo.model.TaskComment;
import com.veerajk.demo.model.User;
import com.veerajk.demo.repo.TaskCommentRepo;
import com.veerajk.demo.repo.TaskRepo;
import com.veerajk.demo.repo.UserRepo;
import com.veerajk.demo.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    UserRepo userRepo;
    TaskCommentRepo commentRepo;
    TaskRepo taskRepo;

    @Autowired
    public CommentServiceImpl(UserRepo userRepo, TaskCommentRepo commentRepo,TaskRepo taskRepo) {
        this.userRepo = userRepo;
        this.commentRepo = commentRepo;
        this.taskRepo = taskRepo;
    }

    @Override
    public CommentDto addComment(CommentDto commentDto,Long taskid) throws Exception{
        TaskComment taskComment = new TaskComment();
        User user = userRepo.findById(1L).orElseThrow();
        Task task = taskRepo.findById(taskid).orElseThrow(()-> new Exception("Task not found!"));

        taskComment.setDescription(commentDto.getDescription());
        taskComment.setUserid(user);
        taskComment.setTaskid(task);

        return mapCommentToDto(commentRepo.save(taskComment));
    }

    protected CommentDto mapCommentToDto(TaskComment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setDescription(comment.getDescription());
        commentDto.setUser(comment.getUserid());
        return commentDto;
    }
}
