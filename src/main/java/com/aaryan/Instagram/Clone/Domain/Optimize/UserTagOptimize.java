package com.aaryan.Instagram.Clone.Domain.Optimize;

import com.aaryan.Instagram.Clone.Domain.RealTime.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserTagOptimize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userTagId;

    private String userWhoWasTagged;


}
