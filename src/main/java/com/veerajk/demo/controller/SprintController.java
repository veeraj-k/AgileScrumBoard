package com.veerajk.demo.controller;

import com.veerajk.demo.dtos.SprintDto;
import com.veerajk.demo.services.impl.SprintServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin("http://localhost:3000/")
@Controller
@RequestMapping("api/boards")
public class SprintController {

    @Autowired
    SprintServiceImpl sprintService;

    @PostMapping("{teamid}/startSprint")
    public ResponseEntity<SprintDto> addSprint(@RequestBody SprintDto sprintDto,@PathVariable Long teamid){
        return new ResponseEntity<>(sprintService.addSprint(sprintDto,teamid), HttpStatus.CREATED);
    }
    @GetMapping("{sprintid}")
    public  ResponseEntity<SprintDto> getSprint(@PathVariable Long sprintid) throws Exception {

        return ResponseEntity.ok(sprintService.getSprint(sprintid));
    }

    @GetMapping
    public  ResponseEntity<List<SprintDto>> getAllSprints(){
        return ResponseEntity.ok(sprintService.getAllSprints());
    }

    @DeleteMapping("{sprintid}")
    public ResponseEntity<String> deleteSprint(@PathVariable Long sprintid) throws Exception {
        return ResponseEntity.ok(sprintService.deleteSprint(sprintid));
    }
}
