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

        @GetMapping("/{username}/confirmAction")
        public ResponseEntity<String> confirmPasswordChangeAction(@PathVariable String userId){


            return ResponseEntity.ok().body( accountSettingService.confirmPasswordChange(userId));
        }

}
