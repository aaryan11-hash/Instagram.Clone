package com.aaryan.Instagram.Clone.Controllers;

import com.aaryan.Instagram.Clone.Domain.Post;
import com.aaryan.Instagram.Clone.Model.PostDto;
import com.aaryan.Instagram.Clone.Services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/getUserPosts")
    public ResponseEntity<List<PostDto>> getUserPosts(){
       return new ResponseEntity(postService.getAllUserPosts(), HttpStatus.OK);
    }


}
