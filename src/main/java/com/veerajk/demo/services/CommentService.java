package com.veerajk.demo.services;

import com.veerajk.demo.dtos.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto addComment(CommentDto commentDto,Long taskid) throws Exception;
    List<CommentDto> getTaskComments(Long taskid) throws Exception;
}
