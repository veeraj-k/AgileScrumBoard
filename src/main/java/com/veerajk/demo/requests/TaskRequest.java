package com.veerajk.demo.requests;

import com.veerajk.demo.enums.TaskType;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {
    String title;
    String description;
    TaskType type;
    int storyPoints;
    String columnName;
    Long boardId;
    Long userId;
}
