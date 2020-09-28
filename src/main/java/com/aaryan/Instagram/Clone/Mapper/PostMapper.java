package com.aaryan.Instagram.Clone.Mapper;

import com.aaryan.Instagram.Clone.Domain.RealTime.Hashtag;
import com.aaryan.Instagram.Clone.Domain.RealTime.Likes;
import com.aaryan.Instagram.Clone.Domain.RealTime.Post;
import com.aaryan.Instagram.Clone.Domain.RealTime.UserTag;
import com.aaryan.Instagram.Clone.Model.PostResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "likes",expression = "java(mapPosts(post.likeslist))")
    @Mapping(target = "userstaggedUsername",expression = "java(taggedUser(post.usersTagged))")
    @Mapping(target="hashTags",expression = "java(hashtags(post.hashtags))")
    PostResponseDto PostToPostDto(Post post);

    default Integer mapPosts(List<Likes> numberOfPosts){return numberOfPosts.size();}

    default List<String> hashtags(List<Hashtag> hashtagList){
        List<String> hashtag = new ArrayList<>() ;
         hashtagList.stream()
                .forEach(r->hashtag.add(r.getHashtag()));

         return hashtag;
    }

    default List<String> taggedUser(List<UserTag> userTagList){
        List<String> taggedPeople = new ArrayList<>();

        userTagList.stream().forEach(tag->taggedPeople.add(tag.getUserWhoWasTagged()));
        return taggedPeople;
    }
}
