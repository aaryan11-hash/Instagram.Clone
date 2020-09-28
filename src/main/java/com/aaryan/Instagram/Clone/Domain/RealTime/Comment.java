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
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String commentDate;

    private String comment;

    private Long commentMadeByUserId;

    @ManyToOne
    Post post;

    @OneToMany
    private List<CommentLike> commentLikes;
}
