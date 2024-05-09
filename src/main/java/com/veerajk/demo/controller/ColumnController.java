package com.veerajk.demo.controller;

import java.util.List;

import com.veerajk.demo.dtos.ColumnDto;
import com.veerajk.demo.dtos.ColumnWithoutTaskDto;
import com.veerajk.demo.services.impl.ColumnServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("api/teams/{teamid}/sprint/columns")
public class ColumnController {

	@Autowired
	ColumnServiceImpl columnServiceImpl;

	@PostMapping
	public ResponseEntity<ColumnDto> addColumn(@RequestBody ColumnDto columnDto, @PathVariable Long teamid) throws Exception {
		return new ResponseEntity<>(columnServiceImpl.addColumn(columnDto, teamid), HttpStatus.CREATED);
	}


	@GetMapping
	public ResponseEntity<List<ColumnDto>> getAllColumns(@PathVariable Long teamid) throws Exception {
		return ResponseEntity.ok(columnServiceImpl.getAllColumns(teamid));
	}

	@GetMapping("{id}")
	public ResponseEntity<ColumnDto> getColumn(@PathVariable Long id) throws Exception{
		return ResponseEntity.ok(columnServiceImpl.getColumn(id));
	}

	@DeleteMapping("{id}")
	public String removeColumn(@PathVariable Long id,@RequestParam Long targetid) throws Exception {
		return columnServiceImpl.removeColumn(id,targetid);
	}

	@GetMapping("/plaincolumns")
	public ResponseEntity<List<ColumnWithoutTaskDto>> getColumnsWithoutTasks(@PathVariable Long sprintid){
		return ResponseEntity.ok(columnServiceImpl.getOnlyColumns(sprintid));
	}
}
