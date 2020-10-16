package com.aaryan.Instagram.Clone.Model;


import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
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
