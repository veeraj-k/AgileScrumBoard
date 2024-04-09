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
    public ResponseEntity<List<Column>> getAllColumns(Long boardid){
        try{
            List<Column> cols = boardRepo.findById(boardid).get().getColumns();
            cols.sort((Column c1, Column c2) -> c1.getLocation() - c2.getLocation());
            return new ResponseEntity<>(cols, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Optional<Column>> getColumn(Long id){
        try{
            return new ResponseEntity<>(columnRepo.findById(id),HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Column> removeColumn(ColumnRemoveRequest columnId, Long boardid) {
        try{

            Optional<Column> column = columnRepo.findById(columnId.getColumnId());
            if(column.get()!=null){

                Column col = column.get();
                List<Task> tasks = col.getTasks();

                for(int i=0;i<tasks.size();i++){
                    if(tasks.get(i).isIsvisible()){
                        return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
                    }
                }
                column.get().setIsvisible(false);
                columnRepo.save(column.get());
                return new ResponseEntity<>(column.get(),HttpStatus.OK);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
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
