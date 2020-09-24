package com.aaryan.Instagram.Clone.Repository;

import com.aaryan.Instagram.Clone.Domain.Post;
import com.aaryan.Instagram.Clone.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.List;
import java.util.Optional;

public interface UserRepository  extends JpaRepository<User,Long> {

    Optional<User> findByAccountSettings_Username(String username);

}
