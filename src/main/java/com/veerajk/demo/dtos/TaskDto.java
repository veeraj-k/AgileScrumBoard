package com.veerajk.demo.dtos;

import com.veerajk.demo.enums.TaskType;
import lombok.Data;

@Data
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private TaskType type;
    private Integer storyPoints;

}
