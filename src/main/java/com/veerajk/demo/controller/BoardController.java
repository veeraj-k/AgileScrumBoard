package com.veerajk.demo.controller;

import com.veerajk.demo.dtos.BoardDto;
import com.veerajk.demo.model.Board;
import com.veerajk.demo.services.BoardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("http://localhost:3000/")
@Controller
@RequestMapping("api/boards")
public class BoardController {

    @Autowired
    BoardServiceImpl boardServiceImpl;

    @PostMapping
    public ResponseEntity<BoardDto> addBoard(@RequestBody BoardDto boardDto){
        return new ResponseEntity<>(boardDto, HttpStatus.CREATED);
    }
    @GetMapping("{boardid}")
    public  ResponseEntity getBoard(@PathVariable Long boardid){
        return boardServiceImpl.getBoard(boardid);
    }

    @GetMapping
    public  ResponseEntity getAllBoards(){
        return boardServiceImpl.getAllBoards();
    }

}
