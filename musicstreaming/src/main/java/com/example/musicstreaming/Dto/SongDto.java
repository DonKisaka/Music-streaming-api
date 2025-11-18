package com.example.musicstreaming.Dto;

import java.util.List;

public record SongDto(
        Long id,
        String title,
        Integer durationInSeconds,
        Long albumId,
        List<Long> artistIds
) {}
