package com.myblog.myblog11.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "comments") // Specifies the name of the table in the database
@AllArgsConstructor // Lombok annotation to generate constructor with all arguments
@NoArgsConstructor // Lombok annotation to generate constructor with no arguments
@Data // Lombok annotation to generate getters, setters, toString, equals, and hashCode methods
public class Comment {
    @Id // Specifies the primary key of the entity
@GeneratedValue(strategy = GenerationType.IDENTITY) // Specifies the generation strategy for the primary key
    private long id;
    private String text;
    private String email;

    @ManyToOne(fetch = FetchType.LAZY) // Establishes the many-to-one relationship with posts
    @JoinColumn(name = "post_id") // Specifies the foreign key column in the comments table
    private Post post; // Represents the post associated with the comment
}