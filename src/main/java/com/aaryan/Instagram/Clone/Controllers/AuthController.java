package com.aaryan.Instagram.Clone.Controllers;

import com.aaryan.Instagram.Clone.Model.SignUpDto;
import com.aaryan.Instagram.Clone.Services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity signUp(@RequestBody SignUpDto signUpDto) {

        authService.signUp(signUpDto);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token){

        authService.verifyAccount(token);
        return new ResponseEntity<>("user activated", HttpStatus.OK);
    }


}
