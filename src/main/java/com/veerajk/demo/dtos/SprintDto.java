package com.veerajk.demo.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SprintDto {
    private long id;
    private String name;
    private String description;
    private LocalDate startdate;
    private int duration;

}
