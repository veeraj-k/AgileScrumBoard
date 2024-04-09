package com.veerajk.demo.controller;

import java.util.List;
import java.util.Optional;

import com.veerajk.demo.dtos.ColumnDto;
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
	public ResponseEntity<List<Column>> getAllColumns(@PathVariable Long boardid){
		return columnServiceImpl.getAllColumns(boardid);
	}

	@GetMapping("{id}")
	public ResponseEntity<Optional<Column>> getColumn(@PathVariable Long id){
		return columnServiceImpl.getColumn(id);
	}

	@PutMapping
	public ResponseEntity<Column> removeColumn(@RequestBody ColumnRemoveRequest columnname, @PathVariable Long boardid){
		return 	columnServiceImpl.removeColumn(columnname,boardid);
	}
}
