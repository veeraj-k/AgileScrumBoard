package com.veerajk.demo.services.impl;

import com.veerajk.demo.dtos.SprintDto;
import com.veerajk.demo.model.Column;
import com.veerajk.demo.model.Sprint;
import com.veerajk.demo.repo.ColumnRepo;
import com.veerajk.demo.repo.SprintRepo;
import com.veerajk.demo.repo.TeamRepo;
import com.veerajk.demo.services.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SprintServiceImpl implements SprintService {
    @Autowired
    SprintRepo sprintRepo;

    @Autowired
    TeamRepo teamRepo;

    public SprintDto addSprint(SprintDto sprintDto,Long teamid){
        Sprint sprint = mapToEntity(sprintDto);

        sprint.setTeam(teamRepo.findById(teamid).orElseThrow());

        Column column = new Column();
        column.setTitle("Done");
        column.setLocation(1);
        column.setSprint(sprint); // Set the Sprint reference for the Column

        List<Column> columns = new ArrayList<>();
        columns.add(column);
        sprint.setColumns(columns);
        sprint.setColumns(columns);

        Sprint sprintRes = sprintRepo.save(sprint);
        SprintDto sprintDtoRes = mapToDto(sprintRes);

        return sprintDtoRes;
    }

    public SprintDto getSprint(Long id) throws Exception {
        Sprint sprint = sprintRepo.findById(id).orElseThrow(() -> new Exception("Sprint not found"));
        SprintDto sprintDto = mapToDto(sprint);
        return sprintDto;
    }

    public List<SprintDto> getAllSprints() {
        List<Sprint> sprintList = sprintRepo.findAll();
        List<SprintDto> sprintDtos = sprintList.stream().map((sprint -> mapToDto(sprint))).toList();

        return sprintDtos;
    }

    @Override
    public String deleteSprint(Long id) throws Exception {
        Sprint sprint = sprintRepo.findById(id).orElseThrow(()-> new Exception("Sprint not found!"));
        sprintRepo.deleteById(id);
        return "Deleted sprint successfully!";
    }

    protected SprintDto mapToDto(Sprint sprint){
        SprintDto sprintDto = new SprintDto();
        sprintDto.setId(sprint.getId());
        sprintDto.setName(sprint.getName());
        sprintDto.setStartdate(sprint.getStartdate());
        sprintDto.setDuration(sprint.getDuration());
        sprintDto.setDescription(sprint.getDescription());
        return sprintDto;
    }

    protected Sprint mapToEntity(SprintDto sprintDto){
        Sprint sprint = new Sprint();
        sprint.setName(sprintDto.getName());
        sprint.setDescription(sprintDto.getDescription());
        sprint.setStartdate(sprintDto.getStartdate());
        sprint.setDuration(sprintDto.getDuration());

        return sprint;
    }
}
