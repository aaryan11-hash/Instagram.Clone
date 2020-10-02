package com.aaryan.Instagram.Clone.Model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserAccountResponseDto {

    private UserDto userDto;

    private AccountSettingsDto accountSettingsDto;

}
