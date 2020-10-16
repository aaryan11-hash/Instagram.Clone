package com.aaryan.Instagram.Clone.Domain.RealTime;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private String userBirthday;

    private String profilePictureUrl;

    private String firstName;
    private String middleName;
    private String lastName;

    private Boolean enabled;

    @OneToMany(fetch = FetchType.LAZY)
    List<Post> posts;

    @OneToOne(fetch = FetchType.EAGER)
    AccountSettings accountSettings;

}
