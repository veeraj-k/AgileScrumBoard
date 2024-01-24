package com.veerajk.demo.controller;

import java.util.List;
import java.util.Optional;

import com.veerajk.demo.PostData;
import com.veerajk.demo.model.Column;
import com.veerajk.demo.model.TaskComment;
import com.veerajk.demo.requests.MoveTaskRequest;
import com.veerajk.demo.requests.TaskRequest;
import com.veerajk.demo.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.veerajk.demo.repo.*;
import com.veerajk.demo.model.Task;

import javax.xml.stream.events.Comment;

@RestController
//@RequestMapping("/board/{id}/")
@CrossOrigin("http://localhost:3000/")
public class TaskController {

	
	@Autowired
	TaskRepo taskRepo;
	@Autowired
	ColumnRepo columnRepo;
	@Autowired
	TaskCommentRepo taskCommentRepo;

	@Autowired
	TaskService taskService;

	@PostMapping("/addTask")
	public ResponseEntity<Task> addTask(@RequestBody TaskRequest taskreq){
		return taskService.addTask(taskreq);
	}
	
	@GetMapping("/tasks")
	public ResponseEntity<List<Task>> getAllTasks() {
		return taskService.getAlltasks();
	}
	
	@GetMapping("/task/{id}")
	public ResponseEntity<Optional<Task>> getTask(@PathVariable Long id) {
		return taskService.getTask(id);
	}

	@GetMapping("/column/{id}/tasks")
	public ResponseEntity<List<Task>> getColumnTasks(@PathVariable Long id){
		return taskService.getColumnTasks(id);
	}



	@PutMapping("/checking")
	public void checking(@RequestBody MoveTaskRequest moveTaskRequest){
		System.out.println("Hello world");
		Task t = taskRepo.findById(moveTaskRequest.getId());
		Column c = columnRepo.findById(moveTaskRequest.getColumn_id());
		t.setColumn_id(c);

		taskRepo.save(t);

	}
}
