package com.veerajk.demo.services.impl;

import com.veerajk.demo.dtos.ColumnWithoutTaskDto;
import com.veerajk.demo.dtos.CommentDto;
import com.veerajk.demo.dtos.TaskDto;
import com.veerajk.demo.model.Column;
import com.veerajk.demo.model.Task;
import com.veerajk.demo.repo.ColumnRepo;
import com.veerajk.demo.repo.TaskRepo;
import com.veerajk.demo.repo.UserRepo;
import com.veerajk.demo.requests.MoveTaskRequest;
import com.veerajk.demo.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    TaskRepo taskRepo;
    ColumnRepo columnRepo;
    CommentServiceImpl commentService;
    UserRepo userRepo;

//    SprintRepo sprintRepo;
//    UserRepo userRepo;

    @Autowired
    public TaskServiceImpl(TaskRepo taskRepo, ColumnRepo columnRepo,CommentServiceImpl commentService,UserRepo userRepo) {
        this.taskRepo = taskRepo;
        this.columnRepo = columnRepo;
        this.commentService = commentService;
//        this.sprintRepo = sprintRepo;
        this.userRepo = userRepo;
    }

    public TaskDto getTask(Long id) throws Exception{
        Task task = taskRepo.findById(id).orElseThrow(()-> new Exception("Task not found!"));

        return mapTaskToDto(task);
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

    public TaskDto addTask(TaskDto taskRequestDto , Long columnid,Long userid) throws Exception{
        Column column = columnRepo.findById(columnid).orElseThrow(()-> new Exception("Column with specified id not found!"));
        Task task = mapToEntity(taskRequestDto);
        task.setUser(userRepo.findById(userid).get());
        task.setColumn_id(column);
        task.setIscompleted(column.isIsdone());
        task.setCreated(new Date());
        task.setUpdated(new Date());
        TaskDto taskResponseDto = mapTaskToDto(taskRepo.save(task));
        return taskResponseDto;
    }

//    public ResponseEntity<List<Task>> getBoardTasks(Long boardid) {
//        try{
//            List<Task> tasks = new ArrayList<>();
//            List<Column> columns =  sprintRepo.findById(boardid).get().getColumns();
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
    public String editTaskTitle(Long taskid,String newTitle) throws Exception{
        Task task = taskRepo.findById(taskid).orElseThrow(()-> new Exception("Task not found!"));
        task.setTitle(newTitle);
        task.setUpdated(new Date());
        taskRepo.save(task);
        return "Title updated successfully!";
    }

    public String editTaskDescription(Long taskid,String newDescription) throws Exception{
        Task task = taskRepo.findById(taskid).orElseThrow(()-> new Exception("Task not found!"));
        task.setDescription(newDescription);
        task.setUpdated(new Date());
        taskRepo.save(task);
        return "Description updated successfully!";
    }


    public String removeTask(Long taskid) throws Exception{
        Task task = taskRepo.findById(taskid).orElseThrow(()-> new Exception("Task not found!"));
        taskRepo.deleteById(taskid);
        return "Task deleted successfully!";

    }

    public TaskDto moveTask(MoveTaskRequest taskRequest) throws Exception {
        Column column = columnRepo.findById(taskRequest.getColumn_id()).orElseThrow(()-> new Exception("Specified column not found!"));
        Task task = taskRepo.findById(taskRequest.getId()).orElseThrow(()-> new Exception("Task not found!"));
        task.setColumn_id(column);
        task.setIscompleted(column.isIsdone());
        task.setUpdated(new Date());
        Task updatedtask = taskRepo.save(task);
        return mapTaskToDto(updatedtask);
    }

    public ColumnWithoutTaskDto getColumnOfTask(Long id){
        Task task = taskRepo.findById(id).orElseThrow();
        Column col = task.getColumn_id();
        return new ColumnWithoutTaskDto(col.getId(), col.getTitle());
    }
    protected TaskDto mapTaskToDto(Task task){
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setType(task.getType());
        taskDto.setStoryPoints(task.getStoryPoints());
        List<CommentDto> commentDtoList = new ArrayList<>();
        if(task.getComments()!=null){
            commentDtoList = task.getComments().stream().map((comment) -> commentService.mapCommentToDto(comment)).toList();
        }
        taskDto.setComments(commentDtoList);
        taskDto.setUser(task.getUser());
        taskDto.setCreated(task.getCreated());
        taskDto.setUpdated(task.getUpdated());
        return taskDto;
    }
    protected Task mapToEntity(TaskDto taskDto){
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStoryPoints(taskDto.getStoryPoints());
        task.setType(taskDto.getType());
//        task.setUser(userRepo.findById(1L).get());
        return task;
    }
}
