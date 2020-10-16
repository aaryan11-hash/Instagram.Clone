package com.aaryan.Instagram.Clone.Controllers;

import com.aaryan.Instagram.Clone.Mapper.UserAccMapper;
import com.aaryan.Instagram.Clone.Mapper.UserAccountMapper;
import com.aaryan.Instagram.Clone.Model.AccountDetailDto;
import com.aaryan.Instagram.Clone.Model.UserAccountResponseDto;
import com.aaryan.Instagram.Clone.Repository.DomainRelated.UserRepository;
import com.aaryan.Instagram.Clone.Services.AccountSettingService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account/setting")
public class UserAccSettingController {

       private AccountSettingService accountSettingService;

        @GetMapping("/profile/getUserInfo/{userId}")
        public ResponseEntity<UserAccountResponseDto> getUserinfo(@PathVariable Long userId){

            return new ResponseEntity(accountSettingService.getUserinfo(userId), HttpStatus.FOUND);
        }

        @PostMapping("/profile/UpdateUserInfo/Misseleneous")
        public ResponseEntity<String> updateUserProfileInfo(@RequestBody AccountDetailDto accountDetailDto){

            return new ResponseEntity<>(accountSettingService.changeUpdateDetail(accountDetailDto),HttpStatus.OK);
        }

        @PostMapping("/profile/UpdateUserInfo/Important")
        public ResponseEntity<String> updateUserCrucialInfo(@RequestBody AccountDetailDto accountDetailDto){

            return new ResponseEntity<>(accountSettingService.changeUserCrucialDataforPassword(accountDetailDto),HttpStatus.OK);
        }

        @GetMapping("/{userId}/confirmAction/password")
        public ResponseEntity<String> confirmPasswordChangeAction(@PathVariable Long userId){

            return ResponseEntity.ok().body( accountSettingService.confirmPasswordChange(userId));
        }

        @GetMapping("/{userId}/confirmAction/email")
        public ResponseEntity<String> confirmEmailChangeAction(@PathVariable Long userId){

            return ResponseEntity.ok().body(accountSettingService.confirmEmailChange(userId));
        }

        @GetMapping("/password/{userId}/revertAction")
        public ResponseEntity<String> revertPasswordChange(@PathVariable Long userId){

            return ResponseEntity.ok().body(accountSettingService.revertNewPasswordChange(userId));
        }

        @GetMapping("/email/{userId}/revertAction")
        public ResponseEntity<String> revertEmailChange(@PathVariable Long userId){

            return ResponseEntity.ok().body(accountSettingService.revertNewEmailChange(userId));
        }

}
