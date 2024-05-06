package com.veerajk.demo.services;

import com.veerajk.demo.dtos.SprintDto;

import java.util.List;

public interface SprintService {

    SprintDto addSprint(SprintDto sprintDto);
    SprintDto getSprint(Long id) throws Exception;
    List<SprintDto> getAllSprints();
    String deleteSprint(Long id) throws Exception;
}
