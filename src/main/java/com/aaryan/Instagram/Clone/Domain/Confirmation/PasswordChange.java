package com.aaryan.Instagram.Clone.Domain.Confirmation;

import lombok.*;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;

@Entity
@Data
@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class PasswordChange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passId;

    private String password;
    private Long userId;

    private Instant passwordChangeInvokedAt;
}
