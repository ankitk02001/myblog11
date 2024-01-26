package com.myblog.myblog11.controller;


import com.myblog.myblog11.payload.CommentDto;
import com.myblog.myblog11.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/comments")
public class CommentController {
    private CommentService commentService;
//contructor injection
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //http://localhost:8080/api/comments?postId=1
    @PostMapping
    public ResponseEntity<CommentDto> createComment(
            @RequestBody CommentDto commentDto,
            @RequestBody Long postId
                ){
        CommentDto dto = commentService.createComment(commentDto, postId);
          return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }
}
