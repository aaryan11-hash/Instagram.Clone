package com.aaryan.Instagram.Clone.Domain.Optimize;

import com.aaryan.Instagram.Clone.Domain.RealTime.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class HashtagOptimize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hashtagId;

    private String hashtag;

    private Long postId;


}
