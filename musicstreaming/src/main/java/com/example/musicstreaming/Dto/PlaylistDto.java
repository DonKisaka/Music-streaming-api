package com.example.musicstreaming.Dto;

import java.time.LocalDateTime;
import java.util.List;

public record PlaylistDto(
        Long id,
        String name,
        String description,
        LocalDateTime createdAt,
        Long userId,
        List<Long> songIds
) {}
