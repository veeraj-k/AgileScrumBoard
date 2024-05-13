package com.veerajk.demo.dtos;

import lombok.Data;

import java.util.List;

@Data
public class BacklogDto {
    private Long id;
    private List<BacklogTaskDto> tasks;
}
