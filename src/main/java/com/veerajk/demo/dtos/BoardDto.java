package com.veerajk.demo.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class BoardDto {
    private long id;
    private String name;
    private String description;
    private Date startdate;
    private int duration;

}
