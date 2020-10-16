package com.aaryan.Instagram.Clone.Model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Data
@Getter
@Setter
public class UserAccountResponseDto {

    private UserDto userDto;

    private AccountSettingsDto accountSettingsDto;

}
