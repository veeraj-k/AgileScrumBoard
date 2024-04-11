package com.veerajk.demo.services;

import com.veerajk.demo.dtos.TaskDto;
import com.veerajk.demo.requests.MoveTaskRequest;

public interface TaskService {
    TaskDto addTask(TaskDto taskRequestDto,Long columnid) throws Exception;
    TaskDto getTask(Long id) throws Exception;
    String removeTask(Long id) throws Exception;
    TaskDto moveTask(MoveTaskRequest taskRequest) throws Exception;
}
