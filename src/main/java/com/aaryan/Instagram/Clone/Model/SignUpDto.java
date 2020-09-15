package com.aaryan.Instagram.Clone.Model;

import lombok.*;

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


}
