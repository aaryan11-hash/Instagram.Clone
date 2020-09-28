package com.aaryan.Instagram.Clone.Repository.OptimizationRelated;

import com.aaryan.Instagram.Clone.Domain.Optimize.UserTagOptimize;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTagRepositoryOP extends JpaRepository<UserTagOptimize,Long> {
}
