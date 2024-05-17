package com.veerajk.demo.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ColumnDto {
    private Long id;
    private String title;
    private int location;
    List<TaskDto> tasks;
}
