package com.veerajk.demo.services;

import com.veerajk.demo.dtos.TaskDto;

public interface TaskService {
    TaskDto addTask(TaskDto taskRequestDto,Long columnid) throws Exception;
}
