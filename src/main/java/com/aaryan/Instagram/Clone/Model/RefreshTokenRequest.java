package com.aaryan.Instagram.Clone.Model;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RefreshTokenRequest {
    @NotBlank
    private String refreshToken;
    private String username;
}
