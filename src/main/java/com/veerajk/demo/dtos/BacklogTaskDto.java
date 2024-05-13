package com.veerajk.demo.dtos;

import com.veerajk.demo.enums.TaskType;
import com.veerajk.demo.model.User;
import lombok.Data;

import java.util.List;
@Data
public class BacklogTaskDto {
    private Long id;
    private String title;
    private String description;
    private TaskType type;
    private Integer storyPoints;
    private List<BacklogTaskCommentDto> comments;
    private User user;
    private String sprint;
}
