package com.veerajk.demo.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class BoardDto {
    private long id;
    private String name;
    private String description;
    private LocalDate startdate;
    private int duration;

}
