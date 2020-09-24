package com.aaryan.Instagram.Clone.Services;

import com.aaryan.Instagram.Clone.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountSettingService {

    private final AuthService authService;
    private final UserRepository userRepository;


}
