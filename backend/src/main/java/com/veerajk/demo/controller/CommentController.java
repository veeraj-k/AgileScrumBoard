package com.veerajk.demo.controller;

import com.veerajk.demo.dtos.CommentDto;
import com.veerajk.demo.model.TaskComment;
import com.veerajk.demo.repo.TaskCommentRepo;
import com.veerajk.demo.repo.TaskRepo;
import com.veerajk.demo.repo.UserRepo;
import com.veerajk.demo.requests.AddComment;
import com.veerajk.demo.services.impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/teams/{teamid}/sprint/columns/tasks")
public class CommentController {


    @Autowired
    CommentServiceImpl commentService;

    @PostMapping("/{taskid}/comments")
    public ResponseEntity<CommentDto> addComment(@RequestBody CommentDto commentDto,@PathVariable Long taskid) throws Exception {

        return  new ResponseEntity<>(commentService.addComment(commentDto,taskid), HttpStatus.CREATED);
    }

    @GetMapping("/{taskid}/comments")
    public ResponseEntity<List<CommentDto>> getTaskComments(@PathVariable Long taskid) throws Exception{
        return ResponseEntity.ok(commentService.getTaskComments(taskid));
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity removeComment(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(commentService.removeComment(id));
    }
}
