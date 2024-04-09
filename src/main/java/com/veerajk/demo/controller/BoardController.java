package com.veerajk.demo.controller;

import com.veerajk.demo.dtos.BoardDto;
import com.veerajk.demo.services.BoardService;
import com.veerajk.demo.services.BoardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin("http://localhost:3000/")
@Controller
@RequestMapping("api/boards")
public class BoardController {

    @Autowired
    BoardServiceImpl boardService;

    @PostMapping
    public ResponseEntity<BoardDto> addBoard(@RequestBody BoardDto boardDto){
        return new ResponseEntity<>(boardService.addBoard(boardDto), HttpStatus.CREATED);
    }
    @GetMapping("{boardid}")
    public  ResponseEntity<BoardDto> getBoard(@PathVariable Long boardid) throws Exception {

        return ResponseEntity.ok(boardService.getBoard(boardid));
    }

    @GetMapping
    public  ResponseEntity<List<BoardDto>> getAllBoards(){
        return ResponseEntity.ok(boardService.getAllBoards());
    }

}
