package com.aaryan.Instagram.Clone.Model;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginRequest {

    private String username;
    private String password;
}
