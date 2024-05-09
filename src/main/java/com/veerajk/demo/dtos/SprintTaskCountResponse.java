package com.veerajk.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SprintTaskCountResponse {
    private Long completedTaskCount;
    private Long remainingTaskCount;
}
