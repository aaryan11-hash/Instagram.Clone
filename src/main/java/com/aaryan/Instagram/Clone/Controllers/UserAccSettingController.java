package com.aaryan.Instagram.Clone.Controllers;

import com.aaryan.Instagram.Clone.Mapper.UserAccMapper;
import com.aaryan.Instagram.Clone.Mapper.UserAccountMapper;
import com.aaryan.Instagram.Clone.Model.UserAccountResponseDto;
import com.aaryan.Instagram.Clone.Repository.DomainRelated.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account/setting")
public class UserAccSettingController {

        private final UserRepository userRepository;
        private final UserAccMapper userAccMapper;

        @GetMapping("/profile/getUserInfo/{userId}")
        public ResponseEntity<UserAccountResponseDto> getUserinfo(@PathVariable Long userId){

            return new ResponseEntity(userAccMapper.convertToUserAccountDto(userRepository.getOne(userId)), HttpStatus.FOUND);
        }


}
