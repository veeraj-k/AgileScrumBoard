package com.veerajk.demo.services;

import com.veerajk.demo.dtos.ColumnDto;

public interface ColumnService {
    ColumnDto addColumn(ColumnDto columnDto,Long boardid);
}
