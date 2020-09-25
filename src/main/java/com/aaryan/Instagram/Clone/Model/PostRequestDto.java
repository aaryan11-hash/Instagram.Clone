package com.aaryan.Instagram.Clone.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PostRequestDto {

    private Long userId;
    private String imgurl;
    private String caption;
    private String Country;
    private String state;
    private String city;

    private List<String> taggedUser;

    private List<String> hashtags;
}
