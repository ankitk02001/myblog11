package com.myblog.myblog11.service.impl;

import com.myblog.myblog11.entity.Comment;
import com.myblog.myblog11.entity.Post;
import com.myblog.myblog11.exception.ResourceNotFoundException;
import com.myblog.myblog11.payload.CommentDto;
import com.myblog.myblog11.repository.CommentRepository;
import com.myblog.myblog11.repository.PostRepository;
import com.myblog.myblog11.service.CommentService;

//@Service
public class CommentServiceImpl implements CommentService {

    private PostRepository postRepository;
    private CommentRepository commentRepository;
    //contructor based injection

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }
    @Override
    public CommentDto createComment(CommentDto commentDto, long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id:" + postId));
        Comment comment=new Comment();
        comment.setEmail(commentDto.getEmail());
        comment.setText(commentDto.getText());
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);

        CommentDto dto=new CommentDto();
        dto.setId(savedComment.getId());
        dto.setEmail(savedComment.getEmail());
        dto.setText(savedComment.getText());
        return dto;
    }
}
