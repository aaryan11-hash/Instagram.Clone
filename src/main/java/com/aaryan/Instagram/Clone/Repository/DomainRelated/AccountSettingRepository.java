package com.aaryan.Instagram.Clone.Repository.DomainRelated;

import com.aaryan.Instagram.Clone.Domain.RealTime.AccountSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountSettingRepository extends JpaRepository<AccountSettings,Long> {


}
