package com.veerajk.demo.services.impl;

import com.veerajk.demo.dtos.TeamDto;
import com.veerajk.demo.model.Team;
import com.veerajk.demo.repo.TeamRepo;
import com.veerajk.demo.services.TaskService;
import com.veerajk.demo.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {

    TeamRepo teamRepo;
    SprintServiceImpl sprintService;
    @Autowired
    public TeamServiceImpl(TeamRepo teamRepo,SprintServiceImpl sprintService){
        this.teamRepo= teamRepo;
        this.sprintService = sprintService;
    }


    @Override
    public List<TeamDto> getAllTeams() {
        List<Team> teams = teamRepo.findAll();
        List<TeamDto> teamDtos = teams.stream().map((team -> mapToDto(team))).toList();

        return teamDtos;
    }

    protected TeamDto mapToDto(Team team){
        TeamDto teamDto = new TeamDto();
        teamDto.setId(team.getId());
        teamDto.setName(team.getName());
        teamDto.setSprint(sprintService.mapToDto(team.getSprint()));
        return teamDto;
    }
    protected Team mapToEntity(TeamDto teamDto){
        Team team = new Team();
        team.setId(teamDto.getId());
        team.setName(teamDto.getName());
        return team;
    }
}
