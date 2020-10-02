package com.aaryan.Instagram.Clone.Mapper;

import com.aaryan.Instagram.Clone.Domain.RealTime.User;
import com.aaryan.Instagram.Clone.Model.AccountSettingsDto;
import com.aaryan.Instagram.Clone.Model.UserAccountResponseDto;
import com.aaryan.Instagram.Clone.Model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class UserAccountMapper implements UserAccMapper {

    private UserAccMapper userAccMapper;

    @Autowired
    public void setUserAccMapper(UserAccMapper userAccMapper) {
        this.userAccMapper = userAccMapper;
    }

    @Override
    public UserAccountResponseDto convertToUserAccountDto(User user) {
        UserDto userDto =UserDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .middleName(user.getMiddleName())
                .profilePictureUrl(user.getProfilePictureUrl())
                .userBirthday(user.getUserBirthday())
                .userId(user.getUserId())
                .build();

        AccountSettingsDto accountSettingsDto = AccountSettingsDto.builder()
                .accId(user.getAccountSettings().getAccId())
                .email(user.getAccountSettings().getEmail())
                .username(user.getAccountSettings().getUsername())
                .notifications(user.getAccountSettings().getNotifications())
                .commercial(user.getAccountSettings().getCommercial())
                .privateORPublic(user.getAccountSettings().getPrivateORPublic())
                .build();

        return UserAccountResponseDto.builder()
                .accountSettingsDto(accountSettingsDto)
                .userDto(userDto)
                .build();
    }
}
