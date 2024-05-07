package com.veerajk.demo.services.impl;

import com.veerajk.demo.dtos.ColumnDto;
import com.veerajk.demo.dtos.ColumnWithoutTaskDto;
import com.veerajk.demo.dtos.TaskDto;
import com.veerajk.demo.model.Sprint;
import com.veerajk.demo.model.Column;
import com.veerajk.demo.model.Task;
import com.veerajk.demo.repo.SprintRepo;
import com.veerajk.demo.repo.ColumnRepo;
import com.veerajk.demo.repo.TaskRepo;
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

    @Autowired
    public ColumnServiceImpl(ColumnRepo columnRepo, SprintRepo sprintRepo, TaskServiceImpl taskService, TaskRepo taskRepo){
        this.columnRepo = columnRepo;
        this.sprintRepo = sprintRepo;
        this.taskRepo = taskRepo;
        this.taskService = taskService;
    }


    public ColumnDto addColumn(ColumnDto columnDto, Long sprintid){
        Sprint sprint = sprintRepo.findById(sprintid).orElseThrow();
        Column column = mapToEntity(columnDto);
        column.setSprint(sprint);
        column.setLocation(columnRepo.findMaxLocation(sprintid) != null ? columnRepo.findMaxLocation(sprintid) + 1 : 1);

        return mapColumnToDto(columnRepo.save(column));
    }
    public List<ColumnDto> getAllColumns(Long sprintid) throws Exception {
        Sprint sprint = sprintRepo.findById(sprintid).orElseThrow(()->new Exception("sprint not found!"));
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
        Column targetColumn = columnRepo.findById(targetid).orElseThrow(() -> new Exception("Target column not found!"));
        List<Task> tasks = column.getTasks();
        if (column.getTasks().size()!= 0) {

            tasks.forEach(task -> {
                task.setColumn_id(targetColumn);
            });

            taskRepo.saveAll(tasks);
            columnRepo.deleteById(id);

            return "Columns deleted and Tasks moved to - "+ targetColumn.getTitle();
        }

        columnRepo.deleteById(id);
        return "Column deleted";
    }

    public List<ColumnWithoutTaskDto> getOnlyColumns(Long sprintid){
        List<Column> columns = sprintRepo.findById(sprintid).orElseThrow().getColumns();
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
