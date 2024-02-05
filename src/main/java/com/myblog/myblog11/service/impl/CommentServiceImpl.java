package com.myblog.myblog11.service.impl;

import com.myblog.myblog11.entity.Comment;
import com.myblog.myblog11.entity.Post;
import com.myblog.myblog11.exception.ResourceNotFoundException;
import com.myblog.myblog11.payload.CommentDto;
import com.myblog.myblog11.repository.CommentRepository;
import com.myblog.myblog11.repository.PostRepository;
import com.myblog.myblog11.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {


    public ModelMapper modelMapper;
    private PostRepository postRepository;
    private CommentRepository commentRepository;
    //contructor based injection


    public CommentServiceImpl(ModelMapper modelMapper, PostRepository postRepository, CommentRepository commentRepository) {
        this.modelMapper = modelMapper;
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

    @Override
    public void deleteComment(long id)  {
        commentRepository.deleteById(id);

    }

    @Override
    public CommentDto updateComment(long id, CommentDto commentDto, long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found for id :" + postId)
        );
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Comment not found for id :" + id)
        );

        Comment c = mapToEntity(commentDto);
        c.setId(comment.getId());
        c.setPost(post);
        Comment savedComment = commentRepository.save(c);

      return  mapToDto(savedComment);
    }
    CommentDto mapToDto(Comment comment){
        CommentDto dto = modelMapper.map(comment, CommentDto.class);
        return dto;
    }
    Comment mapToEntity(CommentDto commentDto){
        Comment comment = modelMapper.map(commentDto, Comment.class);
       return comment;
    }
}
