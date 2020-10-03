package com.aaryan.Instagram.Clone.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountDetailDto {


    private String newUsername;
    private String newEmail;
    private String newPassword;

    private String newFirstName;
    private String newMiddleName;
    private String newLastName;

    private String newBirthDate;

    private String newProfilePictureUrl;

    private Boolean newPrivateORPublic;

    private Boolean commercial;

    private Boolean notifications;






}
