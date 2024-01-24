package com.veerajk.demo.controller;

import com.veerajk.demo.model.TaskComment;
import com.veerajk.demo.repo.TaskCommentRepo;
import com.veerajk.demo.repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    @Autowired
    TaskCommentRepo taskCommentRepo;
    @Autowired
    TaskRepo taskRepo;
    @PostMapping("/addComment")
    public TaskComment addComment(@RequestBody TaskComment taskComment){
        System.out.println(taskComment.getTaskid());
        taskComment.setTaskid(taskRepo.findById(1L));
//        taskCommentRepo.save(taskComment);
        System.out.println(taskComment.getTaskid());
        return taskComment;
    }
}
