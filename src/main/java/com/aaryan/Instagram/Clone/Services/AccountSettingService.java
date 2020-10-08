package com.aaryan.Instagram.Clone.Services;

import com.aaryan.Instagram.Clone.Domain.Confirmation.PasswordChange;
import com.aaryan.Instagram.Clone.Mapper.UserAccMapper;
import com.aaryan.Instagram.Clone.Model.AccountDetailDto;
import com.aaryan.Instagram.Clone.Model.UserAccountResponseDto;
import com.aaryan.Instagram.Clone.Repository.Confirmation.PasswordChangeRepository;
import com.aaryan.Instagram.Clone.Repository.DomainRelated.AccountSettingRepository;
import com.aaryan.Instagram.Clone.Repository.DomainRelated.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@val
public class AccountSettingService {

    private final AuthService authService;
    private final UserAccMapper userAccMapper;
    private final MailingService mailingService;
    private final UserRepository userRepository;
    private final PasswordChangeRepository passwordChangeRepository;
    private final AccountSettingRepository accountSettingRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserAccountResponseDto getUserinfo(Long userId) {
        return userAccMapper.convertToUserAccountDto(userRepository.getOne(userId));
    }


    @Transactional
    public String  changeUpdateDetail(AccountDetailDto accountDetailDto) {

        val user = userRepository.getOne(authService.getCurrentUser().getUserId());

        if(accountDetailDto.getNewFirstName()!=null)
            user.setFirstName(accountDetailDto.getNewFirstName());
        if(accountDetailDto.getNewMiddleName()!=null)
            user.setMiddleName(accountDetailDto.getNewMiddleName());
        if(accountDetailDto.getNewLastName()!=null)
            user.setLastName(accountDetailDto.getNewLastName());
        if(accountDetailDto.getNewBirthDate()!=null)
            user.setUserBirthday(accountDetailDto.getNewBirthDate());
        if(accountDetailDto.getNewUsername()!=null)
            user.getAccountSettings().setUsername(accountDetailDto.getNewUsername());
        if(accountDetailDto.getCommercial()!=null)
            user.getAccountSettings().setCommercial(accountDetailDto.getCommercial());
        if(accountDetailDto.getNewPrivateORPublic()!=null)
            user.getAccountSettings().setCommercial(accountDetailDto.getCommercial());
        if(accountDetailDto.getNotifications()!=null)
            user.getAccountSettings().setNotifications(accountDetailDto.getNotifications());

        userRepository.save(user);

        return "Done";
    }

    @Transactional
    public String changeUserCrucialDataforPassword(AccountDetailDto accountDetailDto) {

        val currentUser = userRepository.getOne(authService.getCurrentUser().getUserId());
        var oldPassword = currentUser.getAccountSettings().getPassword();
        var newPassword = accountDetailDto.getNewPassword();

        val passwordChange  = PasswordChange.builder()
                .password(accountDetailDto.getNewPassword())
                .username(currentUser.getAccountSettings().getUsername())
                .passwordChangeInvokedAt(Instant.now());



        //todo further thinking has to be put in to this function to tackle the issue of security and data roll back scenarios;

            mailingService.sendPasswordChangeNotification(currentUser,oldPassword,newPassword);

        return "temp new password saved successfully";
    }

    @Transactional
    public String confirmPasswordChange(String username) {

        val confirmed = passwordChangeRepository.getByUsername(username);
        val user = userRepository.getOne(authService.getCurrentUser().getUserId());
        val accountSettings = user.getAccountSettings();

        accountSettings.setPassword(passwordEncoder.encode(confirmed.getPassword()));
        accountSettingRepository.save(accountSettings);
        user.setAccountSettings(accountSettings);
        userRepository.save(user);


        return "Password Changed";
    }
}
