package com.aaryan.Instagram.Clone.Model;

import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Data
public class SignUpDto {

    private String username;
    private String password;
    private String email;

    private String firstName;
    private String middleName;
    private String lastName;
    private String birthdate;

}
