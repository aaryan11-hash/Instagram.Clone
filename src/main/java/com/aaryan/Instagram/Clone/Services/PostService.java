package com.aaryan.Instagram.Clone.Services;

import com.aaryan.Instagram.Clone.Domain.Post;
import com.aaryan.Instagram.Clone.Mapper.PostMapper;
import com.aaryan.Instagram.Clone.Model.PostDto;
import com.aaryan.Instagram.Clone.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Collection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostMapper postMapper;
    private final AuthService authService;

    @Transactional
    public List<PostDto> getAllCurrentUserPosts(){

    return userRepository.getOne(authService.getCurrentUser().getUserId()).getPosts()
    .stream()
            .map(postMapper::PostToPostDto).collect(toList());
    }

    @Transactional
    public List<PostDto> getASpecificUserPosts(Long userId){
        return userRepository.getOne(userId).getPosts()
                .stream()
                .map(postMapper::PostToPostDto)
                .collect(Collectors.toList());
    }


}
