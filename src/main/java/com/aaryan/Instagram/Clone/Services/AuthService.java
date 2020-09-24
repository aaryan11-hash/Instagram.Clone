package com.aaryan.Instagram.Clone.Services;

import com.aaryan.Instagram.Clone.Domain.AccountSettings;
import com.aaryan.Instagram.Clone.Domain.User;
import com.aaryan.Instagram.Clone.Domain.VerificationToken;
import com.aaryan.Instagram.Clone.Model.AuthenticationResponse;
import com.aaryan.Instagram.Clone.Model.LoginRequest;
import com.aaryan.Instagram.Clone.Model.RefreshTokenRequest;
import com.aaryan.Instagram.Clone.Model.SignUpDto;
import com.aaryan.Instagram.Clone.Repository.UserRepository;
import com.aaryan.Instagram.Clone.Repository.VerificationRepository;
import com.aaryan.Instagram.Clone.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MailingService mailingService;
    private final AuthenticationManager authenticationManager;
    private final VerificationRepository verificationRepository;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;


    public void signUp(SignUpDto signUpDto) {

        AccountSettings accountSettings = AccountSettings
                .builder()
                .username(signUpDto.getUsername())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .email(signUpDto.getEmail())
                .notifications(false)
                .build();

        User user = User
                .builder()
                .userBirthday(signUpDto.getBirthdate())
                .firstName(signUpDto.getFirstName())
                .lastName(signUpDto.getLastName())
                .accountSettings(accountSettings)
                .middleName(signUpDto.getMiddleName())
                .enabled(false)
                .build();

        userRepository.save(user);

        String token = generateVerificationToken(user);

        mailingService.sendAuthEmail(user.getAccountSettings().getEmail());
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
        Optional<VerificationToken> verificationToken = verificationRepository.findByToken(token);
        fetchUserAndEnable(verificationToken.orElseThrow(()->new RuntimeException("error fetching and authenticating user!!")));

    }

    @Transactional
    public void fetchUserAndEnable(VerificationToken v) {
        String username =v.getUser().getAccountSettings().getUsername();
        User user =null;

        user = userRepository.findByAccountSettings_Username(username).orElseThrow(()->new RuntimeException("User not found with name - " + username));


        user.setEnabled(true);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByAccountSettings_Username(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .build();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}
