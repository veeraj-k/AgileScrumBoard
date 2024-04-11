package com.veerajk.demo.services.impl;

import com.veerajk.demo.dtos.TaskDto;
import com.veerajk.demo.model.Column;
import com.veerajk.demo.model.Task;
import com.veerajk.demo.repo.ColumnRepo;
import com.veerajk.demo.repo.TaskRepo;
import com.veerajk.demo.requests.MoveTaskRequest;
import com.veerajk.demo.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    TaskRepo taskRepo;
    ColumnRepo columnRepo;
//    BoardRepo boardRepo;
//    UserRepo userRepo;

    @Autowired
    public TaskServiceImpl(TaskRepo taskRepo, ColumnRepo columnRepo) {
        this.taskRepo = taskRepo;
        this.columnRepo = columnRepo;
//        this.boardRepo = boardRepo;
//        this.userRepo = userRepo;
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

    public TaskDto addTask(TaskDto taskRequestDto , Long columnid) throws Exception{
        Column column = columnRepo.findById(columnid).orElseThrow(()-> new Exception("Column with specified id not found!"));
        Task task = mapToEntity(taskRequestDto);
        TaskDto taskResponseDto = mapTaskToDto(taskRepo.save(task));
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

    public TaskDto moveTask(MoveTaskRequest taskRequest) throws Exception {
        Column column = columnRepo.findById(taskRequest.getColumn_id()).orElseThrow(()-> new Exception("Specified column not found!"));
        Task task = taskRepo.findById(taskRequest.getId()).orElseThrow(()-> new Exception("Task not found!"));
        task.setColumn_id(column);
        Task updatedtask = taskRepo.save(task);
        return mapTaskToDto(updatedtask);
    }

    protected TaskDto mapTaskToDto(Task task){
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setType(task.getType());
        taskDto.setStoryPoints(task.getStoryPoints());

        return taskDto;
    }
    protected Task mapToEntity(TaskDto taskDto){
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStoryPoints(taskDto.getStoryPoints());
        task.setType(taskDto.getType());

        return task;
    }
}
