package com.veerajk.demo.services.impl;

import com.veerajk.demo.dtos.BacklogTaskCommentDto;
import com.veerajk.demo.dtos.BacklogTaskDto;
import com.veerajk.demo.model.BacklogTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
class BacklogTaskServiceImpl {
    private BacklogTaskCommentServiceImpl backlogTaskCommentService;
    @Autowired
    public BacklogTaskServiceImpl(BacklogTaskCommentServiceImpl backlogTaskCommentService) {
        this.backlogTaskCommentService = backlogTaskCommentService;
    }

    protected BacklogTaskDto mapBacklogTaskToDto(BacklogTask backlogTask){
        BacklogTaskDto backlogTaskDto = new BacklogTaskDto();
        backlogTaskDto.setId(backlogTask.getId());
        backlogTaskDto.setTitle(backlogTask.getTitle());
        backlogTaskDto.setDescription(backlogTask.getDescription());
        backlogTaskDto.setType(backlogTask.getType());
        backlogTaskDto.setStoryPoints(backlogTask.getStoryPoints());
        List<BacklogTaskCommentDto> commentDtoList = new ArrayList<>();
        if(backlogTask.getBacklogTaskComments()!=null){
            commentDtoList = backlogTask.getBacklogTaskComments().stream().map((comment) -> backlogTaskCommentService.mapBacklogTaskCommentToDto(comment)).toList();
        }
        backlogTaskDto.setComments(commentDtoList);
        backlogTaskDto.setUser(backlogTask.getUser());
        backlogTaskDto.setSprint(backlogTask.getSprint());
        return backlogTaskDto;
    }
}
