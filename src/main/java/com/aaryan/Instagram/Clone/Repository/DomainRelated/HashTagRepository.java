package com.aaryan.Instagram.Clone.Repository.DomainRelated;

import com.aaryan.Instagram.Clone.Domain.RealTime.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashTagRepository extends JpaRepository<Hashtag,Long> {

    Hashtag getHashtagByHashtag(String hashtag);
}
