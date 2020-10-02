package com.aaryan.Instagram.Clone.Model;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AccountSettingsDto {

    private Long accId;

    private String email;

    private String username;

    private String privateORPublic;

    private Boolean commercial;

    private Boolean notifications;
}
