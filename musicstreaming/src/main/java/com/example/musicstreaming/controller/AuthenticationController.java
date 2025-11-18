package com.example.musicstreaming.controller;

import com.example.musicstreaming.Dto.AuthenticationResponse;
import com.example.musicstreaming.Dto.CreateUser;
import com.example.musicstreaming.Dto.LoginUser;
import com.example.musicstreaming.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signUp(
            @Valid @RequestBody CreateUser input
            ){
        AuthenticationResponse response = authenticationService.signUp(input);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody LoginUser input
    ){
        AuthenticationResponse response = authenticationService.authenticate(input);
        return ResponseEntity.ok(response);
    }

}
