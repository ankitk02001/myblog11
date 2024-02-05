package com.myblog.myblog11.service.impl;

import com.myblog.myblog11.entity.Post;
import com.myblog.myblog11.exception.ResourceNotFoundException;
import com.myblog.myblog11.payload.PostDto;
import com.myblog.myblog11.repository.PostRepository;
import com.myblog.myblog11.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private ModelMapper modelMapper;
    private PostRepository postRepository;
//for constructor injection no need to use @AutoWired annotation
    public PostServiceImpl(ModelMapper modelMapper, PostRepository postRepository) {
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
//        Post post=new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        Post savedPost = postRepository.save(post);
        PostDto dto = mapToDto(savedPost);
        //getting the data which got saved in DB
//        PostDto dto=new PostDto();
//        dto.setTitle(savedPost.getTitle());
//        dto.setDescription(savedPost.getDescription());
//        dto.setContent(savedPost.getContent());
        return dto;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Post not found with id: "+id)
        );
        //setting the data in dto class object
        PostDto dto=new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getContent());
        return dto;

    }

    @Override
    public List<PostDto> getAllposts(int pageNo, int pageSize, String sortBy, String sortDir) {
      Sort sort=(sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> pagePost = postRepository.findAll(pageable);
        List<Post> posts = pagePost.getContent();
        List<PostDto> dtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        return dtos;
    }


    //for converting Entity to Dto
    PostDto mapToDto(Post post){
        //by using model mapper in one line from  map to entity class
        PostDto dto = modelMapper.map(post, PostDto.class);
//        PostDto dto=new PostDto();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());
        return dto;
    }

    //for converting Dto to Entity
    Post mapToEntity(PostDto postDto){
        Post post = modelMapper.map(postDto, Post.class);
//        Post post=new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }
}
