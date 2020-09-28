package com.aaryan.Instagram.Clone.Domain.RealTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false)
    private String postDate;

    @Column(nullable = false)
    private String imgUrl;

    @Column(nullable = false)
    private String caption;

    private Boolean processing;

    @ManyToOne(fetch = FetchType.EAGER)
    User user;

    @OneToMany(fetch = FetchType.LAZY)
    public List<Likes> likeslist;

    @OneToMany(fetch = FetchType.LAZY)
    public List<Comment> comments;

    @OneToOne(fetch = FetchType.EAGER)
    public Location location;

    @OneToMany
    public List<UserTag> usersTagged;

    @ManyToMany
    public List<Hashtag> hashtags;


}
