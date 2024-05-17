package com.veerajk.demo.controller;

import com.veerajk.demo.dtos.TeamDto;
import com.veerajk.demo.services.TaskService;
import com.veerajk.demo.services.TeamService;
import com.veerajk.demo.services.impl.TaskServiceImpl;
import com.veerajk.demo.services.impl.TeamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/teams")
@CrossOrigin
public class TeamController {

    @Autowired
    TeamServiceImpl teamService;
    @GetMapping
    private ResponseEntity<List<TeamDto>> getAllTeams(){

        return ResponseEntity.ok(teamService.getAllTeams());
    }
}
