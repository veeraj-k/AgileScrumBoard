package com.veerajk.demo.services;

import com.veerajk.demo.dtos.BoardDto;
import com.veerajk.demo.model.Board;
import com.veerajk.demo.repo.BoardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    BoardRepo boardRepo;

    public BoardDto addBoard(BoardDto boardDto){
        Board board = mapToEntity(boardDto);
        Board boardRes = boardRepo.save(board);
        BoardDto boardDtoRes = mapToDto(boardRes);

        return boardDtoRes;
    }

    public BoardDto getBoard(Long id) throws Exception {
        Board board = boardRepo.findById(id).orElseThrow(() -> new Exception("Board not found"));
        BoardDto boardDto = mapToDto(board);
        return boardDto;
    }

    public List<BoardDto> getAllBoards() {
        List<Board> boardList = boardRepo.findAll();
        List<BoardDto> boardDtos = boardList.stream().map((board -> mapToDto(board))).toList();

        return boardDtos;
    }

    private BoardDto mapToDto(Board board){
        BoardDto boardDto = new BoardDto();
        boardDto.setId(board.getId());
        boardDto.setName(board.getName());
        boardDto.setStartdate(board.getStartdate());
        boardDto.setDuration(board.getDuration());
        boardDto.setDescription(board.getDescription());
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
