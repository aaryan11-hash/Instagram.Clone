package com.aaryan.Instagram.Clone.Repository.Confirmation;

import com.aaryan.Instagram.Clone.Domain.Confirmation.PasswordChange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordChangeRepository extends JpaRepository<PasswordChange,Long> {

    PasswordChange getByUserId(Long userId);
    PasswordChange getByUsername(String username);
}
