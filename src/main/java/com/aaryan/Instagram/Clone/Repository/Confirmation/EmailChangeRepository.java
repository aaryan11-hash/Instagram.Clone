package com.aaryan.Instagram.Clone.Repository.Confirmation;

import com.aaryan.Instagram.Clone.Domain.Confirmation.EmailChange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailChangeRepository extends JpaRepository<EmailChange,Long> {

    EmailChange getByUserId(Long userID);
}
