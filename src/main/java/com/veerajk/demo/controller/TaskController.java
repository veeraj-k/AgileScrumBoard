package com.veerajk.demo.controller;

import com.veerajk.demo.dtos.ColumnWithoutTaskDto;
import com.veerajk.demo.dtos.TaskDto;
import com.veerajk.demo.requests.MoveTaskRequest;
import com.veerajk.demo.services.impl.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.veerajk.demo.repo.*;

@RestController
@RequestMapping("api/teams/{teamid}/sprint/columns")
@CrossOrigin("http://localhost:3000/")
public class TaskController {

//
//	@Autowired
//	TaskRepo taskRepo;
//	@Autowired
//	ColumnRepo columnRepo;
//	@Autowired
//	TaskCommentRepo taskCommentRepo;

	@Autowired
	TaskServiceImpl taskServiceImpl;

	@PostMapping("/{columnid}/tasks/user/{userid}")
	public ResponseEntity<TaskDto> addTask(@RequestBody TaskDto taskreq , @PathVariable Long columnid,@PathVariable Long userid) throws Exception{
		return new ResponseEntity<>(taskServiceImpl.addTask(taskreq,columnid,userid), HttpStatus.CREATED);
	}

	@PutMapping("/tasks/{id}/editTitle")
	public ResponseEntity<String> editTaskTitle(@PathVariable Long id,@RequestBody String newTitle) throws Exception {
		return ResponseEntity.ok(taskServiceImpl.editTaskTitle(id,newTitle));
	}
	@PutMapping("/tasks/{id}/editDescription")
	public ResponseEntity<String> editTaskDescription(@PathVariable Long id,@RequestBody String newDescription) throws Exception {
		return ResponseEntity.ok(taskServiceImpl.editTaskDescription(id,newDescription));
	}

//	@GetMapping("columns/tasks")
//	public ResponseEntity<List<Task>> getAllTasks() {
//		return taskServiceImpl.getAlltasks();
//	}
	
	@GetMapping("/tasks/{id}")
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
	@DeleteMapping("/tasks/{taskid}")
	public ResponseEntity<String> removeTask(@PathVariable Long taskid) throws Exception{
		return ResponseEntity.ok(taskServiceImpl.removeTask(taskid));

	}
	@PutMapping("/tasks/movetask")
	public ResponseEntity<TaskDto> moveTask(@RequestBody MoveTaskRequest moveTaskRequest) throws Exception {

		return ResponseEntity.ok(taskServiceImpl.moveTask(moveTaskRequest));
	}

	@GetMapping("/tasks/{id}/getColumn")
	public ResponseEntity<ColumnWithoutTaskDto> getColumnOfTask(@PathVariable Long id){
		return ResponseEntity.ok(taskServiceImpl.getColumnOfTask(id));
	}
}
