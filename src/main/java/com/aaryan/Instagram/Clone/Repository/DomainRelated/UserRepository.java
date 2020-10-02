package com.aaryan.Instagram.Clone.Repository.DomainRelated;

import com.aaryan.Instagram.Clone.Domain.RealTime.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User,Long> {

    Optional<User> findByAccountSettings_Username(String username);

    User getByAccountSettings_Username(String username);

}
