package com.veerajk.demo.controller;

import java.util.List;
import java.util.Optional;

import com.veerajk.demo.requests.ColumnRemoveRequest;
import com.veerajk.demo.services.ColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.veerajk.demo.model.Column;
import com.veerajk.demo.repo.ColumnRepo;
@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("api/boards/{boardid}/columns")
public class ColumnController {

	@Autowired
	ColumnService columnService;

	@PostMapping
	public ResponseEntity<Column> addColumn(@RequestBody Column column,@PathVariable Long boardid){
		return columnService.addColumn(column,boardid);
	}


	@GetMapping
	public ResponseEntity<List<Column>> getAllColumns(@PathVariable Long boardid){
		return columnService.getAllColumns(boardid); //
	}

	@GetMapping("{id}")
	public ResponseEntity<Optional<Column>> getColumn(@PathVariable Long id){
		return columnService.getColumn(id);
	}

	@PutMapping
	public ResponseEntity<Column> removeColumn(@RequestBody ColumnRemoveRequest columnname, @PathVariable Long boardid){
		return 	columnService.removeColumn(columnname,boardid);
	}
}
