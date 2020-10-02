package com.aaryan.Instagram.Clone.Controllers;

import com.aaryan.Instagram.Clone.Domain.RealTime.Post;
import com.aaryan.Instagram.Clone.Model.PostRequestDto;
import com.aaryan.Instagram.Clone.Model.PostResponseDto;
import com.aaryan.Instagram.Clone.Services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @PostMapping("/newUserPost")
    public ResponseEntity<String> saveNewUserPost(@RequestBody PostRequestDto postRequestDto){
        postService.saveNewUserPost(postRequestDto);
        return ResponseEntity.ok().body("Post saved successfully");
    }


    @GetMapping("/getUserPosts")
    public ResponseEntity<List<PostResponseDto>> fetchUserPosts(){
       return new ResponseEntity(postService.getAllCurrentUserPosts(), HttpStatus.OK);
    }

    @GetMapping("/SpecificUser/{userId}/Posts")
    public ResponseEntity<List<PostResponseDto>> fetchSpecificUserPosts(@PathVariable Long userId){
        return new ResponseEntity<>(postService.getASpecificUserPosts(userId),HttpStatus.OK);
    }

    //todo to add a REST endpoint that will fetch posts for the IG search option based on user preference
    @GetMapping("/SpecificUser/{userId}/SearchBtn")
    public void fetchUserPreferencePosts(@PathVariable Long userId){

    }

    @GetMapping("/notification/PostLink/{postId}")
    public ResponseEntity<Post> sendDataforNotificationLinkToPost(@PathVariable("postId") Long postId){

        return new ResponseEntity(postService.getSpecificPostFromSpecificUser(postId),HttpStatus.FOUND);
    }





}
