package com.aaryan.Instagram.Clone.Repository.DomainRelated;

import com.aaryan.Instagram.Clone.Domain.RealTime.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTagRepository extends JpaRepository<UserTag,Long> {

}
