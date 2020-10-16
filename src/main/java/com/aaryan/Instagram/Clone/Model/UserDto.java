package com.aaryan.Instagram.Clone.Model;

import com.aaryan.Instagram.Clone.Domain.RealTime.AccountSettings;
import com.aaryan.Instagram.Clone.Domain.RealTime.Post;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Builder
@Data
@Getter
@Setter
public class UserDto {

    private Long userId;

    private String userBirthday;

    private String profilePictureUrl;

    private String firstName;
    private String middleName;
    private String lastName;






}
