package com.myblog.myblog11.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="posts")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Post {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    private String content;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL) // Establishes the one-to-many relationship with comments
    private List<Comment> comments; // Represents the list of comments associated with the post

}
