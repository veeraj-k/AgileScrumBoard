package com.veerajk.demo.services;

import com.veerajk.demo.dtos.SprintDto;
import com.veerajk.demo.dtos.SprintTaskCountResponse;

import java.util.List;

public interface SprintService {

    SprintDto addSprint(SprintDto sprintDto,Long teamid);
    SprintDto getSprint(Long teamid) throws Exception;
    List<SprintDto> getAllSprints();
    String deleteSprint(Long id) throws Exception;

    SprintTaskCountResponse getTaskCounts(Long teamid) throws Exception;

    String completeSprintAndMoveTasksToBacklog(Long teamId) throws Exception;
}
