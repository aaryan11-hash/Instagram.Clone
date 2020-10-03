package com.aaryan.Instagram.Clone.Services;

import com.aaryan.Instagram.Clone.Mapper.UserAccMapper;
import com.aaryan.Instagram.Clone.Model.UserAccountResponseDto;
import com.aaryan.Instagram.Clone.Repository.DomainRelated.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountSettingService {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final UserAccMapper userAccMapper;


    @Transactional
    public UserAccountResponseDto getUserinfo(Long userId) {
        return userAccMapper.convertToUserAccountDto(userRepository.getOne(userId));
    }



}
