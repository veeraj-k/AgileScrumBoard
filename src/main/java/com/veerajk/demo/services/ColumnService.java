package com.veerajk.demo.services;

import com.veerajk.demo.dtos.ColumnDto;

import java.util.List;

public interface ColumnService {
    ColumnDto addColumn(ColumnDto columnDto,Long boardid);
    List<ColumnDto> getAllColumns(Long boardid) throws Exception;
    ColumnDto getColumn(Long id) throws Exception;
}
