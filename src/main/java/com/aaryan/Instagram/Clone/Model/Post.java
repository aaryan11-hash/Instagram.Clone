package com.aaryan.Instagram.Clone.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String caption;

    private String ImgUrl;

    private String postCreattionDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private User userWhoPosted;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Comments> comments;
}
