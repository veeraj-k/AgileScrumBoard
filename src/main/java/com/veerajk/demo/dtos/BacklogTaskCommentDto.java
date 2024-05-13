package com.veerajk.demo.dtos;

import com.veerajk.demo.model.User;
import lombok.Data;

@Data
public class BacklogTaskCommentDto {
    private Long id;
    private String description;
    private User user;
}
