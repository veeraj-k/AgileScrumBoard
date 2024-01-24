package com.veerajk.demo.services;

import com.veerajk.demo.model.Task;
import com.veerajk.demo.repo.ColumnRepo;
import com.veerajk.demo.repo.TaskRepo;
import com.veerajk.demo.requests.TaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    TaskRepo taskRepo;
    @Autowired
    ColumnRepo columnRepo;
    public ResponseEntity<List<Task>> getAlltasks(){
        try{
            return  new ResponseEntity<>(taskRepo.findAll(),HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public  ResponseEntity<Optional<Task>> getTask(Long id){
        try{
            return  new ResponseEntity<>(taskRepo.findById(id),HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Task>> getColumnTasks(Long id){
        try{
            return  new ResponseEntity<>(columnRepo.findById(id).get().getTasks(), HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Task> addTask(TaskRequest taskRequest){
        try{
            Task t = new Task();
            t.setColumn_id(columnRepo.findByTitle(taskRequest.getColumnName()));
            t.setTitle(taskRequest.getTitle());
            t.setDescription(taskRequest.getDescription());
            t.setType(taskRequest.getType());
            t.setStoryPoints(taskRequest.getStoryPoints());
            taskRepo.save(t);
            return  new ResponseEntity<>(t, HttpStatus.CREATED);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Task(), HttpStatus.BAD_REQUEST);
    }
}
