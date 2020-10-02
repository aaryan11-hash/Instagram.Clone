package com.aaryan.Instagram.Clone.Repository.DomainRelated;

import com.aaryan.Instagram.Clone.Domain.RealTime.Post;
import com.aaryan.Instagram.Clone.Domain.RealTime.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> getAllByProcessingIsFalse();

    List<Post> getPostByTaggedUserNotificationIsFalseAndProcessingIsTrue();

    Post getPostByUserAndImgUrl(User user,String imgUrl);
}
