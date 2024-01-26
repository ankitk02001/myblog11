package com.myblog.myblog11.service;

import com.myblog.myblog11.payload.CommentDto;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto,long postid);
    }

