package com.veerajk.demo.services;

import com.veerajk.demo.dtos.BoardDto;

public interface BoardService {

    BoardDto addBoard(BoardDto boardDto);
    BoardDto getBoard(Long id) throws Exception;
}
