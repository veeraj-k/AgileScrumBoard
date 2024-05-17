package com.veerajk.demo.services.impl;

import com.veerajk.demo.dtos.BacklogDto;
import com.veerajk.demo.dtos.BacklogTaskDto;
import com.veerajk.demo.dtos.TaskDto;
import com.veerajk.demo.model.Backlog;
import com.veerajk.demo.model.Team;
import com.veerajk.demo.repo.BacklogRepo;
import com.veerajk.demo.repo.TeamRepo;
import com.veerajk.demo.services.BacklogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BacklogServiceImpl implements BacklogService {
    private BacklogTaskServiceImpl backlogTaskService;
    private TeamRepo teamRepo;
    private BacklogRepo backlogRepo;
    @Autowired
    public BacklogServiceImpl(BacklogTaskServiceImpl backlogTaskService, TeamRepo teamRepo, BacklogRepo backlogRepo) {
        this.backlogTaskService = backlogTaskService;
        this.teamRepo = teamRepo;
        this.backlogRepo = backlogRepo;
    }

    @Override
    public BacklogDto getBacklog(Long teamid) throws Exception{
        Team team = teamRepo.findById(teamid).orElseThrow();
        Backlog backlog =team.getBacklog();
        return mapToDto(backlog);
    }
    protected BacklogDto mapToDto(Backlog backlog){
        BacklogDto backlogDto = new BacklogDto();
        backlogDto.setId(backlog.getId());
        List<BacklogTaskDto> taskList = new ArrayList<>();
        if(backlog.getBacklogTasks()!=null){
            backlogDto.setTasks(backlog.getBacklogTasks().stream().map((backlogtask) -> backlogTaskService.mapBacklogTaskToDto(backlogtask)).toList());
        }
        else{
            backlogDto.setTasks(
                    taskList
            );
        }
        return backlogDto;
    }


}
