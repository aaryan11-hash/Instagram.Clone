package com.aaryan.Instagram.Clone.Domain;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(columnDefinition = "varchar(60)",updatable = true)
    private String email;

    @Column(columnDefinition = "varchar(20)",updatable = true)
    private String username;

    @Column(columnDefinition = "varchar(100)",updatable = true)
    private String password;

    @Column(columnDefinition = "varchar(100)",updatable = true)
    private String bio;

    private Instant joinedDate;

    private Boolean setEnabled;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Post> userPosts;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Comments> userComments;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Follows> followers;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Follows> following;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Likes> likes;

}
