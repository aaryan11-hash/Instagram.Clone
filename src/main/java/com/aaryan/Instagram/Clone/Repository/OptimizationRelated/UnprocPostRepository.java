package com.aaryan.Instagram.Clone.Repository.OptimizationRelated;

import com.aaryan.Instagram.Clone.Domain.Optimize.UnprocessedPosts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnprocPostRepository extends JpaRepository<UnprocessedPosts,Long> {


}
