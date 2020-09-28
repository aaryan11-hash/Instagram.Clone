package com.aaryan.Instagram.Clone.Services;

import com.aaryan.Instagram.Clone.Domain.Optimize.HashtagOptimize;
import com.aaryan.Instagram.Clone.Domain.Optimize.UnprocessedPosts;
import com.aaryan.Instagram.Clone.Domain.Optimize.UserTagOptimize;
import com.aaryan.Instagram.Clone.Domain.RealTime.Hashtag;
import com.aaryan.Instagram.Clone.Domain.RealTime.Location;
import com.aaryan.Instagram.Clone.Domain.RealTime.Post;
import com.aaryan.Instagram.Clone.Domain.RealTime.User;
import com.aaryan.Instagram.Clone.Mapper.PostMapper;
import com.aaryan.Instagram.Clone.Model.PostRequestDto;
import com.aaryan.Instagram.Clone.Model.PostResponseDto;
import com.aaryan.Instagram.Clone.Repository.DomainRelated.PostRepository;
import com.aaryan.Instagram.Clone.Repository.HashTagRepository;
import com.aaryan.Instagram.Clone.Repository.OptimizationRelated.HashTagRepositoryOP;
import com.aaryan.Instagram.Clone.Repository.OptimizationRelated.UnprocPostRepository;
import com.aaryan.Instagram.Clone.Repository.OptimizationRelated.UserTagRepositoryOP;
import com.aaryan.Instagram.Clone.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
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
    private final PostRepository postRepository;
    private final HashTagRepositoryOP hashTagRepositoryOP;
    private final UserTagRepositoryOP userTagRepositoryOP;
    private final UnprocPostRepository unprocPostRepository;

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
                .processing(false)
                .build();


        postRepository.save(post);

        UnprocessedPosts unprocessedPosts = UnprocessedPosts.builder()
                .hashtags(postRequestDto.getHashtags().stream()
                            .map(r->hashTagRepositoryOP.save(HashtagOptimize.builder().hashtag(r).build()))
                        .collect(toList()))
                .usersTagged(postRequestDto.getTaggedUser().stream()
                            .map(u->userTagRepositoryOP.save(UserTagOptimize.builder().userWhoWasTagged(u).build()))
                .collect(toList()))
                .time(Instant.now().toEpochMilli())
                .build();

        unprocPostRepository.save(unprocessedPosts);

    }

    @Scheduled(fixedDelay = 100000)
    public void processNewlySavedPosts(){
        List<Post> postList = postRepository.getAllByProcessingIsFalse();
        List<UnprocessedPosts> unprocessedPostsList = unprocPostRepository.getAllByTimeOrderByTimeDesc();

        //todo this function will do the finishing job of linking the hashtags,users tags in a post and the post itself...
    }

}
