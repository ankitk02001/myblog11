package com.myblog.myblog11.controller;


import com.myblog.myblog11.payload.PostDto;
import com.myblog.myblog11.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;
    //constructor injection
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')")//by using this only admin can access this but not USER
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        PostDto dto = postService.createPost(postDto);
        return new  ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    //getting the post based on id
    //http://localhost:8080/api/posts/particular?id=1
    @GetMapping("/particular")
    public ResponseEntity<PostDto> getPostById(@RequestParam long id){
        PostDto dto = postService.getPostById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //http://localhost:8080/api/posts?pageNo=0&pageSize=3&sortBy=title&sortDir=asce
    @GetMapping
    public List<PostDto> getAllposts(
            @RequestParam(name="pageNo", required = false, defaultValue = "0")int pageNo,
            @RequestParam(name="pageSize", required = false, defaultValue = "3")int pageSize,
            @RequestParam(name="sortBy", required = false, defaultValue = "id")String sortBy,
            @RequestParam(name="sortDir", required = false, defaultValue = "id")String sortDir
    ){
        List<PostDto> postDtos = postService.getAllposts(pageNo, pageSize,sortBy,sortDir);
        return postDtos;

    }


}
