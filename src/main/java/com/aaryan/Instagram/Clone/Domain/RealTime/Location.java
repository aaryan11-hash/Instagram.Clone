package com.aaryan.Instagram.Clone.Domain.RealTime;



import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Data
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;

    @Column(nullable = true)
    private String country;
    @Column(nullable = true)
    private String state;
    @Column(nullable = true)
    private String city;

}
