package com.aaryan.Instagram.Clone.Services;

import com.aaryan.Instagram.Clone.Domain.Hashtag;
import com.aaryan.Instagram.Clone.Domain.Location;
import com.aaryan.Instagram.Clone.Domain.Post;
import com.aaryan.Instagram.Clone.Domain.User;
import com.aaryan.Instagram.Clone.Mapper.PostMapper;
import com.aaryan.Instagram.Clone.Model.PostRequestDto;
import com.aaryan.Instagram.Clone.Model.PostResponseDto;
import com.aaryan.Instagram.Clone.Repository.HashTagRepository;
import com.aaryan.Instagram.Clone.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostMapper postMapper;
    private final AuthService authService;
    private final HashTagRepository hashTagRepository;

    @Transactional
    public List<PostResponseDto> getAllCurrentUserPosts(){

    return userRepository.getOne(authService.getCurrentUser().getUserId()).getPosts()
    .stream()
            .map(postMapper::PostToPostDto).collect(toList());
    }

    @Transactional
    public List<PostResponseDto> getASpecificUserPosts(Long userId){
        return userRepository.getOne(userId).getPosts()
                .stream()
                .map(postMapper::PostToPostDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Async
    public void saveNewUserPost(PostRequestDto postRequestDto) {

        User user = userRepository.getOne(postRequestDto.getUserId());
        Post post = Post.builder()
                .postDate(Instant.now().toString())
                .caption(postRequestDto.getCaption())
                .imgUrl(postRequestDto.getImgurl())
                .location(Location.builder()
                        .city(postRequestDto.getCity())
                        .state(postRequestDto.getState())
                        .country(postRequestDto.getCity())
                        .build())
                .hashtags(processHashTag(postRequestDto.getHashtags())

        //todo incomplete


    }

    public List<Hashtag> processHashTag(List<String> hastags){


        //todo incomplete
        return null;
    }

}
