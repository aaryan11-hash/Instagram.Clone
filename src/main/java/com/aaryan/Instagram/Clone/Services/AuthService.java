package com.aaryan.Instagram.Clone.Services;

import com.aaryan.Instagram.Clone.Domain.User;
import com.aaryan.Instagram.Clone.Domain.VerificationToken;
import com.aaryan.Instagram.Clone.Model.SignUpDto;
import com.aaryan.Instagram.Clone.Repository.UserRepository;
import com.aaryan.Instagram.Clone.Repository.VerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MailingService mailingService;
    private final AuthenticationManager authenticationManager;
    private final VerificationRepository verificationRepository;


    public void signUp(SignUpDto signUpDto) {
        User user = User.builder()
                .email(signUpDto.getEmail())
                .username(signUpDto.getUsername())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .joinedDate(Instant.now())
                .setEnabled(false)
                .build();

        userRepository.save(user);

        String token = generateVerificationToken(user);

        mailingService.sendAuthEmail(user.getEmail());
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationRepository.save(verificationToken);
        return token;
    }


    public void verifyAccount(String token) {


    }
}
