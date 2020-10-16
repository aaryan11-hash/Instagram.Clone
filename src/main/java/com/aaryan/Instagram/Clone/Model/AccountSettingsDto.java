package com.aaryan.Instagram.Clone.Model;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Data
@Getter
@Setter
public class AccountSettingsDto {

    private Long accId;

    private String email;

    private String username;

    private String privateORPublic;

    private Boolean commercial;

    private Boolean notifications;
}
