package com.aaryan.Instagram.Clone.Domain.RealTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accId;

    private String email;

    private String username;

    private String password;

    private String privateORPublic;

    private Boolean commercial;

    private Boolean notifications;

    @OneToOne(mappedBy = "accountSettings",fetch = FetchType.LAZY)
    User user;
}
