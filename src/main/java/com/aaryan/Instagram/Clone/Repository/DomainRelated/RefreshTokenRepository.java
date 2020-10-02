package com.aaryan.Instagram.Clone.Repository.DomainRelated;


import com.aaryan.Instagram.Clone.Domain.RealTime.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    Optional<RefreshToken> findByToken(String token);
    void deleteByToken(String token);

}
