package com.veerajk.demo.controller;

import com.veerajk.demo.dtos.SprintDto;
import com.veerajk.demo.dtos.SprintTaskCountResponse;
import com.veerajk.demo.services.impl.SprintServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin("http://localhost:3000/")
@Controller
@RequestMapping("api/teams/{teamid}")
public class SprintController {

    @Autowired
    SprintServiceImpl sprintService;

    @PostMapping("/sprint/startSprint")
    public ResponseEntity<SprintDto> addSprint(@RequestBody SprintDto sprintDto,@PathVariable Long teamid){
        return new ResponseEntity<>(sprintService.addSprint(sprintDto,teamid), HttpStatus.CREATED);
    }
    @GetMapping("/sprint")
    public  ResponseEntity<SprintDto> getSprint(@PathVariable Long teamid) throws Exception {

        return ResponseEntity.ok(sprintService.getSprint(teamid));
    }

//    @GetMapping
//    public  ResponseEntity<List<SprintDto>> getAllSprints(){
//        return ResponseEntity.ok(sprintService.getAllSprints());
//    }

    @DeleteMapping("/sprint/{sprintid}")
    public ResponseEntity<String> deleteSprint(@PathVariable Long sprintid) throws Exception {
        return ResponseEntity.ok(sprintService.deleteSprint(sprintid));
    }
    @GetMapping("/sprint/tasks-count")
    public ResponseEntity<SprintTaskCountResponse> getTaskCount(@PathVariable Long teamid) throws Exception {
        return ResponseEntity.ok(sprintService.getTaskCounts(teamid));
    }

}
