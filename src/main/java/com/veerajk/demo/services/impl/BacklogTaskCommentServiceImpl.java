package com.veerajk.demo.services.impl;

import com.veerajk.demo.dtos.BacklogTaskCommentDto;
import com.veerajk.demo.dtos.CommentDto;
import com.veerajk.demo.model.BacklogTaskComment;
import com.veerajk.demo.model.TaskComment;
import org.springframework.stereotype.Service;

@Service
public class BacklogTaskCommentServiceImpl {
    protected BacklogTaskCommentDto mapBacklogTaskCommentToDto(BacklogTaskComment backlogTaskComment){
        BacklogTaskCommentDto backlogTaskCommentDto = new BacklogTaskCommentDto();
        backlogTaskCommentDto.setId(backlogTaskComment.getId());
        backlogTaskCommentDto.setDescription(backlogTaskComment.getDescription());
        backlogTaskCommentDto.setUser(backlogTaskComment.getUser());
        return backlogTaskCommentDto;
    }
}
