package com.veerajk.demo.services;

import com.veerajk.demo.dtos.ColumnDto;

import java.util.List;

public interface ColumnService {
    ColumnDto addColumn(ColumnDto columnDto,Long teamid) throws Exception;
    List<ColumnDto> getAllColumns(Long sprintid) throws Exception;
    ColumnDto getColumn(Long id) throws Exception;
    String removeColumn(Long id,Long targetid) throws Exception;
}
