package com.veerajk.demo.services.impl;

import com.veerajk.demo.dtos.ColumnDto;
import com.veerajk.demo.dtos.TaskDto;
import com.veerajk.demo.model.Board;
import com.veerajk.demo.model.Column;
import com.veerajk.demo.model.Task;
import com.veerajk.demo.repo.BoardRepo;
import com.veerajk.demo.repo.ColumnRepo;
import com.veerajk.demo.services.ColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ColumnServiceImpl implements ColumnService {

    private ColumnRepo columnRepo;
    private BoardRepo boardRepo;
    private TaskServiceImpl taskService;

    @Autowired
    public ColumnServiceImpl(ColumnRepo columnRepo,BoardRepo boardRepo,TaskServiceImpl taskService){
        this.columnRepo = columnRepo;
        this.boardRepo = boardRepo;
        this.taskService = taskService;
    }


    public ColumnDto addColumn(ColumnDto columnDto, Long boardid){
        Board board = boardRepo.findById(boardid).orElseThrow();
        Column column = mapToEntity(columnDto);
        column.setBoard(board);
        column.setLocation(columnRepo.findMaxLocation(boardid) != null ? columnRepo.findMaxLocation(boardid) + 1 : 1);

        return mapColumnToDto(columnRepo.save(column));
    }
    public List<ColumnDto> getAllColumns(Long boardid) throws Exception {
        Board board = boardRepo.findById(boardid).orElseThrow(()->new Exception("board not found!"));
        List<Column> columns = columnRepo.findAllByBoardOrderByLocationDesc(board);
        List<ColumnDto> columnDtoList = columns.stream().map((column -> mapColumnToDto(column))).toList();

        return columnDtoList;
    }

    public ColumnDto getColumn(Long id) throws Exception{
        Column column = columnRepo.findById(id).orElseThrow(()->new Exception("Column not found!"));

        return mapColumnToDto(column);
    }

    public String  removeColumn(Long id) throws Exception {
        Column column = columnRepo.findById(id).orElseThrow();
        if (column.getTasks().size()!= 0) {
            throw new Exception();
        }
        columnRepo.deleteById(id);
        return "Column deleted";
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
