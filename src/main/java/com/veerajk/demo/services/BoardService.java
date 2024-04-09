package com.veerajk.demo.services;

import com.veerajk.demo.dtos.BoardDto;

import java.util.List;

public interface BoardService {

    BoardDto addBoard(BoardDto boardDto);
    BoardDto getBoard(Long id) throws Exception;
    List<BoardDto> getAllBoards();
}
