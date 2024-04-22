package com.veerajk.demo.controller;

import java.util.List;
import java.util.Optional;

import com.veerajk.demo.dtos.ColumnDto;
import com.veerajk.demo.dtos.ColumnWithoutTaskDto;
import com.veerajk.demo.requests.ColumnRemoveRequest;
import com.veerajk.demo.services.impl.ColumnServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.veerajk.demo.model.Column;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("api/boards/{boardid}/columns")
public class ColumnController {

	@Autowired
	ColumnServiceImpl columnServiceImpl;

	@PostMapping
	public ResponseEntity<ColumnDto> addColumn(@RequestBody ColumnDto columnDto, @PathVariable Long boardid){
		return new ResponseEntity<>(columnServiceImpl.addColumn(columnDto,boardid), HttpStatus.CREATED);
	}


	@GetMapping
	public ResponseEntity<List<ColumnDto>> getAllColumns(@PathVariable Long boardid) throws Exception {
		return ResponseEntity.ok(columnServiceImpl.getAllColumns(boardid));
	}

	@GetMapping("{id}")
	public ResponseEntity<ColumnDto> getColumn(@PathVariable Long id) throws Exception{
		return ResponseEntity.ok(columnServiceImpl.getColumn(id));
	}

	@DeleteMapping("{id}")
	public String removeColumn(@PathVariable Long id) throws Exception {
		return columnServiceImpl.removeColumn(id);
	}

	@GetMapping("/plaincolumns")
	public ResponseEntity<List<ColumnWithoutTaskDto>> getColumnsWithoutTasks(@PathVariable Long boardid){
		return ResponseEntity.ok(columnServiceImpl.getOnlyColumns(boardid));
	}
}
