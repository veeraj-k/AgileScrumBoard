package com.veerajk.demo.services;

import com.veerajk.demo.model.Board;
import com.veerajk.demo.repo.BoardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    BoardRepo boardRepo;

    public ResponseEntity<Board> addBoard(Board board){
        try{
            boardRepo.save(board);
            return new ResponseEntity<Board>(board, HttpStatus.CREATED);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<Board>(new Board(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity getBoard(Long boardid) {
        try {
            return new ResponseEntity(boardRepo.findById(boardid),HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity(null,HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity getAllBoards() {
        try{
            return new ResponseEntity(boardRepo.findAll(),HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity(null,HttpStatus.BAD_REQUEST);
    }
}
