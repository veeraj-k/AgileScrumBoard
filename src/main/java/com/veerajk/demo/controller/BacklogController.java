package com.veerajk.demo.controller;

import com.veerajk.demo.dtos.BacklogDto;
import com.veerajk.demo.services.impl.BacklogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin("http://localhost:3000/")
@Controller
@RequestMapping("api/teams/{teamid}")
public class BacklogController {
    @Autowired
    BacklogServiceImpl backlogService;

    @GetMapping("/backlog")
    private ResponseEntity<BacklogDto> getBacklog(@PathVariable Long teamid) throws Exception {
        return ResponseEntity.ok(backlogService.getBacklog(teamid));
    }
}
