package com.aaryan.Instagram.Clone.Repository;

import com.aaryan.Instagram.Clone.Domain.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationRepository extends JpaRepository<VerificationToken,Long> {
}
