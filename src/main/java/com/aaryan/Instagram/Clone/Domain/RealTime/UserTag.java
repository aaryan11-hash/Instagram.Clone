package com.aaryan.Instagram.Clone.Domain.RealTime;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class UserTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userTagId;

    private String userWhoWasTagged;


}
