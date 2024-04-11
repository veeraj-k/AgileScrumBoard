package com.veerajk.demo.services.impl;

import com.veerajk.demo.dtos.TaskDto;
import com.veerajk.demo.model.Board;
import com.veerajk.demo.model.Column;
import com.veerajk.demo.model.Task;
import com.veerajk.demo.repo.BoardRepo;
import com.veerajk.demo.repo.ColumnRepo;
import com.veerajk.demo.repo.TaskRepo;
import com.veerajk.demo.repo.UserRepo;
import com.veerajk.demo.requests.TaskRequest;
import com.veerajk.demo.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    TaskRepo taskRepo;
    ColumnRepo columnRepo;
    BoardRepo boardRepo;
    UserRepo userRepo;

    @Autowired
    public TaskServiceImpl(TaskRepo taskRepo, ColumnRepo columnRepo, BoardRepo boardRepo, UserRepo userRepo) {
        this.taskRepo = taskRepo;
        this.columnRepo = columnRepo;
        this.boardRepo = boardRepo;
        this.userRepo = userRepo;
    }

    public TaskDto getTask(Long id) throws Exception{
        Task task = taskRepo.findById(id).orElseThrow(()-> new Exception("Task not found!"));

        return mapToDto(task);
    }

//    public ResponseEntity<List<Task>> getColumnTasks(Long columnid){
//        try{
//            return  new ResponseEntity<>(columnRepo.findById(columnid).get().getTasks(), HttpStatus.OK);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
//    }

    public TaskDto addTask(TaskDto taskRequestDto , Long columnid) throws Exception{
        Column column = columnRepo.findById(columnid).orElseThrow(()-> new Exception("Column with specified id not found!"));
        Task task = mapToEntity(taskRequestDto);
        TaskDto taskResponseDto = mapToDto(taskRepo.save(task));
        return taskResponseDto;
    }

//    public ResponseEntity<List<Task>> getBoardTasks(Long boardid) {
//        try{
//            List<Task> tasks = new ArrayList<>();
//            List<Column> columns =  boardRepo.findById(boardid).get().getColumns();
//
//            for(int i = 0 ;i<columns.size();i++){
//                tasks.addAll(columns.get(i).getTasks());
//            }
//            return  new ResponseEntity<>(tasks,HttpStatus.OK);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return new ResponseEntity(null,HttpStatus.BAD_REQUEST);
//    }

    public String removeTask(Long taskid) throws Exception{
        Task task = taskRepo.findById(taskid).orElseThrow(()-> new Exception("Task not found!"));
        taskRepo.deleteById(taskid);
        return "Task deleted successfully!";

    }

    private TaskDto mapToDto(Task task){
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setType(task.getType());
        taskDto.setStoryPoints(task.getStoryPoints());

        return taskDto;
    }
    private Task mapToEntity(TaskDto taskDto){
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStoryPoints(taskDto.getStoryPoints());
        task.setType(taskDto.getType());

        return task;
    }
}
