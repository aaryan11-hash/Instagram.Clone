package com.aaryan.Instagram.Clone.Services;

import com.aaryan.Instagram.Clone.Domain.Optimize.HashtagOptimize;
import com.aaryan.Instagram.Clone.Domain.Optimize.UserTagOptimize;
import com.aaryan.Instagram.Clone.Domain.RealTime.*;
import com.aaryan.Instagram.Clone.Mapper.PostMapper;
import com.aaryan.Instagram.Clone.Model.PostRequestDto;
import com.aaryan.Instagram.Clone.Model.PostResponseDto;
import com.aaryan.Instagram.Clone.Repository.DomainRelated.PostRepository;
import com.aaryan.Instagram.Clone.Repository.DomainRelated.UserTagRepository;
import com.aaryan.Instagram.Clone.Repository.DomainRelated.HashTagRepository;
import com.aaryan.Instagram.Clone.Repository.OptimizationRelated.HashTagRepositoryOP;
import com.aaryan.Instagram.Clone.Repository.OptimizationRelated.UnprocPostRepository;
import com.aaryan.Instagram.Clone.Repository.OptimizationRelated.UserTagRepositoryOP;
import com.aaryan.Instagram.Clone.Repository.DomainRelated.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@val
public class PostService {

    private final MailingService mailingService;
    private final PostMapper postMapper;

    private final UserRepository userRepository;
    private final AuthService authService;
    private final HashTagRepository hashTagRepository;
    private final PostRepository postRepository;
    private final HashTagRepositoryOP hashTagRepositoryOP;
    private final UserTagRepositoryOP userTagRepositoryOP;
    private final UnprocPostRepository unprocPostRepository;
    private final UserTagRepository userTagRepository;

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
    public Post getSpecificPostFromSpecificUser(Long postId){
        return postRepository.getOne(postId);
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
                .taggedUserNotification(false)
                .user(user)
                .usersTagged( postRequestDto.getTaggedUser()
                        .stream()
                        .map(r->{UserTag userTag =UserTag.builder().userWhoWasTagged(user.getAccountSettings().getUsername()).build();
                            userTagRepository.save(userTag); return userTag;})
                        .collect(toList()))

                .build();




        postRepository.save(post);
        Post post1 = postRepository.getPostByUserAndImgUrl(user,post.getImgUrl());


        postRequestDto.getHashtags().stream()
                            .map(r-> hashTagRepositoryOP.save(HashtagOptimize.builder().hashtag(r).postId(post1.getPostId()).build()));
        postRequestDto.getTaggedUser().stream()
                            .map(u-> userTagRepositoryOP.save(UserTagOptimize.builder().userWhoWasTagged(u).build()));




        //unprocPostRepository.save(unprocessedPosts);

    }

    @Scheduled(fixedDelay = 100000)
    @Transactional
    @Async
    public void processNewlySavedPosts(){

        val newHashTags = new ArrayList<Hashtag>();

        val hashtagList = hashTagRepository.findAll();
        val hashtagOptimizeList = hashTagRepositoryOP.findAll();




        Collections.sort(hashtagList, new Comparator<Hashtag>() {
            @Override
            public int compare(Hashtag o1, Hashtag o2) {
                return o1.getHashtag().compareToIgnoreCase(o2.getHashtag());
            }
        });

        Collections.sort(hashtagOptimizeList, new Comparator<HashtagOptimize>() {
            @Override
            public int compare(HashtagOptimize o1, HashtagOptimize o2) {
                return o1.getHashtag().compareToIgnoreCase(o2.getHashtag());
            }
        });

         hashtagOptimizeList.stream()
                .forEach(hashtagOptimize -> {
                    int flag = 0;
                    for(Hashtag hashtag:hashtagList){
                        if(hashtag.getHashtag().contentEquals(hashtagOptimize.getHashtag()))
                            flag = 1;
                    }
                    if(flag==0) {
                        Post post = postRepository.getOne(hashtagOptimize.getPostId());
                        post.getHashtags();
                        Hashtag newHashTag = Hashtag.builder().hashtag(hashtagOptimize.getHashtag()).build();
                        newHashTag.getPosts().add(post);
                        hashTagRepository.save(newHashTag);
                        post.getHashtags().add(newHashTag);
                        postRepository.save(post);
                        post.setProcessing(true);
                    }
                    else if(flag==1){
                        Hashtag preexistingHashTag = hashTagRepository.getHashtagByHashtag(hashtagOptimize.getHashtag());
                        Post post = postRepository.getOne(hashtagOptimize.getPostId());
                        preexistingHashTag.getPosts().add(post);
                        hashTagRepository.save(preexistingHashTag);
                        post.getHashtags().add(preexistingHashTag);
                        post.setProcessing(true);
                        postRepository.save(post);

                    }

                });


            processUserNotification();





        //todo this function will do the finishing job of linking the hashtags,users tags in a post and the post itself...
    }

    private void processUserNotification() {
        val posts = postRepository.getPostByTaggedUserNotificationIsFalseAndProcessingIsTrue();

        posts.stream()
                .forEach(post -> {post.getUsersTagged().stream().forEach(r->{mailingService.sendNotificationToTaggedUser(r,post.getUser(),post.getPostId());});});

    }

}
