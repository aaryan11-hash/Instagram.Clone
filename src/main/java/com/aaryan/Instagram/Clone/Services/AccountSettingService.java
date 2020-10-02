package com.aaryan.Instagram.Clone.Services;

import com.aaryan.Instagram.Clone.Repository.DomainRelated.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountSettingService {

    private final AuthService authService;
    private final UserRepository userRepository;


}
