package com.aaryan.Instagram.Clone.Repository;

import com.aaryan.Instagram.Clone.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository  extends JpaRepository<User,Long> {

    User findByUsername(String username);

}
