package com.veerajk.demo.controller;

import java.util.List;
import java.util.Optional;

import com.veerajk.demo.dtos.TaskDto;
import com.veerajk.demo.model.Column;
import com.veerajk.demo.requests.MoveTaskRequest;
import com.veerajk.demo.requests.TaskRequest;
import com.veerajk.demo.services.impl.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.veerajk.demo.repo.*;
import com.veerajk.demo.model.Task;

@RestController
@RequestMapping("api/boards/")
@CrossOrigin("http://localhost:3000/")
public class TaskController {

	
	@Autowired
	TaskRepo taskRepo;
	@Autowired
	ColumnRepo columnRepo;
	@Autowired
	TaskCommentRepo taskCommentRepo;

	@Autowired
	TaskServiceImpl taskServiceImpl;

	@PostMapping("{boardid}/columns/{columnid}/tasks")
	public ResponseEntity<TaskDto> addTask(@RequestBody TaskDto taskreq , @PathVariable Long columnid) throws Exception{
		return new ResponseEntity<>(taskServiceImpl.addTask(taskreq,columnid), HttpStatus.CREATED);
	}
	
//	@GetMapping("columns/tasks")
//	public ResponseEntity<List<Task>> getAllTasks() {
//		return taskServiceImpl.getAlltasks();
//	}
	
	@GetMapping("columns/tasks/{id}")
	public ResponseEntity<TaskDto> getTask(@PathVariable Long id) throws Exception {
		return ResponseEntity.ok(taskServiceImpl.getTask(id));
	}

//	---- To be implemented later ----
//	@GetMapping("columns/{columnid}/tasks")
//	public ResponseEntity<List<Task>> getColumnTasks(@PathVariable Long columnid){
//		return taskServiceImpl.getColumnTasks(columnid);
//	}
//
//	@GetMapping("{boardid}/columns/tasks")
//	public ResponseEntity<List<Task>> getBoardTasks(@PathVariable Long boardid){
//		return taskServiceImpl.getBoardTasks(boardid);
//	}
//
	@DeleteMapping("columns/tasks/{taskid}")
	public ResponseEntity<String> removeTask(@PathVariable Long taskid) throws Exception{
		return ResponseEntity.ok(taskServiceImpl.removeTask(taskid));

	}
	@PutMapping("/update")
	public void checking(@RequestBody MoveTaskRequest moveTaskRequest){
		System.out.println("Hello world");
		Task t = taskRepo.findById(moveTaskRequest.getId());
		Column c = columnRepo.findById(moveTaskRequest.getColumn_id());
		t.setColumn_id(c);

		taskRepo.save(t);

	}
}
