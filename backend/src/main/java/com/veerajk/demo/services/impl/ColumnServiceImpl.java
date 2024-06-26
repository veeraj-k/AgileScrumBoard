package com.veerajk.demo.services.impl;

import com.veerajk.demo.dtos.ColumnDto;
import com.veerajk.demo.dtos.ColumnWithoutTaskDto;
import com.veerajk.demo.dtos.TaskDto;
import com.veerajk.demo.model.Sprint;
import com.veerajk.demo.model.Column;
import com.veerajk.demo.model.Task;
import com.veerajk.demo.model.Team;
import com.veerajk.demo.repo.SprintRepo;
import com.veerajk.demo.repo.ColumnRepo;
import com.veerajk.demo.repo.TaskRepo;
import com.veerajk.demo.repo.TeamRepo;
import com.veerajk.demo.services.ColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColumnServiceImpl implements ColumnService {

    private ColumnRepo columnRepo;
    private SprintRepo sprintRepo;
    private TaskRepo taskRepo;
    private TaskServiceImpl taskService;
    private final TeamRepo teamRepo;

    @Autowired
    public ColumnServiceImpl(ColumnRepo columnRepo, SprintRepo sprintRepo, TaskServiceImpl taskService, TaskRepo taskRepo,
                             TeamRepo teamRepo){
        this.columnRepo = columnRepo;
        this.sprintRepo = sprintRepo;
        this.taskRepo = taskRepo;
        this.taskService = taskService;
        this.teamRepo = teamRepo;
    }


    public ColumnDto addColumn(ColumnDto columnDto, Long teamid) throws Exception {
        Team team = teamRepo.findById(teamid).orElseThrow(()->new Exception("Team not found!"));
        Sprint sprint = team.getSprint();
        if(sprint == null){
            throw new Exception("Active sprint not found!");
        }
//        Sprint sprint = sprintRepo.findById(sprintid).orElseThrow();
        Column column = mapToEntity(columnDto);
        column.setSprint(sprint);
        column.setLocation(columnRepo.findMaxLocation(sprint.getId()) != null ? columnRepo.findMaxLocation(sprint.getId()) + 1 : 1);
        column.setIsdone(false);
        return mapColumnToDto(columnRepo.save(column));
    }
    public List<ColumnDto> getAllColumns(Long teamid) throws Exception {
        Team team = teamRepo.findById(teamid).orElseThrow(()->new Exception("Team not found!"));
        Sprint sprint = team.getSprint();
        if(sprint == null){
            throw new Exception("Active sprint not found!");
        }
        List<Column> columns = columnRepo.findAllBySprintOrderByLocationDesc(sprint);
        List<ColumnDto> columnDtoList = columns.stream().map((column -> mapColumnToDto(column))).toList();

        return columnDtoList;
    }

    public ColumnDto getColumn(Long id) throws Exception{
        Column column = columnRepo.findById(id).orElseThrow(()->new Exception("Column not found!"));

        return mapColumnToDto(column);
    }

    public String  removeColumn(Long id,Long targetid) throws Exception {
        Column column = columnRepo.findById(id).orElseThrow();
        boolean isDone = column.isIsdone();
        if(isDone){
            throw new Exception("Column with done status cannot be removed!");
        }
        Column targetColumn = columnRepo.findById(targetid).orElseThrow(() -> new Exception("Target column not found!"));
        List<Task> tasks = column.getTasks();
        if (column.getTasks().size()!= 0) {

            tasks.forEach(task -> {
                task.setColumn_id(targetColumn);
                task.setIscompleted(targetColumn.isIsdone());
            });

            taskRepo.saveAll(tasks);
            columnRepo.delete(column);

            return "Columns deleted and Tasks moved to - "+ targetColumn.getTitle();
        }
        columnRepo.deleteById(id);
        return "Column deleted";
    }

    public List<ColumnWithoutTaskDto> getOnlyColumns(Long teamid) throws Exception {
        Team team = teamRepo.findById(teamid).orElseThrow();
        Sprint sprint = team.getSprint();
        if(sprint==null){
            throw new Exception("No Active sprint found");
        }
        List<Column> columns = sprint.getColumns();
        return columns.stream().map((column -> {ColumnWithoutTaskDto col = new ColumnWithoutTaskDto(column.getId(),column.getTitle()); return col;})).collect(Collectors.toList());
    }

    private ColumnDto mapColumnToDto(Column column){
        ColumnDto columnDto = new ColumnDto();
        columnDto.setId(column.getId());
        columnDto.setTitle(column.getTitle());
        columnDto.setLocation(column.getLocation());
        List<TaskDto> taskList = new ArrayList<>();
        if(column.getTasks()!=null){
            columnDto.setTasks(column.getTasks().stream().map((task) -> taskService.mapTaskToDto(task)).toList());
        }
        else{
            columnDto.setTasks(
                    taskList
            );
        }


        return columnDto;
    }
    private Column mapToEntity(ColumnDto columnDto){
        Column column = new Column();
        column.setTitle(columnDto.getTitle());
        column.setLocation(columnDto.getLocation());

        return column;
    }
}
