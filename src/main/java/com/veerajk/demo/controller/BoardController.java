package com.veerajk.demo.controller;

import com.veerajk.demo.model.Board;
import com.veerajk.demo.services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("http://localhost:3000/")
@Controller
@RequestMapping("api/boards")
public class BoardController {

    @Autowired
    BoardService boardService;

    @PostMapping
    public ResponseEntity<Board> addBoard(@RequestBody Board board){
//
        return boardService.addBoard(board);
    }

    @GetMapping("{boardid}")
    public  ResponseEntity getBoard(@PathVariable Long boardid){
        return boardService.getBoard(boardid);
    }

    @GetMapping
    public  ResponseEntity getAllBoards(){
        return boardService.getAllBoards();
    }




}
