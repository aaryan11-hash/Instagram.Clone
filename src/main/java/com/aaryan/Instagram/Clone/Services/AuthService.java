package com.aaryan.Instagram.Clone.Services;

import com.aaryan.Instagram.Clone.Domain.User;
import com.aaryan.Instagram.Clone.Model.SignUpDto;
import com.aaryan.Instagram.Clone.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public void signUp(SignUpDto signUpDto) {
        User user = User.builder()
                .email(signUpDto.getEmail())
                .username(signUpDto.getUsername())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .build();

        userRepository.save(user);

    }
}
