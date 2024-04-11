package com.veerajk.demo.services.impl;

import com.veerajk.demo.dtos.ColumnDto;
import com.veerajk.demo.model.Board;
import com.veerajk.demo.model.Column;
import com.veerajk.demo.model.Task;
import com.veerajk.demo.repo.BoardRepo;
import com.veerajk.demo.repo.ColumnRepo;
import com.veerajk.demo.requests.ColumnRemoveRequest;
import com.veerajk.demo.services.ColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ColumnServiceImpl implements ColumnService {

    private ColumnRepo columnRepo;
    private BoardRepo boardRepo;

    @Autowired
    public ColumnServiceImpl(ColumnRepo columnRepo,BoardRepo boardRepo){
        this.columnRepo = columnRepo;
        this.boardRepo = boardRepo;
    }


    public ColumnDto addColumn(ColumnDto columnDto, Long boardid){
        Board board = boardRepo.findById(boardid).orElseThrow();
        Column column = mapToEntity(columnDto);
        column.setBoard(board);
        column.setLocation(columnRepo.findMaxLocation(boardid) != null ? columnRepo.findMaxLocation(boardid) + 1 : 1);

        return mapToDto(columnRepo.save(column));
    }
    public List<ColumnDto> getAllColumns(Long boardid) throws Exception {
        Board board = boardRepo.findById(boardid).orElseThrow(()->new Exception("board not found!"));
        List<Column> columns = columnRepo.findAllByBoardOrderByLocationDesc(board);
        List<ColumnDto> columnDtoList = columns.stream().map((column -> mapToDto(column))).toList();

        return columnDtoList;
    }

    public ColumnDto getColumn(Long id) throws Exception{
        Column column = columnRepo.findById(id).orElseThrow(()->new Exception("Column not found!"));

        return mapToDto(column);
    }

    public String  removeColumn(Long id) throws Exception {
        Column column = columnRepo.findById(id).orElseThrow();
        if (column.getTasks().size()!= 0) {
            throw new Exception();
        }
        columnRepo.deleteById(id);
        return "Column deleted";
    }

    private ColumnDto mapToDto(Column column){
        ColumnDto columnDto = new ColumnDto();
        columnDto.setId(column.getId());
        columnDto.setTitle(column.getTitle());
        columnDto.setLocation(column.getLocation());
        return columnDto;
    }
    private Column mapToEntity(ColumnDto columnDto){
        Column column = new Column();
        column.setTitle(columnDto.getTitle());
        column.setLocation(columnDto.getLocation());

        return column;
    }
}
