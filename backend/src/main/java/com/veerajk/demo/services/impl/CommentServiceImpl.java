package com.veerajk.demo.services.impl;

import com.veerajk.demo.dtos.CommentDto;
import com.veerajk.demo.model.Task;
import com.veerajk.demo.model.TaskComment;
import com.veerajk.demo.model.User;
import com.veerajk.demo.repo.TaskCommentRepo;
import com.veerajk.demo.repo.TaskRepo;
import com.veerajk.demo.repo.UserRepo;
import com.veerajk.demo.services.CommentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<CommentDto> getTaskComments(Long taskid) throws Exception {
        Task task = taskRepo.findById(taskid).orElseThrow(()-> new Exception("Task not found!"));
        List<TaskComment> comments = task.getComments();
        List<CommentDto> commentDtoList = new ArrayList<>();
        if(comments!=null){
            commentDtoList = comments.stream().map((comment) -> mapCommentToDto(comment)).toList();
        }

        return commentDtoList;
    }

    @Override
    @Transactional
    public String removeComment(Long id) throws Exception {
//        TaskComment comment = commentRepo.findById(id).orElseThrow(()-> new Exception("Comment not found!"));
        commentRepo.deleteById(id);
        return "Comment deleted successfully";
    }

    protected CommentDto mapCommentToDto(TaskComment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setDescription(comment.getDescription());
        commentDto.setUser(comment.getUserid());
        return commentDto;
    }
}
