package com.veerajk.demo.services;

import com.veerajk.demo.dtos.BacklogDto;

public interface BacklogService {
    BacklogDto getBacklog(Long teamid) throws Exception;
}
