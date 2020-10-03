package com.aaryan.Instagram.Clone.Mapper;

import com.aaryan.Instagram.Clone.Domain.RealTime.User;
import com.aaryan.Instagram.Clone.Model.AccountDetailDto;
import com.aaryan.Instagram.Clone.Model.AccountSettingsDto;
import com.aaryan.Instagram.Clone.Model.UserAccountResponseDto;
import com.aaryan.Instagram.Clone.Model.UserDto;
import lombok.Builder;
import lombok.Data;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
@DecoratedWith(value = UserAccountMapper.class)
public interface UserAccMapper {


    UserAccountResponseDto convertToUserAccountDto(User user);

    User connvertToUserDomainObject(AccountDetailDto accountDetailDto);

}
