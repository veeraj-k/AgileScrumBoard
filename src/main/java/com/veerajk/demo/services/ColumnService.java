package com.veerajk.demo.services;

import com.veerajk.demo.model.Column;
import com.veerajk.demo.repo.BoardRepo;
import com.veerajk.demo.repo.ColumnRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ColumnService {
    @Autowired
    ColumnRepo columnRepo;

    @Autowired
    BoardRepo boardRepo;
    public ResponseEntity<Column> addColumn(Column column,Long boardid){
        try{
            column.setBoard(boardRepo.findById(boardid).orElse(null));
            columnRepo.save(column);
            return  new ResponseEntity<>(column,HttpStatus.CREATED);
        }catch(Exception e){e.printStackTrace();}
        return new ResponseEntity<>(new Column(), HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<List<Column>> getAllColumns(){
        try{

            return  new ResponseEntity<>(columnRepo.findAllByOrderByLocationAsc(),HttpStatus.OK);
        }catch(Exception e){e.printStackTrace();}
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<Optional<Column>> getColumn(Long id){
        try{

            return  new ResponseEntity<>(columnRepo.findById(id),HttpStatus.OK);
        }catch(Exception e){e.printStackTrace();}
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
