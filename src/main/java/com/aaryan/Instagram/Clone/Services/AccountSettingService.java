package com.aaryan.Instagram.Clone.Services;

import com.aaryan.Instagram.Clone.Domain.Confirmation.EmailChange;
import com.aaryan.Instagram.Clone.Domain.Confirmation.PasswordChange;
import com.aaryan.Instagram.Clone.Domain.RealTime.AccountSettings;
import com.aaryan.Instagram.Clone.Domain.RealTime.User;
import com.aaryan.Instagram.Clone.Mapper.UserAccMapper;
import com.aaryan.Instagram.Clone.Model.AccountDetailDto;
import com.aaryan.Instagram.Clone.Model.UserAccountResponseDto;
import com.aaryan.Instagram.Clone.Repository.Confirmation.EmailChangeRepository;
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
    private final EmailChangeRepository emailChangeRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserAccountResponseDto getUserinfo(Long userId) {
        return userAccMapper.convertToUserAccountDto(userRepository.getOne(userId));
    }


    @Transactional
    public String  changeUpdateDetail(AccountDetailDto accountDetailDto) {

        User user = authService.getCurrentUser();

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
        val currentUser = (User)userRepository.getOne(authService.getCurrentUser().getUserId());

        if(accountDetailDto.getNewEmail().isEmpty()){

            var oldPassword = currentUser.getAccountSettings().getPassword();
            var newPassword = accountDetailDto.getNewPassword();

            val passwordChange  = (PasswordChange)PasswordChange.builder()
                    .password(accountDetailDto.getNewPassword())
                    .userId(currentUser.getUserId())
                    .passwordChangeInvokedAt(Instant.now()).build();

               passwordChangeRepository.save(passwordChange);

            //todo further thinking has to be put in to this function to tackle the issue of security and data roll back scenarios;

            mailingService.sendPasswordChangeNotification(currentUser,oldPassword,newPassword);

            return "Password Changed Object Saved";
        }

        else if(accountDetailDto.getNewPassword().isEmpty()){

            var oldEmail = currentUser.getAccountSettings().getEmail();
            var newEmail = accountDetailDto.getNewEmail();

            val emailChange = (EmailChange)EmailChange.builder()
                    .oldEmail(currentUser.getAccountSettings().getEmail())
                    .newEmail(accountDetailDto.getNewEmail())
                    .userId(currentUser.getUserId())
                    .build();

            emailChangeRepository.save(emailChange);

            mailingService.sendEmailChangedNotification(currentUser,oldEmail,newEmail);

            return "Email Change object Saved";
        }

            return "something went wrong";
    }



    @Transactional
    public String confirmPasswordChange(Long userId) {

        val confirmed = (PasswordChange)passwordChangeRepository.getByUserId(userId);
        val user = (User)authService.getCurrentUser();
        val accountSettings = (AccountSettings)user.getAccountSettings();

        accountSettings.setPassword(passwordEncoder.encode(confirmed.getPassword()));
        accountSettingRepository.save(accountSettings);
        user.setAccountSettings(accountSettings);
        userRepository.save(user);


        return "Password Changed";
    }

    @Transactional
    public String confirmEmailChange(Long userId){

        val confirmed = emailChangeRepository.getByUserId(userId);
        val user = authService.getCurrentUser();
        val accountSettings =(AccountSettings) user.getAccountSettings();

        accountSettings.setEmail(confirmed.getNewEmail());
        accountSettingRepository.save(accountSettings);
        user.setAccountSettings(accountSettings);
        userRepository.save(user);

        return "Email Changed";
    }

    public String revertNewPasswordChange(Long userId) {

        val revertPasswordChange = passwordChangeRepository.getByUserId(userId);
        passwordChangeRepository.delete(revertPasswordChange);

        return "New Password Entity Deleted";
    }

    public String revertNewEmailChange(Long userId) {

        val revertEmailChange = emailChangeRepository.getByUserId(userId);
        emailChangeRepository.delete(revertEmailChange);

        return "New Email Entity Deleted";
    }
}
