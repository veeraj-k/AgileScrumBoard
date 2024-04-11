package com.veerajk.demo.services;

import com.veerajk.demo.dtos.CommentDto;

public interface CommentService {
    CommentDto addComment(CommentDto commentDto,Long taskid) throws Exception;
}
