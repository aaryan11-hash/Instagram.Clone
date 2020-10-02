package com.aaryan.Instagram.Clone.Repository.DomainRelated;

import com.aaryan.Instagram.Clone.Domain.RealTime.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationRepository extends JpaRepository<VerificationToken,Long> {

Optional<VerificationToken> findByToken(String token);
}
