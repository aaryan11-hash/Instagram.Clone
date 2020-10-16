package com.aaryan.Instagram.Clone.Model;


import com.aaryan.Instagram.Clone.Domain.RealTime.Location;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
@Getter
@Setter
public class PostResponseDto {

    private Long postId;

    private String imgUrl;

    private String caption;

    private List<String> userstaggedUsername;

    public List<String> hashTags;

    public Location location;

    public Integer likes;
}
