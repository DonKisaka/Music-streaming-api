package com.example.musicstreaming.Dto;

public record AuthenticationResponse(
        String token,
        String email,
        String username
) {}
