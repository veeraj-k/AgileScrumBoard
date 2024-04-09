package com.veerajk.demo.services;

import com.veerajk.demo.dtos.BoardDto;
import com.veerajk.demo.model.Board;
import com.veerajk.demo.repo.BoardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    BoardRepo boardRepo;

    public BoardDto addBoard(BoardDto boardDto){
        Board board = mapToEntity(boardDto);
        boardRepo.save(board);
        return boardDto;
    }

    public BoardDto getBoard(Long id) throws Exception {
        Board board = boardRepo.findById(id).orElseThrow(() -> new Exception("Board not found"));
        BoardDto boardDto = mapToDto(board);
        return boardDto;
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

    private BoardDto mapToDto(Board board){
        BoardDto boardDto = new BoardDto();
        boardDto.setId(board.getId());
        boardDto.setName(board.getName());
        boardDto.setStartdate(board.getStartdate());
        boardDto.setDuration(board.getDuration());
        return boardDto;
    }

    private Board mapToEntity(BoardDto boardDto){
        Board board = new Board();
        board.setName(boardDto.getName());
        board.setDescription(boardDto.getDescription());
        board.setStartdate(boardDto.getStartdate());
        board.setDuration(boardDto.getDuration());

        return board;
    }
}
