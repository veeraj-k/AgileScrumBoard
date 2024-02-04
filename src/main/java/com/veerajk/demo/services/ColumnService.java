package com.veerajk.demo.services;

import com.veerajk.demo.model.Board;
import com.veerajk.demo.model.Column;
import com.veerajk.demo.repo.BoardRepo;
import com.veerajk.demo.repo.ColumnRepo;
import com.veerajk.demo.requests.ColumnRemoveRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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
            Optional board = boardRepo.findById(boardid);
            if(board.get()!=null) {
                column.setBoard((Board) board.get());
                columnRepo.save(column);
                return new ResponseEntity<>(column, HttpStatus.CREATED);
            }
            else {
                return new ResponseEntity<>(new Column(),HttpStatus.NOT_FOUND);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Column(), HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<List<Column>> getAllColumns(Long boardid){
        try{
//            List<Column> cols = columnRepo.findByBoardO(boardid);
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

    public ResponseEntity<Column> removeColumn(ColumnRemoveRequest columnName, Long boardid) {
        try{
            Optional<Board> board = boardRepo.findById(boardid);

            if(board.get()!=null) {
                List<Column> cols = board.get().getColumns();
                for(int i=0;i<cols.size();i++){
                    System.out.println(cols.get(i).getTitle() +":" +columnName.getTitle());
                    if(cols.get(i).getTitle().equals(columnName.getTitle())){
                        if (cols.get(i).getTasks().size()!=0){
                            return new ResponseEntity<>(null,HttpStatus.CONFLICT);
                        }
                        cols.get(i).setIsvisible(false);
                        columnRepo.save(cols.get(i));
                        return new ResponseEntity<>(cols.get(i), HttpStatus.OK);
                    }
                }

            }
            else {
                return new ResponseEntity<>(new Column(),HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
    }
}
